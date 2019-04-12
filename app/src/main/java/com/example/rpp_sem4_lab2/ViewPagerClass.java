package com.example.rpp_sem4_lab2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;

public class ViewPagerClass extends FragmentActivity {
    ViewPager pager;
    PagerAdapter pagerAdapter;
    private JSONArray data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_view_fragment);

        // Получение json в виде строки и преобразование к JSONObject
        DataHold dataHold = DataHold.getInstance();
        JSONArray data = dataHold.getData();

        this.data = data;

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this.data);
        pager = findViewById(R.id.view_pager);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem((int) getIntent().getSerializableExtra("position"));


    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private JSONArray data;

        public MyFragmentPagerAdapter(FragmentManager fm, JSONArray data) {
            super(fm);
            this.data = data;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Header";
        }

        @Override
        public Fragment getItem(int position) {
            position++;
            String graphic = "";
            String helptext = "No Information.";
            try {
                graphic = data.getJSONObject(position).getString("graphic");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                helptext = data.getJSONObject(position).getString("helptext");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new PageFrag(graphic, helptext);
        }

        @Override
        public int getCount() {
            return data.length();
        }
    }
}
