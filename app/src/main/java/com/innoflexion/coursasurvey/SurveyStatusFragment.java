package com.innoflexion.coursasurvey;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.invensense.coursa.crlsdk.CrlEventListener;
import com.invensense.coursa.crlsdk.CrlManager;
import com.invensense.coursa.crlsdk.CrlSessionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SurveyStatusFragment extends Fragment {

    private View view;
    Button toggleSurvey;
    Context context;

    private String TAG = "Retail SDK Demo";
    private static final int PERM_REQUEST = 1010;
    CrlManager mCrlMgr;


    Handler mTaskHandler;

    String mSdkKey = "acbf2508-47bd-4598-8b2d-aea026317ef3";
    String mUniqueUserId = "ypuzyrenko@innoflexion.com";
    String mUniqueDeviceId = "Unique Device ID";
    boolean mEnableDebug = true;
    boolean mAppActive = false;

    /* Ensure a valid read/write path exists. */
    String mRetailDir = Environment.getExternalStorageDirectory().getPath() + "/coursasurvey/";

//    Runnable mUpdateTask = new Runnable() {
//        @Override
//        public void run() {
//            if (mAppActive) {
//                CrlSessionStatus status = mCrlMgr.crlGetSessionStatus();
////                updateGui(status);
//                mTaskHandler.postDelayed(mUpdateTask, 250);
//            } else {
//                Log.d(TAG, "Updates stopped");
//            }
//        }
//    };

    CrlEventListener mCrlEventListener = new CrlEventListener() {
        @Override
        public void crlStatusUpdate(int i) {
            Log.d(TAG, "CRL Status update: " + i);
        }

        @Override
        public void crlRegionLocationUpdate(Location location) {
            final String loc = String.format(Locale.US, "%.6f, %.6f acc: %.1f",
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getAccuracy());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    mRegionLocText.setText(loc);
                }
            });
        }
    };

    public SurveyStatusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mAppActive = true;
//        mTaskHandler.post(mUpdateTask);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mAppActive = false;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    boolean isStarted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buttons, container, false);
        context = this.getContext();
        toggleSurvey = (Button) view.findViewById(R.id.toogleSurvey);

//        mTaskHandler = new Handler();

        mCrlMgr = CrlManager.getInstance();

        boolean permissionsGranted = checkPermissions();

      /* If permissions already granted, initialize now, otherwise
       * wait until permissions are available.
       */
        if (permissionsGranted) {
            mUniqueDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            mCrlMgr.crlInitialize(mSdkKey, mUniqueUserId, mUniqueDeviceId,
                    mRetailDir, mEnableDebug,
                    mCrlEventListener, context);
        }

        toggleSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStarted) {
                    toggleSurvey.setText(getString(R.string.stop_survey));
                    isStarted = true;
                    int data = mCrlMgr.crlStart(mUniqueUserId, mUniqueDeviceId);
                    Toast.makeText(context, "data = " + data, Toast.LENGTH_LONG).show();
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
//    private void showDialog(Context context) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.custom_dialog);
//        dialog.setTitle(getString(R.string.survey));
//
//        *//*TextView text = (TextView) dialog.findViewById(R.id.text);
//        text.setText("Android custom dialog example!");*//*
//        Button survey_accept = (Button) dialog.findViewById(R.id.survey_accept);
//        Button survey_decline = (Button) dialog.findViewById(R.id.survey_decline);
//        TextView cancel_action = (TextView) dialog.findViewById(R.id.cancel_action);
//
//        cancel_action.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                toggleSurvey.setText(getString(R.string.stop_survey));
//            }
//        });
//
//        survey_accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                toggleSurvey.setText(getString(R.string.start_survey));
//            }
//        });
//
//        survey_decline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                toggleSurvey.setText(getString(R.string.start_survey));
//            }
//        });
//        dialog.show();
//    }

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
                        mCrlMgr.crlStop();
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

    public boolean checkPermissions() {
        boolean permissionsAlreadyGranted = false;

        List<String> perms = new ArrayList<>();

        if (PackageManager.PERMISSION_DENIED ==
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            perms.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (PackageManager.PERMISSION_DENIED ==
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            perms.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (PackageManager.PERMISSION_DENIED ==
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            perms.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!perms.isEmpty()) {
            String[] array = perms.toArray(new String[perms.size()]);
            Log.d(TAG, "Requesting permissions: " + perms);
            ActivityCompat.requestPermissions(getActivity(), array, PERM_REQUEST);
        } else {
            Log.d(TAG, "Required permissions available");
            permissionsAlreadyGranted = true;
        }

        return permissionsAlreadyGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERM_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean externalReadGranted = false;
                    boolean externalWriteGranted = false;
                    boolean fineLocationGranted = false;

                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                externalReadGranted = true;
                            }
                        }
                        if (permissions[i].equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                externalWriteGranted = true;
                            }
                        }
                        if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                fineLocationGranted = true;
                            }
                        }
                    }
                    if (fineLocationGranted && externalReadGranted && externalWriteGranted) {
                        mCrlMgr.crlInitialize(mSdkKey, mUniqueUserId, mUniqueDeviceId,
                                mRetailDir, mEnableDebug, mCrlEventListener, context);
                    } else {
                        Log.e(TAG, "Not all required permissions were granted");
                    }
                } else {
                    Log.e(TAG, "User denied permissions");
                }
                return;
            }
            default:
                Log.e(TAG, "Unknown permission request: " + requestCode);
        }
    }

}
