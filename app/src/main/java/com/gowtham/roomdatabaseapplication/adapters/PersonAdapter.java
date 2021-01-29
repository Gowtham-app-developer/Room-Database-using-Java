package com.gowtham.roomdatabaseapplication.adapters;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gowtham.roomdatabaseapplication.EditActivity;
import com.gowtham.roomdatabaseapplication.R;
import com.gowtham.roomdatabaseapplication.constants.Constants;
import com.gowtham.roomdatabaseapplication.database.AppDatabase;
import com.gowtham.roomdatabaseapplication.model.Contact;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {
    private Context context;
    private List<Contact> mPersonList;

    public PersonAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(mPersonList.get(i).getName());
        myViewHolder.email.setText(mPersonList.get(i).getEmail());
        myViewHolder.number.setText(mPersonList.get(i).getNumber());
        myViewHolder.pinCode.setText(mPersonList.get(i).getPinCode());
        myViewHolder.city.setText(mPersonList.get(i).getCity());
    }

    @Override
    public int getItemCount() {
        if (mPersonList == null) {
            return 0;
        }
        return mPersonList.size();

    }

    public void setTasks(List<Contact> personList) {
        mPersonList = personList;
        notifyDataSetChanged();
    }

    public List<Contact> getTasks() {
        return mPersonList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, email, pinCode, number, city;
        private ImageView editImage;
        private AppDatabase mDatabase;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDatabase = AppDatabase.getInstance(context);
            name = itemView.findViewById(R.id.person_name);
            email = itemView.findViewById(R.id.person_email);
            pinCode = itemView.findViewById(R.id.person_pincode);
            number = itemView.findViewById(R.id.person_number);
            city = itemView.findViewById(R.id.person_city);
            editImage = itemView.findViewById(R.id.edit_Image);
            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int elementId = mPersonList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(context, EditActivity.class);
                    i.putExtra(Constants.UPDATE_Person_Id, elementId);
                    context.startActivity(i);
                }
            });
        }
    }
}
