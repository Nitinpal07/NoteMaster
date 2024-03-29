package nitin.luckyproject.notemaster;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;

import nitin.luckyproject.notemaster.Adapter.NoteAdapter;
import nitin.luckyproject.notemaster.Helperclasses.DataManager;
import nitin.luckyproject.notemaster.Helperclasses.NoteInfo;

public class NoteListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private NoteAdapter mRecycleviewadapter;

    //private ArrayAdapter<NoteInfo> mAdapterNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(NoteListActivity.this,NoteActivity.class));
            }
        });

        initializeDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
       // mAdapterNotes.notifyDataSetChanged();
        mRecycleviewadapter.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {

//        final ListView listNotes =  findViewById(R.id.lists_note);
//
//        List<NoteInfo> notes = DataManager.getInstance().getNotes();
//        mAdapterNotes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
//
//        listNotes.setAdapter(mAdapterNotes);
//
//        //setting onItemClickListener
//
//        listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent =new Intent(NoteListActivity.this,NoteActivity.class);
////                NoteInfo note = (NoteInfo) listNotes.getItemAtPosition(i);
//                intent.putExtra(NoteActivity.NOTE_POSITION,i);
//                startActivity(intent);
//
//            }
//        });
//
//
//

        mRecyclerView = findViewById(R.id.recyclerview_note);
        LinearLayoutManager noteLinearLayoutManager =new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(noteLinearLayoutManager);

        List<NoteInfo> notes = DataManager.getInstance().getNotes();

        mRecycleviewadapter = new NoteAdapter(this,notes);
        mRecyclerView.setAdapter(mRecycleviewadapter);
    }

}
