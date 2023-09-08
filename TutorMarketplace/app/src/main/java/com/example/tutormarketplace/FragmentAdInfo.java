package com.example.tutormarketplace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tutormarketplace.libs.Ad;

import org.w3c.dom.Text;


public class FragmentAdInfo extends Fragment {

    private long id;
    private Ad ad;
    public FragmentAdInfo(long id)
    {
        this.id = id;
        Ad ad = Ad.GetAdById(id);
        this.ad = ad;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ad_info, container, false);

        TextView nameView = v.findViewById(R.id.name);
        TextView descriptionView = v.findViewById(R.id.description);
        TextView costView = v.findViewById(R.id.cost);

        nameView.setText(ad.getName());
        descriptionView.setText(ad.getDescription());
        costView.setText(String.valueOf(ad.getCost()));

        Button btnOrder = v.findViewById(R.id.declareApp);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Вставить код обработки заявки
                FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction();
                manager.replace(R.id.frameContent, new FragmentOrdering());
                manager.commit();

                getActivity().setTitle("Оформление заявки");
            }
        });

        return v;
    }
}