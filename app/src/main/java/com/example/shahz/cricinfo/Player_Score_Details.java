package com.example.shahz.cricinfo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.shahz.cricinfo.fragments.First_Inning_Score;
import com.example.shahz.cricinfo.fragments.Second_Inning_Score;

public class Player_Score_Details extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;
    Bundle bundle;

    String getbat;
    String getbowl;
    String getMod;
    String getbt;
    String getbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player__score__details);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        getbat=getIntent().getStringExtra("t1name");
        getbowl=getIntent().getStringExtra("t2name");
        getMod=getIntent().getStringExtra("mod");
        getbt=getIntent().getStringExtra("bat");
        getbl=getIntent().getStringExtra("bowl");


        mViewPager=findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout= findViewById(R.id.tabs);

        tabLayout.getTabAt(0).setText(getbt);
        tabLayout.getTabAt(1).setText(getbl);
//        if (getMod.equals("Bat"))
//        {
//
//        }
//        else
//        {
//            tabLayout.getTabAt(1).setText(getbat);
//            tabLayout.getTabAt(0).setText(getbowl);
//        }



        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setArg();
                Fragment fragment= new Second_Inning_Score();
                fragment.setArguments(bundle);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);

            }
        });

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position)
            {
                case 0:

                   setArg();
                   fragment= new First_Inning_Score();
                   fragment.setArguments(bundle);

                    break;
                case 1:
                    setArg();
                   fragment= new Second_Inning_Score();
                   fragment.setArguments(bundle);
                    break;
                default:
                    return null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }

    private void setArg()
    {
        bundle =new Bundle();
        bundle.putString("bat", getbat);
        bundle.putString("bowl", getbowl);
        bundle.putString("batorbowl",getMod);
    }

}
