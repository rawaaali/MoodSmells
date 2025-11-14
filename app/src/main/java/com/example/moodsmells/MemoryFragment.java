package com.example.moodsmells;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;



public class MemoryFragment extends Fragment {

    private EditText editTextDate, editTextName, editTextSmell, editTextMood;
    private Button buttonSave;
    private FirebaseFirestore db;

    public MemoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory, container, false);

        editTextDate = view.findViewById(R.id.editTextDate);
        editTextName = view.findViewById(R.id.editTextName);
        editTextSmell = view.findViewById(R.id.editTextSmell);
        editTextMood = view.findViewById(R.id.editTextMood);
        buttonSave = view.findViewById(R.id.buttonSave);

        db = FirebaseFirestore.getInstance();

        buttonSave.setOnClickListener(v -> saveMemory());

        return view;
    }

    private void saveMemory() {
        String date = editTextDate.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String smell = editTextSmell.getText().toString().trim();
        String mood = editTextMood.getText().toString().trim();

        if (date.isEmpty() || name.isEmpty() || smell.isEmpty() || mood.isEmpty()) {
            Toast.makeText(getActivity(), "يجب ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        Memory memory = new Memory(date, name, smell, mood);

        db.collection("memories")
                .add(memory)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(getActivity(), "تم الحفظ بنجاح", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(getActivity(), "خطأ: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}