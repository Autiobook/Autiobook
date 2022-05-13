    package com.example.autiobook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.autiobook.fragments.CreateAudiobook;
import com.example.autiobook.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

    public class MainActivity extends AppCompatActivity {

        public static final String TAG = "MainActivity";
        private BottomNavigationView bottomNavigationView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            bottomNavigationView = findViewById(R.id.bottom_navigation);

            final FragmentManager fragmentManager = getSupportFragmentManager();

            // Render fragments based on navigation clicks
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                Fragment fragment;
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.btnHome:
                            Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                            fragment = new HomeFragment();
                            break;
                        case R.id.btnUpload:
                            Toast.makeText(MainActivity.this, "Upload", Toast.LENGTH_SHORT).show();
                            fragment = new CreateAudiobook();
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                            fragment = new HomeFragment();
                            break;
                    }
                    fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                    return true;
                }
            });
            fragmentManager.beginTransaction().replace(R.id.flContainer, new HomeFragment()).commit();
        }
    }