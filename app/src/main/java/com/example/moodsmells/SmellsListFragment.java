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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class SmellsListFragment extends Fragment {


    private RecyclerView recyclerView;
    private FirebaseServices fbs;
    private MyAdapter MyAdapter;
    private ArrayList<Memory> list, filteredList;
    private FloatingActionButton btnAdd;
    private SearchView srchView;
    private Button favIcon;
    private Map<String, Memory> memoryMap;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SmellsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SmellsListFragment newInstance(String param1, String param2) {
        SmellsListFragment fragment = new SmellsListFragment();
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
        //carsMap = new HashMap<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        filteredList = new ArrayList<>();
        //carsMap = getCarsMap();
        MyAdapter  = new MyAdapter(getContext(), list);
        recyclerView.setAdapter(MyAdapter);


        MyAdapter.setOnItemClickListener(position -> {
            // Handle item click here
            Memory selectedItem = (filteredList != null && !filteredList.isEmpty()) ? filteredList.get(position) : list.get(position);
            Toast.makeText(getActivity(), "Clicked: " + selectedItem.getSmellName(), Toast.LENGTH_SHORT).show();
            Bundle args = new Bundle();
            args.putParcelable("memory", selectedItem);
            SmellsDetailsFragment cd = new SmellsDetailsFragment();
            cd.setArguments(args);
            FragmentTransaction ft=getParentFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout,cd);
            ft.addToBackStack(null);
            ft.commit();
        });

        fbs.getFire().collection("memories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    Memory memory= dataSnapshot.toObject(Memory.class);
                    list.add(memory);
                }


                MyAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        srchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //applyFilter(newText);
                return false;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddSmellsFragment();
            }
        });
        btnAdd.setVisibility(View.INVISIBLE); // currently hidden

        ((MainActivity) requireActivity()).pushFragment(new SmellsListFragment());
    }

    private void applyFilter(String query) {
        // TODO: add onBackspace - old and new query
        if (query.trim().isEmpty())
        {
            MyAdapter = new MyAdapter(getContext(), list);
            recyclerView.setAdapter(MyAdapter);
            //myAdapter.notifyDataSetChanged();
            return;
        }
        filteredList.clear();
        for(Memory memory : list)
        {
            if (memory.getSmellName().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getSmellIntensity().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getMemoryType().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getPhone().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getSmellColor().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getMemoryId().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getSmellSource().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getMemoryDate().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getSmellCategory().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getMemoryDescription().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getMemoryLocation().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getSmellStrength().toLowerCase().contains(query.toLowerCase()) ||
                    memory.getSmellStyle().toLowerCase().contains(query.toLowerCase())||
                    memory.getFeeling().toLowerCase().contains(query.toLowerCase())||
                    memory.getPhoto().toLowerCase().contains(query.toLowerCase()))


            {
                filteredList.add(memory);
            }
        }
        if (filteredList.size() == 0)
        {
            showNoDataDialogue();
            return;
        }
        MyAdapter = new MyAdapter(getContext(), filteredList);
        recyclerView.setAdapter(MyAdapter);
        MyAdapter= new MyAdapter(getActivity(),filteredList);
        recyclerView.setAdapter(MyAdapter);
        MyAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                String selectedItem = filteredList.get(position).getSmellName();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("memory", filteredList.get(position)); // or use Parcelable for better performance
                SmellsDetailsFragment cd = new SmellsDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout,cd);
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_smells_list, container, false);
    }

    public void gotoAddSmellsFragment() {
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AddSmellsFragment());
        ft.commit();
    }

}