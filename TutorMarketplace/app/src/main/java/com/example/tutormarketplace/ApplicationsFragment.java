package com.example.tutormarketplace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tutormarketplace.adapter.AdAdapter;
import com.example.tutormarketplace.adapter.ApplicationAdapter;
import com.example.tutormarketplace.libs.Application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsFragment extends Fragment {

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applications, container, false);
        listView = (ListView) view.findViewById(R.id.lvContent);

        List<Application> items = initData();
        ApplicationAdapter adapter = new ApplicationAdapter(getActivity(), items);
        listView.setAdapter(adapter);

        return view;
    }

    private List<Application> initData()
    {
        List<Application> items = new ArrayList<Application>();

        return items;
    }
}