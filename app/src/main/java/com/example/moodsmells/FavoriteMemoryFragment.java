package com.example.moodsmells;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.moodsmells.User;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteMemoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteMemoryFragment extends Fragment {



        private RecyclerView recyclerView;
        private FirebaseServices fbs;
        private MyAdapter myAdapter;
        private SearchView srchView;
        private ArrayList<Memory> memories, filteredList;

        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        private String mParam1;
        private String mParam2;

        public FavoriteMemoryFragment() {
            // Required empty constructor
        }

        public static FavoriteMemoryFragment newInstance(String param1, String param2) {
            FavoriteMemoryFragment fragment = new FavoriteMemoryFragment();
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
        public void onStart() {
            super.onStart();
            init();
        }

        private void init() {
            recyclerView = getView().findViewById(R.id.rvMemoryList);
            srchView = getView().findViewById(R.id.srchViewFavoriteMemoryFragment);
            fbs = FirebaseServices.getInstance();

            memories = new ArrayList<>();
            filteredList = new ArrayList<>();

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            myAdapter = new MyAdapter(getActivity(), memories);
            recyclerView.setAdapter(myAdapter);

            myAdapter.setOnItemClickListener(position -> {
                Memory selectedMemory = memories.get(position);
                Toast.makeText(getActivity(), "Clicked: " + selectedMemory.getSmellName(), Toast.LENGTH_SHORT).show();

                Bundle args = new Bundle();
                args.putParcelable("memory", selectedMemory);
                SmellsDetailsFragment md = new SmellsDetailsFragment();
                md.setArguments(args);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.framelayout, md);
                ft.commit();
            });

            loadFavoriteMemories();

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
        }

        private void loadFavoriteMemories() {
            User u = fbs.currentUser();
            if (u == null) return;

            fbs.getFire().collection("memories")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            memories.clear();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Memory memory = doc.toObject(Memory.class);
                                if (u.getFavorites().contains(memory.getMemoryId())) {
                                    memories.add(memory);
                                }
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    });
        }

        private void applyFilter(String query) {
            if (query.trim().isEmpty()) {
                myAdapter = new MyAdapter(getContext(), memories);
                recyclerView.setAdapter(myAdapter);
                return;
            }

            filteredList.clear();
            for (Memory mem : memories) {
                if ((mem.getSmellName() != null && mem.getSmellName().toLowerCase().contains(query.toLowerCase())) ||
                        (mem.getMemoryDescription() != null && mem.getMemoryDescription().toLowerCase().contains(query.toLowerCase())) ||
                        (mem.getMemoryLocation() != null && mem.getMemoryLocation().toLowerCase().contains(query.toLowerCase())) ||
                        (mem.getMemoryDate() != null && mem.getMemoryDate().toLowerCase().contains(query.toLowerCase()))) {
                    filteredList.add(mem);
                }
            }

            if (filteredList.size() == 0) {
                showNoDataDialogue();
                return;
            }

            myAdapter = new MyAdapter(getContext(), filteredList);
            recyclerView.setAdapter(myAdapter);

            myAdapter.setOnItemClickListener(position -> {
                Memory selectedMemory = filteredList.get(position);
                Toast.makeText(getActivity(), "Clicked: " + selectedMemory.getSmellName(), Toast.LENGTH_SHORT).show();

                Bundle args = new Bundle();
                args.putParcelable("memory", selectedMemory);
                SmellsDetailsFragment md = new SmellsDetailsFragment();
                md.setArguments(args);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.framelayout, md);
                ft.commit();
            });
        }

        private void showNoDataDialogue() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("No Results");
            builder.setMessage("Try again!");
            builder.show();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_favorite_memory, container, false);
        }
    }