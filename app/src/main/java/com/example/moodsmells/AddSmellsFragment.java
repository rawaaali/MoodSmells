package com.example.moodsmells;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;

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

    public AddSmellsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_smells, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init(){

        fbs=FirebaseServices.getInstance();
        utils=Utils.getInstance();

        etSmellName=getView().findViewById(R.id.etSmellName);
        etSmellIntensity=getView().findViewById(R.id.etSmellIntensity);
        etMemoryType=getView().findViewById(R.id.etMemoryType);
        etPhone=getView().findViewById(R.id.etPhone);
        etMemoryId=getView().findViewById(R.id.etMemoryId);
        etSmellSource=getView().findViewById(R.id.etSmellSource);
        etSmellCategory=getView().findViewById(R.id.etSmellCategory);
        etMemoryDescription=getView().findViewById(R.id.etMemoryDescription);
        etMemoryLocation=getView().findViewById(R.id.etMemoryLocation);
        etSmellStrength=getView().findViewById(R.id.etSmellStrength);
        etSmellStyle=getView().findViewById(R.id.etSmellStyle);
        etFeeling=getView().findViewById(R.id.etFeeling);

        btnAddMemory=getView().findViewById(R.id.btnAddMemory);
        img=getView().findViewById(R.id.ivMemoryImage);

        smellColorSpinner=getView().findViewById(R.id.smellColorSpinner);
        ArrayAdapter<String>adapter=new ArrayAdapter<>(getActivity(),R.layout.item,colors);
        smellColorSpinner.setAdapter(adapter);

        memoryDateSpinner=getView().findViewById(R.id.memoryDateSpinner);
        ArrayAdapter<String>adapter2=new ArrayAdapter<>(getActivity(),R.layout.item,dates);
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
                    gotoSmellsList();
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

        if(requestCode==GALLERY_REQUEST_CODE && resultCode==getActivity().RESULT_OK && data!=null){
            Uri selectedImageUri=data.getData();
            img.setImageURI(selectedImageUri);
            utils.uploadImage(getActivity(),selectedImageUri);
        }
    }

    public void gotoSmellsList(){
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framelayout,new MemoryListMapFragment());
        ft.commit();
    }
}