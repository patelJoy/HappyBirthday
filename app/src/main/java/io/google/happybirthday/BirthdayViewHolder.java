package io.google.happybirthday;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by patel on 10/10/2019.
 */

public class BirthdayViewHolder extends RecyclerView.ViewHolder {

    public TextView name,date,ph_name;
    public ImageView deleteContact;
    public  ImageView editContact;

    public BirthdayViewHolder(View itemView) {
        super(itemView);
        ph_name = (TextView)itemView.findViewById(R.id.ph_name);
        date = (TextView)itemView.findViewById(R.id.ph_date);
        deleteContact = (ImageView)itemView.findViewById(R.id.delete_contact);
        editContact = (ImageView)itemView.findViewById(R.id.edit_contact);
    }
}