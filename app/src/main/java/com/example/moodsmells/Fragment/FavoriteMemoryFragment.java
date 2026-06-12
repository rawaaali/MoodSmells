package com.example.moodsmells.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.moodsmells.Adapters.SmellsListAdapter;
import com.example.moodsmells.Class.Memory;
import com.example.moodsmells.Class.User;
import com.example.moodsmells.FirebaseServices;
import com.example.moodsmells.R;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FavoriteMemoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseServices fbs;
    private SmellsListAdapter myAdapter;
    private SearchView srchView;
    private ArrayList<Memory> memoryList, filteredList;

    public FavoriteMemoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_memory, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        View view = getView();
        if (view == null) return;

        recyclerView = view.findViewById(R.id.rvMemoryList);
        srchView = view.findViewById(R.id.srchViewFavoriteMemory); // Match layout ID

        fbs = FirebaseServices.getInstance();
        memoryList = new ArrayList<>();
        filteredList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myAdapter = new SmellsListAdapter(getActivity(), memoryList);
        recyclerView.setAdapter(myAdapter);

        getFavoriteMemories();

        myAdapter.setOnItemClickListener(position -> {
            Memory selectedItem = (filteredList.isEmpty()) ? memoryList.get(position) : filteredList.get(position);
            Bundle args = new Bundle();
            args.putParcelable("memory", selectedItem);
            SmellsDetailsFragment detailsFragment = new SmellsDetailsFragment();
            detailsFragment.setArguments(args);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        if (srchView != null) {
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
    }

    private void getFavoriteMemories() {
        fbs.getFire().collection("memories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        memoryList.clear();
                        User currentUser = fbs.getCurrentUser();
                        if (currentUser != null && currentUser.getFavorites() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Memory memory = document.toObject(Memory.class);
                                memory.setId(document.getId());
                                if (currentUser.getFavorites().contains(memory.getId())) {
                                    memoryList.add(memory);
                                }
                            }
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void applyFilter(String query) {
        if (query == null || query.trim().isEmpty()) {
            filteredList.clear();
            myAdapter = new SmellsListAdapter(getActivity(), memoryList);
            recyclerView.setAdapter(myAdapter);
            return;
        }

        filteredList.clear();
        for (Memory item : memoryList) {
            if (item.getSmellName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        myAdapter = new SmellsListAdapter(getActivity(), filteredList);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(position -> {
            Memory selectedItem = filteredList.get(position);
            Bundle args = new Bundle();
            args.putParcelable("memory", selectedItem);
            SmellsDetailsFragment detailsFragment = new SmellsDetailsFragment();
            detailsFragment.setArguments(args);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
