package com.example.tutormarketplace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tutormarketplace.R;
import com.example.tutormarketplace.libs.Application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationAdapter extends BaseAdapter
{
    private List<Application> items = new ArrayList<Application>();
    private LayoutInflater layoutInflater;

    public ApplicationAdapter(Context context, List<Application> items) {
        this.items = items;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Application getItem(int position) {
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
            view = layoutInflater.inflate(R.layout.item_layout_application, parent, false);

        Application application = getApplication(position);

        TextView ownerView = view.findViewById(R.id.owner);
        TextView messageView = view.findViewById(R.id.message);
        TextView adView = view.findViewById(R.id.ad);

        ownerView.setText(application.getOwner().getName());
        messageView.setText(application.getMessage());
        adView.setText(application.getAd().getName());

        return view;
    }

    private Application getApplication(int position)
    {
        return getItem(position);
    }
}
