package nitin.luckyproject.notemaster.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nitin.luckyproject.notemaster.Helperclasses.NoteInfo;
import nitin.luckyproject.notemaster.NoteActivity;
import nitin.luckyproject.notemaster.R;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyHolder>{

    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<NoteInfo> mNote;

    public NoteAdapter(Context context, List<NoteInfo> note) {
        mContext = context;
        mNote = note;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview =mLayoutInflater.inflate(R.layout.item_note_list,parent,false);
        return new MyHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.mTextcourse.setText(mNote.get(position).getCourse().getTitle());
        holder.mTexttitle.setText(mNote.get(position).getTitle());
        holder.mcurrentposition = position;



    }


    @Override
    public int getItemCount() {
        return mNote.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {


        public final TextView mTextcourse;
        public final TextView mTexttitle;
        public int mcurrentposition;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mTextcourse = itemView.findViewById(R.id.text_course);
            mTexttitle = itemView.findViewById(R.id.text_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent =new Intent(mContext, NoteActivity.class);
                intent.putExtra(NoteActivity.NOTE_POSITION,mcurrentposition);
                mContext.startActivity(intent);
                }
            });
        }
    }
}
