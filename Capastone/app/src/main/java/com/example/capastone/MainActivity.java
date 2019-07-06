package com.example.capastone;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.capastone.AsyncTask.InternetCheck;
import com.example.capastone.Fragments.ChatsFragment;
import com.example.capastone.Fragments.ProfileFragment;
import com.example.capastone.Fragments.UsersFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);

        ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment("Chats",new ChatsFragment());
        viewPageAdapter.addFragment("Users",new UsersFragment());
        viewPageAdapter.addFragment("Profile",new ProfileFragment());
        viewPager.setAdapter(viewPageAdapter);


        tabLayout.setupWithViewPager(viewPager);
        InternetCheck internetCheck=new InternetCheck();
        internetCheck.execute(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    class ViewPageAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        private ViewPageAdapter(FragmentManager fm) {
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    ChatsFragment tab1 = new ChatsFragment();
                    return tab1;
                case 1:
                    UsersFragment tab2 = new UsersFragment();
                    return tab2;
                case 2:
                    ProfileFragment tab3 = new ProfileFragment();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        private void addFragment(String title,Fragment fragment){
             fragments.add(fragment);
             titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
        return titles.get(position);
        }
    }
}
