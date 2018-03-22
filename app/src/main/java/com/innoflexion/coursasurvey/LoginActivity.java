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
                String email = userName.getText().toString().trim();

                //Validate email & its format
                if(email.length() == 0 || !email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                    userName.setError(getString(R.string.no_email));
                    userName.requestFocus();

                    return;
                }

                //Password shouldn't be empty.
                if(password.getText().toString().trim().length() == 0){
                    password.setError(getString(R.string.no_password));
                    password.requestFocus();

                    return;
                }

                //Once validation is good, call the API to authenticate the user.
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
