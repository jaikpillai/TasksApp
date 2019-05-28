package com.jai.applicationarchitecture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class TaskRecyclerAdapter extends ListAdapter<TaskModelClass, TaskRecyclerAdapter.NoteHolder> {
    private OnItemClickListener listener;


    protected TaskRecyclerAdapter() {
        super(DIFF_CALLBACK);

    }

    private static final DiffUtil.ItemCallback<TaskModelClass> DIFF_CALLBACK = new DiffUtil.ItemCallback<TaskModelClass>() {
        @Override
        public boolean areItemsTheSame(@NonNull TaskModelClass oldItem, @NonNull TaskModelClass newItem) {


            return oldItem.getId() == newItem.getId();

        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskModelClass oldItem, @NonNull TaskModelClass newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);




        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        TaskModelClass task = getItem(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.priority.setText(String.valueOf(task.getPriority()));



    }





    public TaskModelClass getNoteAt(int position) {

        return getItem(position);
    }



    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView priority;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_view_title);
            description = itemView.findViewById(R.id.text_view_description);
            priority = itemView.findViewById(R.id.text_view_priority);



            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TaskModelClass task);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }


}
