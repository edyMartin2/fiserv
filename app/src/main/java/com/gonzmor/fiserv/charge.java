package com.gonzmor.fiserv;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import net.glxn.qrgen.android.QRCode;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link charge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class charge extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    //mis componentes
    private ImageView QR;
    private TextInputEditText importeCapture;
    private MaterialButton btnGenerate ;

    public charge() {
        // Required empty public constructor
    }


    public static charge newInstance(String param1, String param2) {
        charge fragment = new charge();
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
        View root = inflater.inflate(R.layout.fragment_charge, container, false);
        final Context context = root.getContext();

        QR = root.findViewById(R.id.Qr);
        importeCapture = root.findViewById(R.id.importeCapture);
        btnGenerate = root.findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String all = importeCapture.getText().toString();
                Bitmap qr = QRCode.from(all + ";payment").bitmap();
                QR.setImageBitmap(qr);
                Toast.makeText(context, "Generando importe por " + importeCapture.getText().toString() + " $", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}