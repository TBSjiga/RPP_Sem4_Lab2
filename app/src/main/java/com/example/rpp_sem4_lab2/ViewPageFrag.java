package com.example.rpp_sem4_lab2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

public class ViewPageFrag extends Fragment {

    private RecyclerView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view =  inflater.inflate(R.layout.main_list, container, false);

        // Получение json в виде строки и преобразование к JSONObject
        DataHold dataHold = DataHold.getInstance();
        JSONArray data = dataHold.getData();
        System.out.println(data.toString());

        Context context = getActivity();

        list = view.findViewById(R.id.card);
        Adapter adapter = new Adapter(data.length()-1, context);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

}
