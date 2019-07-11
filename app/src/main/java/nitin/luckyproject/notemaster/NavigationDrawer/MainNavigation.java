package nitin.luckyproject.notemaster.NavigationDrawer;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.List;

import nitin.luckyproject.notemaster.Adapter.CourseAdapter;
import nitin.luckyproject.notemaster.Adapter.NoteAdapter;
import nitin.luckyproject.notemaster.Helperclasses.CourseInfo;
import nitin.luckyproject.notemaster.Helperclasses.DataManager;
import nitin.luckyproject.notemaster.Helperclasses.NoteInfo;
import nitin.luckyproject.notemaster.NoteActivity;
import nitin.luckyproject.notemaster.R;

public class MainNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerItem;
    private NoteAdapter mRecycleviewadapter;
    private LinearLayoutManager mNoteLinearLayoutManager;
    private CourseAdapter mCourseAdapter;
    private GridLayoutManager mCourseLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainNavigation.this, NoteActivity.class));
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        initializeDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // mAdapterNotes.notifyDataSetChanged();
        mRecycleviewadapter.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {


        mRecyclerItem = findViewById(R.id.list_item);

        mNoteLinearLayoutManager = new LinearLayoutManager(this);
        mCourseLayoutManager = new GridLayoutManager(this,2);

        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        mRecycleviewadapter = new NoteAdapter(this,notes);
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        mCourseAdapter = new CourseAdapter(this,courses);
        DisplayNote();
    }

    public void DisplayNote(){
        mRecyclerItem.setLayoutManager(mNoteLinearLayoutManager);
        mRecyclerItem.setAdapter(mRecycleviewadapter);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu =navigationView.getMenu();
        menu.findItem(R.id.nav_notes).setChecked(true);

    }
    public void DisplayCourse(){
        mRecyclerItem.setLayoutManager(mCourseLayoutManager);
        mRecyclerItem.setAdapter(mCourseAdapter);

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu =navigationView.getMenu();
        menu.findItem(R.id.nav_course).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            // Handle the notes action
            DisplayNote();
        } else if (id == R.id.nav_course) {
            handleselection("Course");
            DisplayCourse();
        }
        else if (id == R.id.nav_share) {
            handleselection("Share");
        } else if (id == R.id.nav_send) {
            handleselection("Send");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleselection(String msg) {
        View view =findViewById(R.id.list_item);
        Snackbar.make(view,msg,Snackbar.LENGTH_LONG).show();
    }
}
