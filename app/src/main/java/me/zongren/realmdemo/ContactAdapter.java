package me.zongren.realmdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zongren on 2017/4/28.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    public List<Contact> contacts;
    public OnContactClickListener listener;

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
        holder.textView.setText(contacts.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickContact(contacts.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public interface OnContactClickListener {
        void onClickContact(Contact contact);
    }
}
