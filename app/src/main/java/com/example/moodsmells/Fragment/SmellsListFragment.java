package com.example.moodsmells.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moodsmells.Adapters.MyAdapter;
import com.example.moodsmells.Class.Memory;
import com.example.moodsmells.FirebaseServices;
import com.example.moodsmells.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SmellsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseServices fbs;
    private MyAdapter adapter;

    private ArrayList<Memory> list;
    private ArrayList<Memory> filteredList;

    private FloatingActionButton btnAdd;
    private SearchView srchView;

    public SmellsListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_smells_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        if (getView() == null) return;

        recyclerView = getView().findViewById(R.id.rvMemoryList);
        btnAdd = getView().findViewById(R.id.btnAddMemory);
        srchView = getView().findViewById(R.id.srchViewSmellsList);

        fbs = FirebaseServices.getInstance();

        list = new ArrayList<>();
        filteredList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new MyAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(position -> {

            Memory selectedItem =
                    (filteredList != null && !filteredList.isEmpty())
                            ? filteredList.get(position)
                            : list.get(position);

            Toast.makeText(getContext(),
                    selectedItem.getSmellName(),
                    Toast.LENGTH_SHORT).show();

            Bundle args = new Bundle();
            args.putParcelable("memory", selectedItem);

            SmellsDetailsFragment detailsFragment = new SmellsDetailsFragment();
            detailsFragment.setArguments(args);

            FragmentTransaction ft = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();

            ft.replace(R.id.frameLayout, detailsFragment);
            ft.addToBackStack(null);
            ft.commit();
        });


        fbs.getFire()
                .collection("memories")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    list.clear();

                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        Memory memory = doc.toObject(Memory.class);
                        list.add(memory);
                    }

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(),
                                "Error loading data",
                                Toast.LENGTH_SHORT).show()
                );


        srchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        btnAdd.setOnClickListener(v -> gotoAddSmellsFragment());
        btnAdd.setVisibility(View.INVISIBLE);
    }

    private void applyFilter(String query) {

        if (query.trim().isEmpty()) {
            adapter = new MyAdapter(getContext(), list);
            recyclerView.setAdapter(adapter);
            return;
        }

        filteredList.clear();

        for (Memory memory : list) {

            if (memory.getSmellName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(memory);
            }
        }

        if (filteredList.isEmpty()) {
            showNoDataDialogue();
            return;
        }

        adapter = new MyAdapter(getContext(), filteredList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {

            Memory selected = filteredList.get(position);

            Bundle args = new Bundle();
            args.putParcelable("memories", selected);

            SmellsDetailsFragment fragment = new SmellsDetailsFragment();
            fragment.setArguments(args);

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void showNoDataDialogue() {
        new AlertDialog.Builder(getContext())
                .setTitle("No Results")
                .setMessage("Try again!")
                .show();
    }

    private void gotoAddSmellsFragment() {
        FragmentTransaction ft = requireActivity()
                .getSupportFragmentManager()
                .beginTransaction();

        ft.replace(R.id.frameLayout, new AddSmellsFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
}