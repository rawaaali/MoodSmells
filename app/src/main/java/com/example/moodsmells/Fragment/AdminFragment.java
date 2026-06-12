package com.example.moodsmells.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.moodsmells.R;


public class AdminFragment extends Fragment {

    private LinearLayout btnPage1, btnPage2,btnEdit,btnDetails;

    private Button btnGame;

    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        // ربط الأزرار
        btnPage1 =  view.findViewById(R.id.btnPage1);
        btnEdit=view.findViewById(R.id.btnEdit);
        btnPage2 = view.findViewById(R.id.btnPage2);
        btnDetails= view.findViewById(R.id.btnDetails);
        btnGame= view.findViewById(R.id.btnGame);


        // زر الصفحة الأولى
        btnPage1.setOnClickListener(v -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout, new AddSmellsFragment());
            ft.addToBackStack(null); // يتيح الرجوع
            ft.commit();
        });

        // زر الصفحة الثانية
        btnPage2.setOnClickListener(v -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout, new SmellsListFragment());
            ft.addToBackStack(null);
            ft.commit();
        });
        btnDetails.setOnClickListener(v -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout, new SmellsDetailsFragment());
            ft.addToBackStack(null);
            ft.commit();
        });
        btnEdit.setOnClickListener(v -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout, new FragmentEdit());
            ft.addToBackStack(null);
            ft.commit();
        });
        btnGame.setOnClickListener(v -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout, new ScemtGameFragment());
            ft.addToBackStack(null);
            ft.commit();
        });

        return view;
    }
}
