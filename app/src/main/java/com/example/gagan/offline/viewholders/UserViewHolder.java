package com.example.gagan.offline.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gagan.offline.R;
import com.example.gagan.offline.models.Users;
import com.example.gagan.offline.transform.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gagan on 6/13/2018.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_pic)
    ImageView imageView;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phno)
    TextView tv_phno;

    public UserViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Users users) {
        Picasso.get().load(users.getImage_url()).
                transform(new CircleTransform()).into(imageView);
        tv_name.setText(users.getName());
        tv_phno.setText(users.getPhone());
    }
}
