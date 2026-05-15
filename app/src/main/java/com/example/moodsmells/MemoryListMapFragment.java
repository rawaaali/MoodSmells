package com.example.moodsmells;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MemoryListMapFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseServices fbs;
    private SmellsListAdapter myAdapter;
    private SearchView srchView;
    private ArrayList<Memory> memoryList, filteredList;

    public MemoryListMapFragment() {}

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        View view = getView();
        if (view == null) return;

        recyclerView = view.findViewById(R.id.rvMemoryListMap);
        srchView = view.findViewById(R.id.srchViewMemoryList);

        fbs = FirebaseServices.getInstance();
        fbs.setUserChangeFlag(false);

        memoryList = new ArrayList<>();
        filteredList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myAdapter = new SmellsListAdapter(getActivity(), memoryList);
        recyclerView.setAdapter(myAdapter);

        getMemory();

        myAdapter.setOnItemClickListener(position -> {
            Memory selectedItem = (filteredList != null && !filteredList.isEmpty()) ? filteredList.get(position) : memoryList.get(position);
            Toast.makeText(getActivity(), "Clicked: " + selectedItem.getSmellName(), Toast.LENGTH_SHORT).show();

            Bundle args = new Bundle();
            args.putParcelable("memory", selectedItem);

            SmellsDetailsFragment cd = new SmellsDetailsFragment();
            cd.setArguments(args);

            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout, cd);
            ft.addToBackStack(null);
            ft.commit();
        });

        srchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                applyFilter(newText);
                return false;
            }
        });
    }

    private void applyFilter(String query) {
        if (query == null || query.trim().isEmpty()) {
            myAdapter = new SmellsListAdapter(getActivity(), memoryList);
            recyclerView.setAdapter(myAdapter);
            return;
        }

        filteredList.clear();
        for (Memory item : memoryList) {
            if (item.getSmellName().toLowerCase().contains(query.toLowerCase()) ||
                item.getSmellColor().toLowerCase().contains(query.toLowerCase()) ||
                item.getFeeling().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            showNoDataDialogue();
        }

        myAdapter = new SmellsListAdapter(getActivity(), filteredList);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(position -> {
            Memory selectedItem = filteredList.get(position);
            Bundle args = new Bundle();
            args.putParcelable("memory", selectedItem);
            SmellsDetailsFragment cd = new SmellsDetailsFragment();
            cd.setArguments(args);
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, cd).addToBackStack(null).commit();
        });
    }

    private void showNoDataDialogue() {
        new AlertDialog.Builder(getContext())
                .setTitle("No Results")
                .setMessage("Try again!")
                .setPositiveButton("OK", null)
                .show();
    }

    public void getMemory() {
        fbs.getFire().collection("memories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        memoryList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Memory item = document.toObject(Memory.class);
                            item.setId(document.getId());
                            memoryList.add(item);
                        }
                        myAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memory_list_map, container, false);
    }
}
