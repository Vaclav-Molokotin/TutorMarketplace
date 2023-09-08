package com.example.tutormarketplace.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.tutormarketplace.ActivityStudent;
import com.example.tutormarketplace.AdsFragment;
import com.example.tutormarketplace.FragmentAdInfo;
import com.example.tutormarketplace.FragmentEditAd;
import com.example.tutormarketplace.FragmentEditProfile;
import com.example.tutormarketplace.FragmentOrdering;
import com.example.tutormarketplace.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.tutormarketplace.libs.Ad;
import com.example.tutormarketplace.libs.AppSettings;

public class AdAdapter extends BaseAdapter implements Filterable
{
    // Определяет доступность кнопок удалить и редактировать вне зависимости от режима администратора
    boolean myAds;
    private List<Ad> ads;

    private ModelFilter filter;
    private List<Ad> filteredAds = new ArrayList<>();
    private List<Ad> filteredModelAds = new ArrayList<>();
    private LayoutInflater layoutInflater;

    Context context;

    public AdAdapter(Context context, List<Ad> ads, boolean myAds) {
        this.context = context;
        this.myAds = myAds;
        this.ads = ads;
        for(int i = 0; i < ads.size(); i++)
            filteredAds.add(ads.get(i));
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filteredAds.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredAds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filteredAds.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null)
            view = layoutInflater.inflate(R.layout.item_layout_ad, parent, false);

        Ad ad = getAd(position);

        TextView idView = (TextView) view.findViewById(R.id.id);
        TextView ownerView = (TextView) view.findViewById(R.id.owner);
        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView descriptionView = (TextView) view.findViewById(R.id.description);
        TextView costView = (TextView) view.findViewById(R.id.cost);

        ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnDelete);
        ImageButton btnEdit = (ImageButton) view.findViewById(R.id.btnEdit);

        Button btnOrder = (Button) view.findViewById(R.id.btnOrder);

        idView.setText(String.valueOf(ad.getId()));
        ownerView.setText(String.valueOf(ad.getOwnerName()));
        nameView.setText(ad.getName());
        descriptionView.setText(ad.getDescription());
        costView.setText(String.valueOf(ad.getCost()));

        if(!myAds && !AppSettings.AdminMode)
        {
            btnClose.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
        }
        else
        {
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ad.DeleteAd(filteredAds.get(position));

                    FragmentTransaction manager = AppSettings.MainActivity.getSupportFragmentManager().beginTransaction();
                    manager.replace(R.id.frameContent, new AdsFragment(myAds));
                    manager.commit();
                }
            });

            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Ad.CurrentAd = ad;
                    FragmentTransaction manager = AppSettings.MainActivity.getSupportFragmentManager().beginTransaction();
                    manager.replace(R.id.frameContent, new FragmentOrdering());
                    manager.commit();

                    AppSettings.MainActivity.setTitle("Оформление заявки");
                }
            });



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction manager = AppSettings.MainActivity.getSupportFragmentManager().beginTransaction();
                    manager.replace(R.id.frameContent, new FragmentAdInfo(ad.getId()));
                    manager.addToBackStack(null);
                    manager.commit();

                    AppSettings.MainActivity.setTitle("Информация об услуге");
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ad.CurrentAd = filteredAds.get(position);

                    FragmentTransaction manager = AppSettings.MainActivity.getSupportFragmentManager().beginTransaction();
                    manager.replace(R.id.frameContent, new FragmentEditAd(false));
                    manager.commit();

                    AppSettings.MainActivity.setTitle("Изменить объявление");
                }
            });
        }
        return view;
    }

    private Ad getAd(int position)
    {
        return (Ad) getItem(position);
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new ModelFilter();
        return filter;
    }


    private class ModelFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            constraint = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            List<Ad> filteredItems = new ArrayList<Ad>();
            if(constraint.toString().length() > 0)
            {


                for (int i = 0; i < ads.size(); i++)
                {
                    Ad ad = ads.get(i);
                    if(ad.getName().toLowerCase().contains(constraint))
                        filteredItems.add(ad);
                }
            }
            else
            {
                for (int i = 0; i < ads.size(); i++)
                {
                    Ad ad = ads.get(i);
                        filteredItems.add(ad);
                }
            }

            results.count = filteredItems.size();
            results.values = filteredItems;

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filteredModelAds = (ArrayList<Ad>)results.values;
            notifyDataSetChanged();
            filteredAds.clear();
            for(int i = 0; i < filteredModelAds.size(); i++)
                filteredAds.add(filteredModelAds.get(i));
            notifyDataSetInvalidated();
        }
    }
}