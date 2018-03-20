package com.sample.webcontrolapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SurveyStatusFragment extends Fragment {

    private View view;
    Button toggleSurvey;
    Context context;

    public SurveyStatusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    boolean isStarted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buttons, container, false);
        context = this.getContext();
        toggleSurvey = (Button) view.findViewById(R.id.toogleSurvey);

        toggleSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStarted) {
                    toggleSurvey.setText(getString(R.string.stop_survey));
                    isStarted = true;
                } else {
 //                   showDialog(context);
                      showDefaultDialog();
                    isStarted = false;
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

      //custom dialog box
   /* private void showDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle(getString(R.string.survey));

        *//*TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Android custom dialog example!");*//*
        Button survey_accept = (Button) dialog.findViewById(R.id.survey_accept);
        Button survey_decline = (Button) dialog.findViewById(R.id.survey_decline);
        TextView cancel_action = (TextView) dialog.findViewById(R.id.cancel_action);

        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                toggleSurvey.setText(getString(R.string.stop_survey));
            }
        });

        survey_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                toggleSurvey.setText(getString(R.string.start_survey));
            }
        });

        survey_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                toggleSurvey.setText(getString(R.string.start_survey));
            }
        });
        dialog.show();
    }*/

    private void showDefaultDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(getString(R.string.survey))
                .setMessage(getString(R.string.dialog_msg))
                .setPositiveButton(getString(R.string.accept_survey), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        toggleSurvey.setText(getString(R.string.start_survey));
                    }
                })
                .setNegativeButton(getString(R.string.decline_survey), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        toggleSurvey.setText(getString(R.string.start_survey));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert) //        TODO: if you want to remove icon just comment this line.
                .show();
    }

}
