package com.example.gagan.offline.helper;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;

import com.example.gagan.offline.firebasehelper.FirebaseUserDb;
import com.example.gagan.offline.models.Users;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Gagan on 6/13/2018.
 */

public class AddUserHelper extends BaseObservable {
    private final Users users;

    public AddUserHelper() {
        this.users = new Users();
    }

    @Bindable
    public String getName() {
        return users.getName();
    }

    public void setName(String name) {
        users.setName(name);
    }

    @Bindable
    public String getPhone() {
        return users.getPhone();
    }

    public void setPhone(String phone) {
        users.setPhone(phone);
    }

    public boolean isValid() {
        return users.getPhone().trim().length() != 0
                &&
                users.getName().trim().length() > 3;
    }


    public Users getUser() {
        return users;
    }
}
