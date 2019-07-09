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

import nitin.luckyproject.notemaster.Helperclasses.CourseInfo;
import nitin.luckyproject.notemaster.Helperclasses.DataManager;
import nitin.luckyproject.notemaster.Helperclasses.NoteInfo;

public class NoteActivity extends AppCompatActivity {

    public static final String NOTE_POSITION ="nitin.luckyproject.notemaster.NOTE_POSITION";
    public static final int POSITION_NOT_SET = -1;
    public static final String ORIGINAL_NOTE_COURSE_ID="nitin.luckyproject.notemaster.ORIGINAL_NOTE_COURSE_ID";
    public static final String ORIGINAL_NOTE_TITLE="nitin.luckyproject.notemaster.ORIGINAL_NOTE_TITLE";
    public static final String ORIGINAL_NOTE_TEXT="nitin.luckyproject.notemaster.ORIGINAL_NOTE_TEXT";
    private NoteInfo mNote;
    private boolean mIsNewNote;
    private Spinner mSpinnercourse;
    private EditText mTextNoteTitle;
    private EditText mTextNoteText;
    private int mNoteposition;
    private boolean mIsCancelling;
    private String mOriginalNoteCourseId;
    private String mOriginalNoteTitle;
    private String mOriginalNoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mSpinnercourse = findViewById(R.id.spinner_course);
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> adapterCourses =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnercourse.setAdapter(adapterCourses);


        readDisplayStateValues();
        if(savedInstanceState ==null){
            saveOriginalNoteValue();
        }
        else{
            restoreOriginalNoteValue(savedInstanceState);
        }


        mTextNoteTitle = findViewById(R.id.note_title);
        mTextNoteText = findViewById(R.id.note_desc);

        if(!mIsNewNote)
            displayNote(mSpinnercourse, mTextNoteTitle, mTextNoteText);


    }

    private void restoreOriginalNoteValue(Bundle savedInstanceState) {
        mOriginalNoteCourseId = savedInstanceState.getString(ORIGINAL_NOTE_COURSE_ID);
        mOriginalNoteTitle =savedInstanceState.getString(ORIGINAL_NOTE_TITLE);
        mOriginalNoteText =savedInstanceState.getString(ORIGINAL_NOTE_TEXT);
    }


    private void saveOriginalNoteValue() {
        if(mIsNewNote){
            return;
        }

        mOriginalNoteCourseId = mNote.getCourse().getCourseId();
        mOriginalNoteTitle = mNote.getTitle();
        mOriginalNoteText = mNote.getText();

    }


    @Override
    protected void onPause() {
        super.onPause();
        if(mIsCancelling){
            if(mIsNewNote) {
                DataManager.getInstance().removeNote(mNoteposition);
            }else{
                storePreviousNoteValue();
            }
        }
        else{
            saveNote();
        }

    }

    private void storePreviousNoteValue() {
        CourseInfo course =DataManager.getInstance().getCourse(mOriginalNoteCourseId);
        mNote.setCourse(course);
        mNote.setTitle(mOriginalNoteTitle);
        mNote.setText(mOriginalNoteText);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ORIGINAL_NOTE_COURSE_ID,mOriginalNoteCourseId);
        outState.putString(ORIGINAL_NOTE_TEXT,mOriginalNoteTitle);
        outState.putString(ORIGINAL_NOTE_TEXT,mOriginalNoteText);
    }

    private void saveNote() {
        mNote.setCourse((CourseInfo) mSpinnercourse.getSelectedItem());
        mNote.setTitle(mTextNoteTitle.getText().toString());
        mNote.setText(mTextNoteText.getText().toString());

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
        if(mIsNewNote){
            createNewNote();
        }
        else
            {
                mNote=DataManager.getInstance().getNotes().get(position);

            }
    }

    private void createNewNote() {
        DataManager dm =DataManager.getInstance();
        mNoteposition = dm.createNewNote();
        mNote =dm.getNotes().get(mNoteposition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.action_next);
        int LastNoteIndex = DataManager.getInstance().getNotes().size() -1;
        item.setEnabled(mNoteposition < LastNoteIndex);
        return super.onPrepareOptionsMenu(menu);
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
        if (id == R.id.action_cancel) {
            mIsCancelling = true;
            finish();
        }
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        if (id == R.id.action_next) {
            movenext();
        }


        return super.onOptionsItemSelected(item);
    }

    private void movenext() {
        saveNote();

        ++mNoteposition;
        mNote =DataManager.getInstance().getNotes().get(mNoteposition);

        saveOriginalNoteValue();
        displayNote(mSpinnercourse,mTextNoteTitle,mTextNoteText);
         invalidateOptionsMenu();

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
