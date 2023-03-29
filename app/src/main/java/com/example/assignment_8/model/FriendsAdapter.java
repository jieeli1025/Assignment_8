package com.example.assignment_8.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_8.Friends;
import com.example.assignment_8.R;
import com.example.assignment_8.RegisterFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>
{

    private ArrayList<Friends> friends;
    private IfriendsListRecyclerAction mListener;
    public FriendsAdapter(ArrayList<Friends> friends, Context context)
    {
        this.friends = friends;
        if(context instanceof IfriendsListRecyclerAction){
            this.mListener = (IfriendsListRecyclerAction) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement IeditButtonAction");
        }
    }

    public ArrayList<Friends> getFriends() {
        return friends;
    }
    public void setUsers(ArrayList<Friends> friends) {
        this.friends = friends;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        private final TextView nameText, emailText;
        private final Button chatButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.friendName);
            emailText = itemView.findViewById(R.id.emailName);
            chatButton = itemView.findViewById(R.id.chat_button);

        }

        public TextView getNameText() {
            return nameText;
        }

        public TextView getEmailText() {
            return emailText;
        }

        public Button getChatButton() {
            return chatButton;
        }

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friends currentFriend = this.getFriends().get(position);
        holder.getEmailText().setText(currentFriend.getEmail());
        holder.getNameText().setText(currentFriend.getName());
        holder.getChatButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = friends.get(holder.getAdapterPosition()).getEmail();
                mListener.chatButtonPressedFromRecyclerView(email);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.getFriends().size();
    }






    public interface IfriendsListRecyclerAction {
        void chatButtonPressedFromRecyclerView(String friendEmail);
    }

}
