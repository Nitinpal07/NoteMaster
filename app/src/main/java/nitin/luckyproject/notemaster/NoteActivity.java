package nitin.luckyproject.notemaster;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    public static final String NOTE_POSITION ="nitin.luckyproject.notemaster.NOTE_POSITION";
    public static final int POSITION_NOT_SET = -1;
    private NoteInfo mNote;
    private boolean mIsNewNote;
    private Spinner mSpinnercourse;
    private EditText mTextNoteTitle;
    private EditText mTextNoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSpinnercourse = findViewById(R.id.spinner_course);
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> adapterCourses =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnercourse.setAdapter(adapterCourses);


        readDisplayStateValues();

        mTextNoteTitle = findViewById(R.id.note_title);
        mTextNoteText = findViewById(R.id.note_desc);

        if(!mIsNewNote)
            displayNote(mSpinnercourse, mTextNoteTitle, mTextNoteText);


    }
    private void displayNote(Spinner spinnerCourses, EditText textNoteTitle, EditText textNoteText) {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        int courseIndex = courses.indexOf(mNote.getCourse());
        spinnerCourses.setSelection(courseIndex);
        textNoteTitle.setText(mNote.getTitle());
        textNoteText.setText(mNote.getText());
    }
    private void readDisplayStateValues() {
        Intent intent = getIntent();
        int position = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
        mIsNewNote = position == POSITION_NOT_SET;
        if(!mIsNewNote){
            mNote=DataManager.getInstance().getNotes().get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_share) {
            sendEmail();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void sendEmail() {


        CourseInfo course = (CourseInfo) mSpinnercourse.getSelectedItem();
        String subject =mTextNoteText.getText().toString();
        String text ="Checkout What I learned In android \" "+course.getTitle() +"\"\n" + mTextNoteText.getText().toString();

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc28822");
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        startActivity(intent);

    }
}
