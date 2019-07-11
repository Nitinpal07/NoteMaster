package nitin.luckyproject.notemaster.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import nitin.luckyproject.notemaster.Helperclasses.CourseInfo;
import nitin.luckyproject.notemaster.Helperclasses.NoteInfo;
import nitin.luckyproject.notemaster.NoteActivity;
import nitin.luckyproject.notemaster.R;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyHolder>{

    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<CourseInfo> mCourse;

    public CourseAdapter(Context context, List<CourseInfo> course) {
        mContext = context;
        mCourse = course;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview =mLayoutInflater.inflate(R.layout.item_course_list,parent,false);
        return new MyHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.mTextcourse.setText(mCourse.get(position).getTitle());
        holder.mcurrentposition = position;
    }


    @Override
    public int getItemCount() {
        return mCourse.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {


        public final TextView mTextcourse;
        public int mcurrentposition;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mTextcourse = itemView.findViewById(R.id.text_course);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view,mCourse.get(mcurrentposition).getTitle(),Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}
