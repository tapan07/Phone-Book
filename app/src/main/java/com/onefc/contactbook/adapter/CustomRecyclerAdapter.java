package com.onefc.contactbook.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onefc.contactbook.R;
import com.onefc.contactbook.model.ContactDetail;

import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private List<ContactDetail> contacts;

    public CustomRecyclerAdapter(List<ContactDetail> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactDetail contact = contacts.get(position);
        holder.contactName.setText(contact.getName());
        holder.contactNumber.setText(String.valueOf(contact.getNumber()));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView contactName;
        public TextView contactNumber;

        public ViewHolder(@NonNull View view) {
            super(view);
            contactName = (TextView) view.findViewById(R.id.contact_name);
            contactNumber = (TextView) view.findViewById(R.id.contact_number);
        }
    }
}
