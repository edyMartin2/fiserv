package com.gonzmor.fiserv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gonzmor.fiserv.databaseAll.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cards#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cards extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //mis complementos
    private FloatingActionButton btnAdd;
    private TextView hi ;
    ListView lvCards ;

    private String mParam1;
    private String mParam2;

    public cards(Context context) {

    }
    public static cards newInstance(String param1, String param2) {
        cards fragment = new cards(null);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cards, container, false);
        final Context context = root.getContext();

        lvCards = root.findViewById(R.id.cards);
        lvCards.setAdapter(fillinCard(context));


        btnAdd = root.findViewById(R.id.addCar);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), addCard.class);
                startActivity(intent);
            }
        });

        return root;
    }

    //este segmento es para obtener las targetas de la db
    public ArrayAdapter<String> fillinCard (Context context){
        ArrayList<String> items = new ArrayList<>();
        controllerWallet controller = new controllerWallet(context);
        items = controller.getCards();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items);
        if (items != null ){
           return  adapter;
        } else {
            return null;
        }

    }

}