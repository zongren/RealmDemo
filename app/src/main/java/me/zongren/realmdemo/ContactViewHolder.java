package me.zongren.realmdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zongren on 2017/4/28.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public ContactViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
    }
}
