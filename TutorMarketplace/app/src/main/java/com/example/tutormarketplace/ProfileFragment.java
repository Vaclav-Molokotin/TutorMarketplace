package com.example.tutormarketplace;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutormarketplace.libs.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Button btnExit = (Button) v.findViewById(R.id.btnExit);
        ImageButton btnEdit = (ImageButton) v.findViewById(R.id.btnEdit);
        ImageView imgPreview = (ImageView) v.findViewById(R.id.imgPreview);

        TextView name = v.findViewById(R.id.name);
        TextView description = v.findViewById(R.id.description);

        TextView rating = v.findViewById(R.id.rating);
        TextView myAds = v.findViewById(R.id.MyAds);

        name.setText(User.CurrentUser.getName());
        description.setText("О себе:\n" + User.CurrentUser.getBio());

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityLogin.class);
                getActivity().startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction();
                manager.replace(R.id.frameContent, new FragmentEditProfile());
                manager.addToBackStack(null);
                manager.commit();

                getActivity().setTitle("Редактирование профиля");
            }
        });

        imgPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Функция в разработке", Toast.LENGTH_SHORT).show();
            }
        });

        // Если текущий пользователь - студент
        if(User.CurrentUser .getRoleID() == 1)
            myAds.setVisibility(View.INVISIBLE);
        else {
            myAds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction();
                    manager.replace(R.id.frameContent, new AdsFragment(true));
                    manager.addToBackStack(null);
                    manager.commit();

                    getActivity().setTitle("Мои объявления");
                }
            });
        }
        return v;
    }

}