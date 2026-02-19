package com.example.moodsmells;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;

public class MemoryFragment extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 123;

    private EditText editTextDate, editTextName, editTextSmell, editTextMood;
    private Button buttonSave;
    private FirebaseFirestore db;
    private ImageView img;
    private String imageStr;
    private Utils utils;

    public MemoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_memory, container, false);

        // ربط العناصر من الواجهة
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextName = view.findViewById(R.id.editTextName);
        editTextSmell = view.findViewById(R.id.editTextSmell);
        editTextMood = view.findViewById(R.id.editTextMood);
        buttonSave = view.findViewById(R.id.buttonSave);
        img = view.findViewById(R.id.img);   // ✔️ تم نقلها للأعلى قبل الاستخدام

        // Firebase + Utils
        db = FirebaseFirestore.getInstance();
        utils = Utils.getInstance();

        // أزرار الاستماع
        buttonSave.setOnClickListener(v -> saveMemory());
        img.setOnClickListener(v -> openGallery());

        return view;
    }


    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK &&
                data != null) {

            Uri selectedImageUri = data.getData();
            img.setImageURI(selectedImageUri);

            // رفع الصورة
            utils.uploadImage(getActivity(), selectedImageUri);
        }
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

        // إنشاء الكائن
        Memory memory = new Memory(date, name, smell, mood);

        // حفظ الـ Memory في Firestore
        db.collection("memory")
                .add(memory)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(getActivity(), "تم الحفظ بنجاح", Toast.LENGTH_SHORT).show()
                );

        // الانتقال لصفحة القائمة
        FragmentTransaction ft = getActivity()
                .getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.frameLayoutMain, new SmellsListFragment());
        ft.commit();
    }
}