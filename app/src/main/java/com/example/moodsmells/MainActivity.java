package com.example.moodsmells;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Stack;
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
                    selectedFragment = new MemoryListMapFragment();
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

            //
            /*
            if (fbs.getAuth().getCurrentUser() == null) {
                bottomNavigationView.setVisibility(View.GONE);
                loadFragment(new LoginFragment());
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
                loadFragment(new MemoryListMapFragment());
            } */
            loadFragment(new AdminFragment());
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
                                loadFragment(new MemoryListMapFragment());
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
            return null;
    }
}
