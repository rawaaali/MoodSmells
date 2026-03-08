package com.example.moodsmells;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;



public class SmellsDetailsFragment extends Fragment {



        private static final int PERMISSION_SEND_SMS = 1;
        private static final int REQUEST_CALL_PERMISSION = 2;

        private FirebaseServices fbs;

        private TextView tvSmellName,tvSmellIntensity,tvMemoryType,tvColor,tvMemoryId,
                tvSmellSource,tvMemoryDate,tvSmellCategory,tvMemoryDescription,
                tvMemoryLocation,tvSmellStrength,tvSmellStyle,tvFeeling, tvPhone;

        private ImageView ivMemoryPhoto;

        private Memory myMemory;

        private Button sendSMSButton, btnWhatsapp, btnCall;

        private boolean isEnlarged = false;

        public SmellsDetailsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_smells_details, container, false);
        }

        @Override
        public void onStart() {
            super.onStart();

            init();

            ImageView ivMemoryPhoto = getView().findViewById(R.id.ivMemoryDetailsFragment);

            ivMemoryPhoto.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    ViewGroup.LayoutParams layoutParams = ivMemoryPhoto.getLayoutParams();

                    if (isEnlarged) {
                        layoutParams.height = 500;
                    } else {
                        layoutParams.height = 2200;
                    }

                    ivMemoryPhoto.setLayoutParams(layoutParams);

                    isEnlarged = !isEnlarged;
                }
            });
        }

        public void init() {

            fbs = FirebaseServices.getInstance();

            tvSmellName = getView().findViewById(R.id.tvSmellNameDetailsFragment);
            tvSmellIntensity = getView().findViewById(R.id.tvSmellIntensityDetailsFragment);
            tvMemoryType = getView().findViewById(R.id.tvMemoryTypeDetailsFragment);
            tvPhone = getView().findViewById(R.id.tvPhoneDetailsFragment);
            tvMemoryId = getView().findViewById(R.id.tvMemoryIdDetailsFragment);
            tvSmellSource = getView().findViewById(R.id.tvSmellSourceDetailsFragment);
            tvMemoryDate = getView().findViewById(R.id.tvMemoryDateDetailsFragment);
            tvColor = getView().findViewById(R.id.tvSmellColorDetailsFragment);
            tvSmellCategory = getView().findViewById(R.id.tvSmellCategoryDetailsFragment);
            tvMemoryDescription = getView().findViewById(R.id.tvMemoryDescriptionDetailsFragment);
            tvMemoryLocation = getView().findViewById(R.id.tvMemoryLocationDetailsFragment);
            tvSmellStrength = getView().findViewById(R.id.tvSmellStrengthDetailsFragment);
            tvSmellStyle = getView().findViewById(R.id.tvSmellStyleDetailsFragment);
            tvFeeling = getView().findViewById(R.id.tvFeelingDetailsFragment);

            ivMemoryPhoto = getView().findViewById(R.id.ivMemoryDetailsFragment);

            Bundle args = getArguments();

            if (args != null) {

                myMemory = args.getParcelable("memory");

                if (myMemory != null) {

                    tvSmellName.setText(myMemory.getSmellName());
                    tvSmellIntensity.setText(myMemory.getSmellIntensity());
                    tvMemoryType.setText(myMemory.getMemoryType());
                    tvPhone.setText(myMemory.getPhone());
                    tvMemoryId.setText(myMemory.getMemoryId());
                    tvSmellSource.setText(myMemory.getSmellSource());
                    tvMemoryDate.setText(myMemory.getMemoryDate());
                    tvColor.setText(myMemory.getSmellColor());
                    tvSmellCategory.setText(myMemory.getSmellCategory());
                    tvMemoryDescription.setText(myMemory.getMemoryDescription());
                    tvMemoryLocation.setText(myMemory.getMemoryLocation());
                    tvSmellStrength.setText(myMemory.getSmellStrength());
                    tvSmellStyle.setText(myMemory.getSmellStyle());
                    tvFeeling.setText(myMemory.getFeeling());

                    if (myMemory.getPhoto() == null || myMemory.getPhoto().isEmpty()) {

                        Picasso.get().load(R.drawable.ic_fav).into(ivMemoryPhoto);

                    } else {

                        Picasso.get().load(myMemory.getPhoto()).into(ivMemoryPhoto);
                    }
                }
            }

            sendSMSButton = getView().findViewById(R.id.btnSMS);

            sendSMSButton.setOnClickListener(v -> checkAndSendSMS());

            btnWhatsapp = getView().findViewById(R.id.btnWhatsApp);

            btnWhatsapp.setOnClickListener(v -> sendWhatsAppMessage(v));

            btnCall = getView().findViewById(R.id.btnCall);

            btnCall.setOnClickListener(v -> makePhoneCall());
        }

        private void checkAndSendSMS() {

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.SEND_SMS},
                        PERMISSION_SEND_SMS);

            } else {

                sendSMS();
            }
        }

        private void sendSMS() {

            String phoneNumber = myMemory.getPhone();

            String message = "I am interested in this memory smell: "
                    + myMemory.getSmellName();

            try {

                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(phoneNumber, null, message, null, null);

                Toast.makeText(getActivity(), "SMS sent.", Toast.LENGTH_LONG).show();

            } catch (Exception e) {

                Toast.makeText(getActivity(), "SMS sending failed.", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }
        }

        public void sendWhatsAppMessage(View view) {

            String smsNumber = "+972" + myMemory.getPhone();

            Intent sendIntent = new Intent(Intent.ACTION_SEND);

            sendIntent.setType("text/plain");

            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "I am interested in this memory smell: " + myMemory.getSmellName());

            sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net");

            sendIntent.setPackage("com.whatsapp");

            startActivity(sendIntent);
        }

        private void makePhoneCall() {

            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_CALL_PERMISSION);

            } else {

                startCall();
            }
        }

        private void startCall() {

            Intent callIntent = new Intent(Intent.ACTION_CALL);

            callIntent.setData(Uri.parse("tel:" + myMemory.getPhone()));

            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                startActivity(callIntent);
            }
        }
    }
