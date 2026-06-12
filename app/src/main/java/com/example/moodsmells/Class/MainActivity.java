package com.example.moodsmells.Class;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.moodsmells.Fragment.AddSmellsFragment;
import com.example.moodsmells.FirebaseServices;
import com.example.moodsmells.Fragment.AdminFragment;
import com.example.moodsmells.Fragment.FragmentEdit;
import com.example.moodsmells.Fragment.LoginFragment;
import com.example.moodsmells.R;
import com.example.moodsmells.Fragment.SignupFragment;
import com.example.moodsmells.Fragment.SmellsListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private FirebaseServices fbs;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setupBackHandler();
    }

    private void init() {

        fbs = FirebaseServices.getInstance();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentContainer = findViewById(R.id.framelayout);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.action_home) {
                selectedFragment = new LoginFragment();
            }
            else if (item.getItemId() == R.id.action_signout) {
                signout();
                return true;
            }
            else if (item.getItemId() == R.id.action_add) {
                selectedFragment = new FragmentEdit();
            }

            else if (item.getItemId() == R.id.btnAddMemory) {
                selectedFragment = new AddSmellsFragment();
            }



            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;
        });

        // ---- التعديل الجديد المطلوب تم هنا ----
        // إخفاء القائمة السفلية فوراً عند التشغيل
        bottomNavigationView.setVisibility(View.GONE);

        // فتح صفحة تسجيل الدخول مباشرة في كل مرة يفتح فيها التطبيق
        loadFragment(new LoginFragment());
        // ----------------------------------------
    }

    //
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment)
                .commit();
    }

    //
    private void setupBackHandler() {
        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {

                        Fragment current = getSupportFragmentManager()
                                .findFragmentById(R.id.framelayout);

                        if (current instanceof LoginFragment) {
                            finish();
                        }
                        else {
                            loadFragment(new AdminFragment());
                            bottomNavigationView.setSelectedItemId(R.id.action_home);
                        }
                    }
                });
    }

    private void signout() {
        fbs.getAuth().signOut();
        bottomNavigationView.setVisibility(View.GONE);
        loadFragment(new LoginFragment());
    }



    public User getUserDataObject() {
        return null;
    }

    public void pushFragment(SignupFragment signupFragment) {
    }

    public void pushFragment(SmellsListFragment smellsListFragment) {
    }

    public View getBottomNavigationView() {
        return bottomNavigationView;
    }
}