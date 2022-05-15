package tbc.dma.toapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<notesEn> notesData = new ArrayList<>();
    private OnItemClickListener listener;

    public void setNotesData(List<notesEn> notesData) {
        this.notesData.clear();
        this.notesData = notesData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View notesRow = layoutInflater.inflate(R.layout.notes_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(notesRow);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final notesEn data = notesData.get(position);
        holder.Title.setText(data.getNotesTitle());
        holder.Description.setText(data.getNotesText());
    }

    @Override
    public int getItemCount() {
        return notesData.size();
    }

    public int getPosition() { return getPosition(); }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public TextView Description;
        public RelativeLayout container;
        public ImageView delete;

        public ViewHolder(View view) {
            super(view);
            this.Title = view.findViewById(R.id.Title);
            this.Description = view.findViewById(R.id.Description);
            this.container = view.findViewById(R.id.notes_row);
            this.delete = view.findViewById(R.id.delete);
            boolean undo = false;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    MainActivity.deleteNote(notesData.get(position));
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notesData.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(notesEn note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
