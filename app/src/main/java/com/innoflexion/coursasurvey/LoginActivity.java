package com.innoflexion.coursasurvey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by raghu on 3/19/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        userName = (EditText) findViewById(R.id.email);
        password =(EditText) findViewById(R.id.password);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().trim().length() == 0) {
                    userName.setError("Username is not entered");
                    userName.requestFocus();
                }
                if(password.getText().toString().trim().length() == 0){
                    password.setError("Password is not entered");
                    password.requestFocus();
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
