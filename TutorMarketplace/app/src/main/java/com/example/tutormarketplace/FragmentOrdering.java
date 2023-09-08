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
import com.example.tutormarketplace.libs.Application;
import com.example.tutormarketplace.libs.User;
import com.google.android.material.slider.BaseOnChangeListener;

import java.time.Instant;

public class FragmentOrdering extends Fragment {

    public FragmentOrdering()
    {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ordering, container, false);

        Button btnOrder = v.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tryOrder(v))
                {
                    FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction();
                    manager.replace(R.id.frameContent, new ProfileFragment());
                    manager.commit();

                    Toast.makeText(getActivity(), "Заявка оформлена!", Toast.LENGTH_SHORT).show();

                    getActivity().setTitle("Профиль");
                }
            }
        });

        return v;
    }

    private boolean tryOrder(View view)
    {
        EditText message = (EditText) view.findViewById(androidx.appcompat.R.id.message);
        String messageText = message.getText().toString();

        if(messageText.trim().equals(""))
        {
            Toast.makeText(AppSettings.MainActivity, "Напишите сообщение!", Toast.LENGTH_SHORT).show();
            return false;
        }

        Application application = new Application(0, User.CurrentUser, message.getText().toString(), Ad.CurrentAd, Instant.now());
        Application.addApplication(application);

        return true;
    }
}