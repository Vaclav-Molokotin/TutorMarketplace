package com.example.tutormarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tutormarketplace.libs.AppSettings;

public class ActivityTutor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

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
            case R.id.layoutApplications:
                manager.replace(R.id.frameContent, new ApplicationsFragment());
                setTitle("Заявки");
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

    /*Fragment1 fragment1;
    Fragment2 fragment2;
    FragmentTransaction fragmentTransaction;
    CheckBox chbStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        chbStack = (CheckBox)findViewById(R.id.chbStack);
    }

    public void onClick(View view)
    {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId())
        {
            case R.id.btnAdd:
                fragmentTransaction.add(R.id.frmCont, fragment1);
                break;
            case R.id.btnRemove:
                fragmentTransaction.remove(fragment1);
                break;
            case R.id.btnReplace:
                fragmentTransaction.replace(R.id.frmCont, fragment2);
                break;
            default:
                break;
        }
        if(chbStack.isChecked())
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
*/

}