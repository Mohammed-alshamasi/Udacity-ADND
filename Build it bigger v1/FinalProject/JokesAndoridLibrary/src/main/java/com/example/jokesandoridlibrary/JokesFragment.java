package com.example.jokesandoridlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.Inflater;

public class JokesFragment extends Fragment {
TextView textView;
   static String GCE_KEY="gce";
    public JokesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final View rootView= inflater.inflate(R.layout.fragment_detail,container,false);
    textView=rootView.findViewById(R.id.textView);

        String gce =getActivity().getIntent().getStringExtra(GCE_KEY);
        textView.setText(gce);
        return rootView;

    }
}
