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
    private TextView tvNameMemory,tvyear,tvPlace,tvType,tvPerson,tvMood,tvColor,tvLoction,tvPhone;
    private ImageView ivSmellsPhoto;
    private SmellsItem mysmells;
    private Button sendSMSButton, btnWhatsapp, btnCall;

    private boolean isEnlarged = false; //משתנה כדי לעקוב אחרי המצב הנוכחי של התמונה (האם היא מגודלת או לא)

    public SmellsDetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_smells_details, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        init();
        ImageView ivSmellsPhoto = getView().findViewById(R.id.ivCarDetailsFragment);

        ivSmellsPhoto.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                ViewGroup.LayoutParams layoutParams = ivSmellsPhoto.getLayoutParams();
                if (isEnlarged) {
                    layoutParams.height =500;
                } else {
                    layoutParams.height = 2200;
                }
                ivSmellsPhoto.setLayoutParams(layoutParams);

                // נשנה את המצב הנוכחי של התמונה
                isEnlarged = !isEnlarged;

            }
        });


    }



    public void init()
    {
/*Car(String nameCar, String horse_power, String owners, String phone, String color,
               String car_num, String manufacturer, String year, String car_model, String test,
               String kilometre, String engine_capacity, String gear_shifting_model, String price, String photo)
* */

        fbs= FirebaseServices.getInstance();
        tvNameMemory=getView().findViewById(R.id.tvnameSmellsDetailsFragment);
        tvyear=getView().findViewById(R.id.tvDateDetailsFragment);
        tvPlace=getView().findViewById(R.id.tvPlaceDetailsFragment);
        tvPhone=getView().findViewById(R.id.tvPhoneDetailsFragment);
        tvMood=getView().findViewById(R.id.tvMoodDetailsFragment);
        tvPerson=getView().findViewById(R.id.tvPersonDetailsFragment);
         tvLoction=getView().findViewById(R.id.tvLocationDetailsFragment);
        tvColor = getView().findViewById(R.id.tvColorDetailsFragment);
        tvType=getActivity().findViewById(R.id.tvTypeDetailsFragment);


        Bundle args = getArguments();
        if (args != null) {
            mysmells = args.getParcelable("smells");
            if (mysmells != null) {
                //String data = myObject.getData();
                // Now you can use 'data' as needed in FragmentB
                tvNameMemory.setText(mysmells.getNameMemory());
                tvyear.setText(mysmells.getYear());
                tvPlace.setText(mysmells.getPlace());
                tvPhone.setText(mysmells.getPhone());
                tvMood.setText(mysmells.getMood());
                tvPerson.setText(mysmells.getPerson());
                tvLoction.setText(mysmells.getLoction());
                tvColor.setText(mysmells.getColor());
                tvType.setText(mysmells.getType());


            }
        }
        sendSMSButton = getView().findViewById(R.id.btnSMS);
        sendSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSendSMS();            }
        });

        btnWhatsapp = getView().findViewById(R.id.btnWhatsApp);
        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWhatsAppMessage(v);

            }
        });

        btnCall = getView().findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }

    private void checkAndSendSMS() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SEND_SMS);
        } else {
            sendSMS();
        }
    }

    private void sendSMS() {
        String phoneNumber = mysmells.getPhone();
        String message = "I am Interested in your  "+mysmells.getNameMemory()+"  smells: " + mysmells.getType();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getActivity(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "SMS sending failed.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS();
            } else {
                Toast.makeText(requireContext(), "Permission denied. Cannot send SMS.", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCall();
            }
        }
    }
    // TODO : check Phone number is not correct;
    public void sendWhatsAppMessage(View view) {

        String smsNumber = "+972"+mysmells.getPhone();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        //  Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, " I am Interested in your  " +mysmells.getNameMemory()+ "  car:  "  + mysmells.getType());
        sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");

        startActivity(sendIntent);
//        String phoneNumber ="+972"+ myCar.getPhone(); // Replace with the recipient's phone number
//        String message = "Hello, this is a WhatsApp message!"; // Replace with your message
//        String phoneNumber2=  phoneNumber;
//        boolean isWhatsAppInstalled  =isAppInstalled("com.whatsapp");
//
//        if(isWhatsAppInstalled ){
//            Intent intent=new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+phoneNumber+"&text="+message));
//            startActivity(intent);
//        }
//        else {
//            Toast.makeText(getActivity(), "whatsapp is not installed", Toast.LENGTH_SHORT).show();
//        }

//
//
//        // Create an intent with the ACTION_SENDTO action and the WhatsApp URI
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("smsto:" + phoneNumber));
//        intent.putExtra("sms_body", message);
//
//        // Verify if WhatsApp is installed
//        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivity(intent);
//        } else {
//            // WhatsApp is not installed
//            // You can handle this case as per your app's requirement
//        }
    }
    //  888 whatsapp setting
    private boolean isAppInstalled(String s) {
        PackageManager packageManager= getActivity().getPackageManager();
        boolean is_installed;
        try{
            packageManager.getPackageInfo(s,PackageManager.GET_ACTIVITIES);
            is_installed=true;
        } catch (PackageManager.NameNotFoundException e) {
            is_installed=false;
            throw new RuntimeException(e);
        }
        return  is_installed;
    }

    private void makePhoneCall() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            startCall();
        }
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
//        } else {
//            startCall();
//        }
    }

    private void startCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mysmells.getPhone()));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
        }


//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse(myCar.getPhone()));
//
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//            startActivity(callIntent);
//        }
    }


}
