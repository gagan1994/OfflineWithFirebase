package com.example.gagan.offline.firebasehelper;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gagan.offline.R;
import com.example.gagan.offline.models.Users;
import com.example.gagan.offline.utils.Constant;
import com.example.gagan.offline.viewholders.UserViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by Gagan on 6/13/2018.
 */

public class FirebaseUserDb {
    private final static FirebaseUserDb firebaseUserDb = new FirebaseUserDb();
    private final static String USER = "users1";
    private final StorageReference storage;
    private final DatabaseReference userRef;

    public static FirebaseUserDb getFirebaseUserDb() {
        return firebaseUserDb;
    }

    private FirebaseUserDb() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        storage = FirebaseStorage.getInstance().getReference("users_pic");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference(USER);
        userRef.keepSynced(true);
    }


    public Task<Void> upload(Users users) {
        DatabaseReference newRequestRef = userRef.push();
        Task<Void> task = newRequestRef.setValue(users);
        return task;
    }


    public UploadTask uploadUserImage(Users user, Uri uri) {

        UploadTask task = storage.child(user.getName() + user.getPhone()).putFile(uri);

        return task;
    }

    public FirebaseRecyclerAdapter<Users, UserViewHolder> getAdapter() {
        Query query = userRef
                .limitToLast(50);
        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .build();
        return new FirebaseRecyclerAdapter<Users, UserViewHolder>(options) {

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.user_row, null, false);
                return new UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull Users model) {
                holder.bind(model);
            }
        };
    }
}
