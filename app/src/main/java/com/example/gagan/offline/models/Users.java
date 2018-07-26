package com.example.gagan.offline.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Gagan on 6/13/2018.
 */

public class Users {
    private String name;
    private String phone;
    private String image_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
