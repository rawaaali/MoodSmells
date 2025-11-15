package com.example.moodsmells;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;


public class SmellsListFragment extends Fragment {


        private RecyclerView recyclerView;
        private SearchView searchView;
        private SmellsListAdapter adapter;
        private FirebaseServices fbs;

        private ArrayList<SmellsItem> smellsList = new ArrayList<>();
        private ArrayList<SmellsItem> filteredList = new ArrayList<>();

        public SmellsListFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_smells_list, container, false);
        }

        @Override
        public void onStart() {
            super.onStart();
            init();
            loadSmellsFromFirebase();
        }

        private void init() {
            recyclerView = getView().findViewById(R.id.rvSmellslist);
            searchView = getView().findViewById(R.id.srchViewSmells);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);

            fbs = FirebaseServices.getInstance();

            adapter = new SmellsListAdapter(getActivity(), smellsList);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(position -> {
                Toast.makeText(getActivity(),
                        "Clicked: " + smellsList.get(position).getName(),
                        Toast.LENGTH_SHORT).show();
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    applyFilter(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    applyFilter(newText);
                    return true;
                }
            });
        }

        private void loadSmellsFromFirebase() {
            fbs.getFirestore().collection("memories" )

                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            smellsList.clear();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                SmellsItem sm = doc.toObject(SmellsItem.class);
                                smellsList.add(sm);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
        }

        private void applyFilter(String query) {
            if (query.trim().isEmpty()) {
                adapter = new SmellsListAdapter(getActivity(), smellsList);
                recyclerView.setAdapter(adapter);
                return;
            }

            filteredList.clear();

            for (SmellsItem sm : smellsList) {
                if (sm.getName().toLowerCase().contains(query.toLowerCase()) ||
                        sm.getMood().toLowerCase().contains(query.toLowerCase()) ||
                        sm.getType().toLowerCase().contains(query.toLowerCase()) ||
                        sm.getYear().toLowerCase().contains(query.toLowerCase())
                ) {
                    filteredList.add(sm);
                }
            }

            adapter = new SmellsListAdapter(getActivity(), filteredList);
            recyclerView.setAdapter(adapter);
        }
    }
