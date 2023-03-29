package com.example.assignment_8;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignment_8.model.Chat;
import com.example.assignment_8.model.ChatAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private String email;
    private ArrayList<Chat> chatArray;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    public chatFragment() {
        // Required empty public constructor
    }



    public static chatFragment newInstance(String email) {
        chatFragment fragment = new chatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
              email = getArguments().getString(ARG_PARAM1);
        }
        chatArray = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        loadchat();
    }


    private void loadchat() {
        Log.d("chat fragment loadingchat", "loadData: ");
        ArrayList<Chat> ChatArray = new ArrayList<>();

        db.collection("users")
                .document("authenticatedUsers")
                .collection("friends")
                .document(email)
                .collection("chat")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
//
                                Chat chats = documentSnapshot.toObject(Chat.class);
                                Log.d("ChatFragment: friend snapshot received back from firebase", chats.toString());
                                ChatArray.add(chats);

                            }
                            updateRecyclerView(ChatArray);
                        }
                    }
                });


    }

    public void updateRecyclerView(ArrayList<Chat> chatArray){
        this.chatArray = chatArray;
        Log.d("chat fragment - updating recyler view ", chatArray.toString());
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        chatAdapter = new ChatAdapter(chatArray);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(chatAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = rootView.findViewById(R.id.chatRecyclerVieww);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        chatAdapter = new ChatAdapter(chatArray);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(chatAdapter);

        getActivity().setTitle("Chat Fragment");
        EditText editText = rootView.findViewById(R.id.EditChat);
        Button sendButton = rootView.findViewById(R.id.editChatButton);
        Log.d("Chat fragment email received back", email);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                Chat newChat = new Chat(message);
                LocalTime currentTime = LocalTime.now();
                db.collection("users")
                        .document("authenticatedUsers")
                        .collection("friends")
                        .document(email)
                        .collection("chat")
                        .document(currentTime.toString())
                        .set(newChat)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("chat fragment: chat added", "friends added");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("FAILED TO ADD A chat", String.valueOf(e));
                            }
                        });
                loadchat();
                editText.setText("");

            }





});




        return rootView;
    }
}