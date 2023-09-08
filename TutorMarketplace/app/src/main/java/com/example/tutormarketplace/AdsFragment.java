package com.example.tutormarketplace;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tutormarketplace.adapter.AdAdapter;
import com.example.tutormarketplace.libs.Ad;
import com.example.tutormarketplace.libs.AppSettings;
import com.example.tutormarketplace.libs.Db;
import com.example.tutormarketplace.libs.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AdsFragment extends Fragment {

    private List<Ad> ads;
    private ListView listView;
    private AdAdapter adapter;
    private EditText search;

    private sortType sort = sortType.None;
    private enum sortType
    {
        Asc,
        Desc,
        None
    }
    private boolean myAds; // Определяет, отображаются все объявления, либо только свои.

    public AdsFragment(boolean myAds) {
        this.myAds = myAds;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ads, container, false);

        search = v.findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Filter filter = adapter.getFilter();
                filter.filter(search.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setInterface(v);

        listView = (ListView) v.findViewById(R.id.lvContent);

        ads = initData();
        adapter = new AdAdapter(getActivity(), ads, myAds);

        listView.setAdapter(adapter);

        return v;
    }

    private List<Ad> initData() {
        List<Ad> list;
        if(myAds)
            list = Ad.GetAdsByOwner(User.CurrentUser.getName());
        else
        {
            list = Ad.GetAds();
        }
        return list;
    }

    private void setInterface(View view)
    {
        if(myAds)
        {
            Button btnAdd = view.findViewById(R.id.btnAdd);
            btnAdd.setVisibility(View.VISIBLE);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction();
                    manager.replace(R.id.frameContent, new FragmentEditAd(true));
                    manager.commit();

                    getActivity().setTitle("Новое объявление");
                }
            });
        }
    }
}