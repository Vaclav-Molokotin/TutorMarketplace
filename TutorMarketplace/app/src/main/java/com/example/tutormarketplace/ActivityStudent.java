package com.example.tutormarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tutormarketplace.libs.AppSettings;

public class ActivityStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        AppSettings.MainActivity = this;

        setTitle("Профиль");
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        manager.add(R.id.frameContent, new ProfileFragment());
        manager.commit();
    }

    public void bottomMenu_onClick(View view)
    {
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        switch (view.getId())
        {
            case R.id.layoutAds:
                manager.replace(R.id.frameContent, new AdsFragment(false));
                setTitle("Объявления");
                break;
            case R.id.layoutMessanger:
                manager.replace(R.id.frameContent, new MessangerFragment());
                setTitle("Диалоги");
                break;
            case R.id.layoutProfile:
                manager.replace(R.id.frameContent, new ProfileFragment());
                setTitle("Профиль");
                break;
            default:
                break;
        }
        manager.addToBackStack(null);
        manager.commit();
    }



}