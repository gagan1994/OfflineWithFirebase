package com.example.gagan.offline.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gagan.offline.R;

import com.example.gagan.offline.databinding.FragmentFireBaseBinding;
import com.example.gagan.offline.firebasehelper.FirebaseUserDb;
import com.example.gagan.offline.helper.AddUserHelper;
import com.example.gagan.offline.models.Users;
import com.example.gagan.offline.transform.CircleTransform;
import com.example.gagan.offline.utils.Constant;
import com.example.gagan.offline.viewholders.UserViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static android.icu.text.DateTimePatternGenerator.PatternInfo.OK;

public class FireBaseFragment extends BaseFragment {
    private static final int PICTURE_STATUS = 1;
    @BindView(R.id.iv_pic)
    ImageView camera;

    AddUserHelper addUserHelper;
    private File imageFile;
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    private Uri uri;
    private FirebaseRecyclerAdapter<Users, UserViewHolder> adapter;

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
    }

    public FireBaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFireBaseBinding fragmentFireBaseBinding =
                DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                        R.layout.fragment_fire_base, null, false);
        addUserHelper = new AddUserHelper();
        fragmentFireBaseBinding.setUserModel(addUserHelper);
        View view = fragmentFireBaseBinding.getRoot();
        ButterKnife.bind(this, view);
        initFirebase();
        return view;
    }

    private void initFirebase() {
        adapter = FirebaseUserDb.getFirebaseUserDb().getAdapter();
        adapter.startListening();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.iv_pic)
    public void onCamera() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), PICTURE_STATUS);
    }

    @OnClick(R.id.fb_add)
    public void OnClickAdd() {
        if (addUserHelper.isValid()) {
            if (imageFile == null) {
                addUserHelper.getUser().setImage_url(Constant.DUMMY_IMAGE);
                uploadUser(addUserHelper.getUser());

            } else {
                UploadTask task = FirebaseUserDb.getFirebaseUserDb().uploadUserImage(addUserHelper.getUser(), uri);
                task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        addUserHelper.getUser().setImage_url(taskSnapshot.getUploadSessionUri().toString());
                        uploadUser(addUserHelper.getUser());
                    }
                });
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
            }

        }
    }

    private void uploadUser(Users user) {
        Task<Void> task = FirebaseUserDb.getFirebaseUserDb().upload(user);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "successfull", Toast.LENGTH_LONG).show();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICTURE_STATUS:
                    selectIamge(data);
                    break;
            }
        }
    }

    private void selectIamge(Intent data) {
        uri = data.getData();
        String path = getRealPathFromDocumentUri(uri);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        bmOptions.inSampleSize = 1;
        bmOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bmOptions.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        imageFile = new File(path);
        Picasso.get().load(uri).transform(new CircleTransform()).into(camera);
    }

    private String getRealPathFromDocumentUri(Uri uri) {
        String filePath = "";

        Pattern p = Pattern.compile("(\\d+)$");
        Matcher m = p.matcher(uri.toString());
        if (!m.find()) {
            Log.e("asas", "ID for requested image not found: " + uri.toString());
            return filePath;
        }
        String imgId = m.group();

        String[] column = {MediaStore.Images.Media.DATA};
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{imgId}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();

        return filePath;
    }

    @Override
    public String getTitleOfThis() {
        return "Firebase";
    }

}
