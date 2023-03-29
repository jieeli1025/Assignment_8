package com.example.assignment_8.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_8.Friends;
import com.example.assignment_8.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
private ArrayList<Chat> texts;

public ChatAdapter(ArrayList<Chat> texts){
        this.texts=texts;
        }

        public ArrayList<Chat> getTexts() {
        return texts;
        }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;

        public TextView getText() {
            return text;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.text = itemView.findViewById(R.id.chatText);
        }


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = this.getTexts().get(position);
        holder.getText().setText(chat.getChatText());


    }



    @Override
    public int getItemCount() {
        return this.getTexts().size();
    }









}
