package io.google.happybirthday;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by patel on 10/10/2019.
 */

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Birthdays> listBirthdays;
    private ArrayList<Birthdays> mArrayList;

    private SqliteDatabase mDatabase;

    public BirthdayAdapter(Context context, ArrayList<Birthdays> listBirthdays) {
        this.context = context;
        this.listBirthdays = listBirthdays;
        this.mArrayList = listBirthdays;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public BirthdayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.birthday_list_layout, parent, false);
        return new BirthdayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BirthdayViewHolder holder, int position) {
        final Birthdays birthdays = listBirthdays.get(position);
        Log.i("JOY",""+listBirthdays.size());
        Log.i("JOY",""+birthdays.getName());
        holder.ph_name.setText(birthdays.getName());
        holder.date.setText(birthdays.getDate());

        holder.editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(birthdays);
            }
        });

        holder.deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteContact(birthdays.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    listBirthdays = mArrayList;
                } else {

                    ArrayList<Birthdays> filteredList = new ArrayList<>();

                    for (Birthdays birthdays : mArrayList) {

                        if (birthdays.getName().toLowerCase().contains(charString)) {

                            filteredList.add(birthdays);
                        }
                    }

                    listBirthdays = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listBirthdays;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listBirthdays = (ArrayList<Birthdays>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return listBirthdays.size();
    }


    private void editTaskDialog(final Birthdays birthdays){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_birthday_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText contactField = (EditText)subView.findViewById(R.id.enter_phno);
        final EditText dateField = (EditText)subView.findViewById(R.id.enter_date);
        final EditText wishStrField = (EditText)subView.findViewById(R.id.enter_wish_str);

        if(birthdays != null){
            nameField.setText(birthdays.getName());
            contactField.setText(String.valueOf(birthdays.getPhnum()));
            dateField.setText(String.valueOf(birthdays.getDate()));
            wishStrField.setText(String.valueOf(birthdays.getWishStr()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit contact");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT CONTACT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String ph_no = contactField.getText().toString();
                final String date = dateField.getText().toString();
                final String wish_str = wishStrField.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateBirthdays(new Birthdays(birthdays.getId(), name, ph_no, date, wish_str));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}