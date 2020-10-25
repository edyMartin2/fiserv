package com.gonzmor.fiserv;
import com.gonzmor.fiserv.databaseAll.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewPager view_pager;
    private TabLayout tab_layout;
    public ListView lvCards;
    public FloatingActionButton btnAddCard ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view_pager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
        lvCards = findViewById(R.id.cards);


        scann scann = new scann();
        charge charge = new charge();
        cards cards = new cards(this);
        tab_layout.setupWithViewPager(view_pager);

        ViewPagerAdapter viewadapter = new ViewPagerAdapter(getSupportFragmentManager(),0);
        viewadapter.addFragment(scann, "Scann");
        viewadapter.addFragment(charge, "Charge");
        viewadapter.addFragment(cards, "Cards");
        view_pager.setAdapter(viewadapter);




    }

    //este segmento es para obtener las targetas de la db
    public void fillinCard (){
        ArrayList <String> items = new ArrayList<>();
        controllerWallet controller = new controllerWallet(this);
        items = controller.getCards();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvCards.setAdapter(adapter);

    }

    //este segmento es para llenar las tabs
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment (Fragment fragment, String fragmentTitle){
            fragments.add(fragment);
            fragmentTitles.add(fragmentTitle);
        }
    }


}