package android.example.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager=getSupportFragmentManager();
        MasterFragment masterFragment =new MasterFragment();
       fragmentManager.beginTransaction().add(R.id.frame_container,masterFragment).commit();

    }

    }

