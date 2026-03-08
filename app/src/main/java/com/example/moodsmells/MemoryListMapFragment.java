package com.example.moodsmells;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemoryListMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoryListMapFragment extends Fragment {

        private RecyclerView recyclerView;
        private ImageView ivProfile;
        private FirebaseServices fbs;
        private SmellsListAdapter myAdapter;
        private SearchView srchView;
        private ArrayList<Memory> memories, filteredList;

        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        private String mParam1;
        private String mParam2;

        public MemoryListMapFragment() {
            // Required empty public constructor
        }

        public static MemoryListMapFragment newInstance(String param1, String param2) {
            MemoryListMapFragment fragment = new MemoryListMapFragment();
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
            recyclerView = getView().findViewById(R.id.rvMemoryListMap);
            ivProfile = getView().findViewById(R.id.ivProfileMemoryListMapFragment);
            fbs = FirebaseServices.getInstance();
            fbs.setUserChangeFlag(false);

            memories = new ArrayList<>();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            memories = getMemories();
            myAdapter = new SmellsListAdapter(getActivity(), memories);

            filteredList = new ArrayList<>();

            myAdapter.setOnItemClickListener(new SmellsListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Memory selectedMemory = memories.get(position);
                    Toast.makeText(getActivity(), "Clicked: " + selectedMemory.getSmellName(), Toast.LENGTH_SHORT).show();

                    Bundle args = new Bundle();
                    args.putParcelable("memory", selectedMemory);

                    SmellsDetailsFragment md = new SmellsDetailsFragment();
                    md.setArguments(args);

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout, md);
                    ft.commit();
                }
            });

            srchView = getView().findViewById(R.id.srchViewMemoryListMapFragment);
            srchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    applyFilter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // يمكن تفعيل البحث أثناء الكتابة هنا
                    return false;
                }
            });

            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoFragmentEdit();
                }
            });
        }

        private void applyFilter(String query) {
            if (query.trim().isEmpty()) {
                myAdapter = new SmellsListAdapter(getContext(), memories);
                recyclerView.setAdapter(myAdapter);
                return;
            }

            filteredList.clear();
            for (Memory memory : memories) {
                if (memory.getSmellName().toLowerCase().contains(query.toLowerCase()) ||
                        memory.getMemoryType().toLowerCase().contains(query.toLowerCase()) ||
                        memory.getFeeling().toLowerCase().contains(query.toLowerCase()) ||
                        memory.getMemoryDate().toLowerCase().contains(query.toLowerCase()) ||
                        memory.getMemoryLocation().toLowerCase().contains(query.toLowerCase()) ||
                        memory.getSmellCategory().toLowerCase().contains(query.toLowerCase()) ||
                        memory.getSmellStyle().toLowerCase().contains(query.toLowerCase())) {

                    filteredList.add(memory);
                }
            }

            if (filteredList.size() == 0) {
                showNoDataDialogue();
                return;
            }

            myAdapter = new SmellsListAdapter(getContext(), filteredList);
            recyclerView.setAdapter(myAdapter);

            myAdapter.setOnItemClickListener(new SmellsListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Memory selectedMemory = filteredList.get(position);
                    Bundle args = new Bundle();
                    args.putParcelable("memory", selectedMemory);
                    SmellsDetailsFragment md = new SmellsDetailsFragment();
                    md.setArguments(args);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout, md);
                    ft.commit();
                }
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
            return inflater.inflate(R.layout.fragment_memory_list_map, container, false);
        }

        public void gotoFragmentEdit() {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout, new FragmentEdit());
            ft.commit();
        }

        public ArrayList<Memory> getMemories() {
            ArrayList<Memory> memories = new ArrayList<>();

            try {
                fbs.getFire().collection("memories")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    memories.clear();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        memories.add(document.toObject(Memory.class));
                                    }

                                    SmellsListAdapter adapter = new SmellsListAdapter(getActivity(), memories);
                                    recyclerView.setAdapter(adapter);
                                }
                            }
                        });
            } catch (Exception e) {
                Log.e("getMemories(): ", e.getMessage());
            }

            return memories;
        }

        @Override
        public void onPause() {
            super.onPause();
            User u = fbs.getCurrentUser();
            if (u != null && fbs.isUserChangeFlag())
                fbs.updateUser(u);
        }
    }