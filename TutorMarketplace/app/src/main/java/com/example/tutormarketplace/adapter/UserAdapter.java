package com.example.tutormarketplace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tutormarketplace.R;
import com.example.tutormarketplace.libs.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter
{
    private List<User> items = new ArrayList<User>();
    private LayoutInflater layoutInflater;

    public UserAdapter(Context context, List<User> items)
    {
        this.items = items;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
            view = layoutInflater.inflate(R.layout.item_layout_dialog, parent, false);

        User user = getUser(position);

        return view;
    }

    private User getUser(int position)
    {
        return (User)getItem(position);
    }
}
