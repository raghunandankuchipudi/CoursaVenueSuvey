package com.sample.webcontrolapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ButtonsFragment extends Fragment {

    private View view;
    Button toogleSurvey;
    Context context;

    public ButtonsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buttons, container, false);
        context = this.getContext();
        toogleSurvey = (Button) view.findViewById(R.id.toogleSurvey);

        toogleSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toogleSurvey.getText().toString().equalsIgnoreCase("Start Survey")) {
                    toogleSurvey.setText("Stop Survey");
                } else {
                    showDialog(context);
                }
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void showDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Survey");

        /*TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Android custom dialog example!");*/
        Button survey_accept = (Button) dialog.findViewById(R.id.survey_accept);
        Button survey_decline = (Button) dialog.findViewById(R.id.survey_decline);
        TextView cancel_action = (TextView) dialog.findViewById(R.id.cancel_action);

        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                toogleSurvey.setText("Stop Survey");
            }
        });

        survey_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                toogleSurvey.setText("Start Survey");
            }
        });

        survey_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                toogleSurvey.setText("Start Survey");
            }
        });
        dialog.show();
    }
}
