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

import com.example.tutormarketplace.libs.Db;
import com.example.tutormarketplace.libs.User;

public class FragmentEditProfile extends Fragment {

    View thisView;

    public FragmentEditProfile() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        Button btnSave = thisView.findViewById(R.id.btnSave);
        Button btnCancel = thisView.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cохранение изменений
                if(tryWriteProfileInfo()) {
                    FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction();
                    getActivity().setTitle("Профиль");
                    manager.replace(R.id.frameContent, new ProfileFragment());
                    manager.commit();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction();
                getActivity().setTitle("Профиль");
                manager.replace(R.id.frameContent, new ProfileFragment());
                manager.commit();
            }
        });

        return thisView;
    }

    private boolean tryWriteProfileInfo()
    {
        EditText editName = thisView.findViewById(R.id.editName);
        EditText editBio = thisView.findViewById(R.id.editBio);

        String editNameText = editName.getText().toString();
        String editBioText = editBio.getText().toString();

        if(editNameText.trim().equals(""))
        {
            Toast.makeText(getActivity(), "Имя не может быть пустым!", Toast.LENGTH_SHORT).show();
            return false;
        }

        String[] args = {editNameText, editBioText, String.valueOf(User.CurrentUser.getId())};
        Db.db.execSQL("UPDATE User SET Name = ?, Bio = ? WHERE ID = ?;", args);

        User.CurrentUser.setName(editNameText);
        User.CurrentUser.setBio(editBioText);

        return true;
    }
}