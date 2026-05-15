package com.example.moodsmells;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import com.example.moodsmells.R;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.example.moodsmells.User;


import java.util.UUID;

public class FragmentEdit extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText etFirstName, etLastName, etAddress, etPhone;
    private static final int GALLERY_REQUEST_CODE = 134;

    private Button btnUpdate;
    private ImageView ivUser;

    private FirebaseServices fbs;
    private Utils utils;

    private String imageStr;

    private boolean flagAlreadyFilled = false;

    public FragmentEdit() {
        // Required empty public constructor
    }

    public static FragmentEdit newInstance(String param1, String param2) {
        FragmentEdit fragment = new FragmentEdit();

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
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        init(view);

        return view;
    }

    private void init(View view) {

        fbs = FirebaseServices.getInstance();

        utils = Utils.getInstance();

        etFirstName = view.findViewById(R.id.etFirstnameUserDetailsEdit);
        etLastName = view.findViewById(R.id.etLastnameUserDetailsEdit);
        etAddress = view.findViewById(R.id.etAddressUserDetailsEdit);
        etPhone = view.findViewById(R.id.etPhoneUserDetailsEdit);

        ivUser = view.findViewById(R.id.ivUserUserDetailsEdit);

        btnUpdate = view.findViewById(R.id.btnUpdateUserDetailsEdit);

        btnUpdate.setOnClickListener(view1 -> {

            String firstname = etFirstName.getText().toString().trim();
            String lastname = etLastName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (firstname.isEmpty() ||
                    lastname.isEmpty() ||
                    address.isEmpty() ||
                    phone.isEmpty()) {

                Toast.makeText(getActivity(),
                        "Some fields are empty",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            User current = fbs.getCurrentUser();

            if (current != null) {

                boolean imageChanged = false;

                if (fbs.getSelectedImageURL() != null) {

                    imageChanged = !current.getPhoto()
                            .equals(fbs.getSelectedImageURL().toString());
                }

                if (!current.getFirstName().equals(firstname) ||
                        !current.getLastName().equals(lastname) ||
                        !current.getAddress().equals(address) ||
                        !current.getPhone().equals(phone) ||
                        imageChanged) {

                    User user;

                    if (fbs.getSelectedImageURL() != null) {

                        user = new User(
                                firstname,
                                lastname,
                                fbs.getAuth().getCurrentUser().getEmail(),
                                address,
                                phone,
                                fbs.getSelectedImageURL().toString()
                        );

                    } else {

                        user = new User(
                                firstname,
                                lastname,
                                fbs.getAuth().getCurrentUser().getEmail(),
                                address,
                                phone,
                                ""
                        );
                    }

                    fbs.updateUser(user);

                    utils.showMessageDialog(
                            getActivity(),
                            "Data updated successfully!"
                    );

                    fbs.reloadInstance();

                } else {

                    utils.showMessageDialog(
                            getActivity(),
                            "No changes!"
                    );
                }
            }
        });

        ivUser.setOnClickListener(v -> openGallery());

        fillUserData();

        flagAlreadyFilled = true;
    }

    private void fillUserData() {

        if (flagAlreadyFilled)
            return;

        User current = fbs.getCurrentUser();

        if (current != null) {

            etFirstName.setText(current.getFirstName());
            etLastName.setText(current.getLastName());
            etAddress.setText(current.getAddress());
            etPhone.setText(current.getPhone());

            if (current.getPhoto() != null &&
                    !current.getPhoto().isEmpty()) {
                Glide.with(this)
                        .load(current.getPhoto())
                        .centerCrop()
                        .override(300,300)
                        .into(ivUser);

                fbs.setSelectedImageURL(
                        Uri.parse(current.getPhoto())
                );
            }
        }
    }

    public void openGallery() {

        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );

        startActivityForResult(
                galleryIntent,
                GALLERY_REQUEST_CODE
        );
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK &&
                data != null) {

            btnUpdate.setEnabled(false);

            Uri imageUri = data.getData();

            Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .override(300, 300)
                    .into(ivUser);

            uploadImage(imageUri);
        }
    }

    public void uploadImage(Uri selectedImageUri) {

        if (selectedImageUri == null) {

            Toast.makeText(getActivity(),
                    "Please choose an image first",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        btnUpdate.setEnabled(false);

        imageStr = "images/" + UUID.randomUUID() + ".jpg";

        StorageReference imageRef =
                fbs.getStorage()
                        .getReference()
                        .child(imageStr);

        imageRef.putFile(selectedImageUri)

                .addOnSuccessListener(taskSnapshot ->

                        imageRef.getDownloadUrl()

                                .addOnSuccessListener(uri -> {

                                    fbs.setSelectedImageURL(uri);

                                    Toast.makeText(
                                            getActivity(),
                                            "Image uploaded successfully",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    btnUpdate.setEnabled(true);

                                })

                                .addOnFailureListener(e -> {

                                    Toast.makeText(
                                            getActivity(),
                                            e.getMessage(),
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    btnUpdate.setEnabled(true);

                                })

                )

                .addOnFailureListener(e -> {

                    Toast.makeText(
                            getActivity(),
                            "Failed to upload image",
                            Toast.LENGTH_SHORT
                    ).show();

                    btnUpdate.setEnabled(true);

                });
    }
}