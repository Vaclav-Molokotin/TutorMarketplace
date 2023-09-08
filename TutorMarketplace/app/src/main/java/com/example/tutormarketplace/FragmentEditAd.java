package com.example.tutormarketplace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutormarketplace.libs.Ad;
import com.example.tutormarketplace.libs.AppSettings;
import com.example.tutormarketplace.libs.Db;
import com.example.tutormarketplace.libs.User;

import java.time.Instant;

public class FragmentEditAd extends Fragment {

    private View view;
    private EditText editName;
    private EditText editDescription;
    private EditText editCost;

    private boolean isNew; // Создаётся ли новое объявление

    public FragmentEditAd(boolean isNew)
    {
        this.isNew = isNew;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_ad, container, false);

        editName = view.findViewById(R.id.editName);
        editDescription = view.findViewById(R.id.editDescription);
        editCost = view.findViewById(R.id.editCost);

        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(tryEditAd())
                {
                    FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction();
                    if(!AppSettings.AdminMode)
                    {
                        manager.replace(R.id.frameContent, new AdsFragment(true));
                        getActivity().setTitle("Мои объявления");
                    }
                    else
                    {
                        manager.replace(R.id.frameContent, new AdsFragment(false));
                        getActivity().setTitle("Объявления");
                    }
                    manager.commit();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction();
                if(!AppSettings.AdminMode)
                {
                    manager.replace(R.id.frameContent, new AdsFragment(true));
                    getActivity().setTitle("Мои объявления");
                }
                else
                {
                    manager.replace(R.id.frameContent, new AdsFragment(false));
                    getActivity().setTitle("Объявления");
                }
                manager.commit();
            }
        });

        return view;
    }

    private boolean tryEditAd()
    {
        if(editName.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Укажите наименование услуги", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(editDescription.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Укажите описание услуги", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(editCost.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Укажите стоимость услуги", Toast.LENGTH_SHORT).show();
            return false;
        }


        String[] args;
        // Если редактируется существующее объявление
        if(!isNew) {
            Ad ad = new Ad(
                    Ad.CurrentAd.getId(),
                    editName.getText().toString(),
                    editDescription.getText().toString(),
                    Ad.CurrentAd.getOwner(),
                    Long.valueOf(editCost.getText().toString()));
            Ad.UpdateAd(ad);
        }
        // Если создаётся новое объявление
        else
        {
            Instant date = Instant.now();
            Ad ad = new Ad(
                    0,
                    editName.getText().toString(),
                    editDescription.getText().toString(),
                    Float.valueOf(editCost.getText().toString()),
                    date.toString(),
                    User.CurrentUser.getId());
            Ad.AddAd(ad);
        }

        return true;
    }

}