package com.example.moodsmells;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class SignupFragment extends Fragment {

        private static final int GALLERY_REQUEST_CODE = 123;
        private EditText etUsername, etPassword;
        private Button btnSignup;
        private FirebaseServices fbs;
        ImageView ivUserPhoto;
        private Utils utils;



        public SignupFragment() {
            // Required empty public constructor
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_signup, container, false);
        }

        @Override
        public void onStart() {
            super.onStart();
            etUsername = getView().findViewById(R.id.etUsernameSignup);
            etPassword = getView().findViewById(R.id.etPasswordSignup);
            btnSignup = getView().findViewById(R.id.btnSignupSignup);

            // FIX: نُفضل تهيئة Firebase هنا وليس داخل زر التسجيل
            fbs = FirebaseServices.getInstance();

            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // data validation
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    // FIX: تغيير && إلى ||
                    if (username.trim().isEmpty() || password.trim().isEmpty()) {
                        Toast.makeText(getActivity(), "some fields are empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // FIX: التأكد أن اسم المستخدم هو Email
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                        Toast.makeText(getActivity(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // signup procedure
                    fbs.getAuth().createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getActivity(), "you have successfully signup!", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Toast.makeText(getActivity(), "failed to signup!check user or password!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });

            ivUserPhoto = getView().findViewById(R.id.ivPhotoSignupFragment);
            utils = Utils.getInstance();

            ivUserPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery();
                }
            });
        }

        private void openGallery() {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);


            if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {

                Uri selectedImageUri = data.getData();

                ivUserPhoto.setImageURI(selectedImageUri);


                Utils.getInstance().uploadImage(getActivity(), selectedImageUri);
            }
        }
    }
