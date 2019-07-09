package nitin.luckyproject.notemaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{

    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<NoteInfo> mNote;

    public MyAdapter(Context context, List<NoteInfo> note) {
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

        holder.mTextcourse.setText(mNote.get(position).getText());
        holder.mTexttitle.setText(mNote.get(position).getTitle());


    }


    @Override
    public int getItemCount() {
        return mNote.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {


        public final TextView mTextcourse;
        public final TextView mTexttitle;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mTextcourse = itemView.findViewById(R.id.text_course);
            mTexttitle = itemView.findViewById(R.id.text_title);
        }
    }
}
