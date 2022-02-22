package com.example.notes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.DBHelper;
import com.example.notes.EditNoteActivity;
import com.example.notes.Model.NotesModel;
import com.example.notes.NoteDetailActivity;
import com.example.notes.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    ArrayList<NotesModel> list;
    Context context;
    String userName;
    DBHelper db;

    public NotesAdapter(ArrayList<NotesModel> list, Context context,String userName) {
        this.list = list;
        this.context = context;
        this.userName=userName;
        db=new DBHelper(context);
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_note_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView popButton=holder.itemView.findViewById(R.id.menuPopUpButton);
        NotesModel note= list.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, NoteDetailActivity.class);
                intent.putExtra("title",note.getTitle());
                intent.putExtra("content",note.getContent());
                intent.putExtra("userName",userName);
                //also image need to transfer
                context.startActivity(intent);
            }
        });
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(view.getContext(),view, Gravity.END);
                popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent=new Intent(view.getContext(), EditNoteActivity.class);
                        intent.putExtra("title",note.getTitle());
                        intent.putExtra("content",note.getContent());
                        intent.putExtra("userName",userName);
//                        intent.putExtra("noteId",docId);
                        view.getContext().startActivity(intent);
                        return false;
                    }
                });

                popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //delete the data
                        String uid=db.getUid(userName,note.getTitle(),note.getContent());
                        Boolean answer=db.deleteNote(uid);
                        if(answer){
                            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Some Problem occur", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            image=itemView.findViewById(R.id.);
            title = itemView.findViewById(R.id.NoteTittle);
            content = itemView.findViewById(R.id.NoteDesc);
        }
    }
}
