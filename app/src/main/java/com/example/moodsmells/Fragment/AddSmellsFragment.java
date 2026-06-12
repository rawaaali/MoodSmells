package com.example.moodsmells.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.moodsmells.Class.Memory;
import com.example.moodsmells.FirebaseServices;
import com.example.moodsmells.R;
import com.example.moodsmells.Utils;

public class AddSmellsFragment extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 123;
    ImageView img;

    private EditText etSmellName, etSmellIntensity, etMemoryType, etPhone,
            etMemoryId, etSmellSource, etSmellCategory, etMemoryDescription,
            etMemoryLocation, etSmellStrength, etSmellStyle, etFeeling;

    private Button btnAddMemory;

    private FirebaseServices fbs;
    private Utils utils;

    Spinner smellColorSpinner, memoryDateSpinner;

    String[] colors={"select smell color","black","white","gray","green","red","light blue","other..."};

    String[] dates={"select memory date","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014",
            "2013","2012","2011","2010","2009","2008","2007","2006","2005","2004",
            "2003","2002","2001","2000","other..."};

    private View view;

    public AddSmellsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_smells, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init(){

        fbs=FirebaseServices.getInstance();
        utils=Utils.getInstance();

        etSmellName=view.findViewById(R.id.etSmellName);
        etSmellIntensity=view.findViewById(R.id.etSmellIntensity);
        etMemoryType=view.findViewById(R.id.etMemoryType);
        etPhone=view.findViewById(R.id.etPhone);
        etMemoryId=view.findViewById(R.id.etMemoryId);
        etSmellSource=view.findViewById(R.id.etSmellSource);
        etSmellCategory=view.findViewById(R.id.etSmellCategory);
        etMemoryDescription=view.findViewById(R.id.etMemoryDescription);
        etMemoryLocation=view.findViewById(R.id.etMemoryLocation);
        etSmellStrength=view.findViewById(R.id.etSmellStrength);
        etSmellStyle=view.findViewById(R.id.etSmellStyle);
        etFeeling=view.findViewById(R.id.etFeeling);

        btnAddMemory=view.findViewById(R.id.btnAddMemory);
        img=view.findViewById(R.id.ivMemoryImage);

        smellColorSpinner=view.findViewById(R.id.smellColorSpinner);
        ArrayAdapter<String>adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,colors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smellColorSpinner.setAdapter(adapter);

        memoryDateSpinner=view.findViewById(R.id.memoryDateSpinner);
        ArrayAdapter<String>adapter2=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,dates);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memoryDateSpinner.setAdapter(adapter2);

        btnAddMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFirestore();
            }
        });

        img.setOnClickListener(v -> openGallery());
    }

    private void addToFirestore(){

        String smellName,smellIntensity,memoryType,phone,smellColor,
                memoryId,smellSource,memoryDate,smellCategory,memoryDescription,
                memoryLocation,smellStrength,smellStyle,feeling;

        smellName=etSmellName.getText().toString();
        smellIntensity=etSmellIntensity.getText().toString();
        memoryType=etMemoryType.getText().toString();
        phone=etPhone.getText().toString();
        smellColor=smellColorSpinner.getSelectedItem().toString();
        memoryId=etMemoryId.getText().toString();
        smellSource=etSmellSource.getText().toString();
        memoryDate=memoryDateSpinner.getSelectedItem().toString();
        smellCategory=etSmellCategory.getText().toString();
        memoryDescription=etMemoryDescription.getText().toString();
        memoryLocation=etMemoryLocation.getText().toString();
        smellStrength=etSmellStrength.getText().toString();
        smellStyle=etSmellStyle.getText().toString();
        feeling=etFeeling.getText().toString();

        if(smellName.trim().isEmpty() ||
                smellIntensity.trim().isEmpty() ||
                memoryType.trim().isEmpty() ||
                smellColor.trim().isEmpty() ||
                memoryId.trim().isEmpty() ||
                smellSource.trim().isEmpty() ||
                memoryDate.trim().isEmpty() ||
                smellCategory.trim().isEmpty() ||
                memoryDescription.trim().isEmpty() ||
                memoryLocation.trim().isEmpty() ||
                smellStrength.trim().isEmpty() ||
                smellStyle.trim().isEmpty() ||
                feeling.trim().isEmpty())
        {
            Toast.makeText(getActivity(),"sorry some data missing!",Toast.LENGTH_SHORT).show();
            return;
        }

        Memory memory;

        if (fbs.getSelectedImageURL()==null)
        {
            memory=new Memory(smellName,smellIntensity,memoryType,phone,smellColor,
                    memoryId,smellSource,memoryDate,smellCategory,memoryDescription,
                    memoryLocation,smellStrength,smellStyle,feeling,"");
        }
        else
        {
            memory=new Memory(smellName,smellIntensity,memoryType,phone,smellColor,
                    memoryId,smellSource,memoryDate,smellCategory,memoryDescription,
                    memoryLocation,smellStrength,smellStyle,feeling,
                    fbs.getSelectedImageURL().toString());
        }

        fbs.getFire().collection("memories").add(memory)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getActivity(),"Memory Added Successfully",Toast.LENGTH_SHORT).show();
                    gotoAdminFragment();
                })
                .addOnFailureListener(e -> Log.e("addToFirestore",e.getMessage()));
    }

    private void openGallery(){
        Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==GALLERY_REQUEST_CODE &&
                resultCode==Activity.RESULT_OK &&
                data!=null){
            Uri selectedImageUri=data.getData();
            img.setImageURI(selectedImageUri);
            utils.uploadImage(getActivity(),selectedImageUri);
        }
    }

    public void gotoAdminFragment(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framelayout, new AdminFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
}