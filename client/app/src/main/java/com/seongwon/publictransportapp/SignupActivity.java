package com.seongwon.publictransportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    private Button btn_sign_cancel,btn_sign_confirm;
    private EditText et_signup_id,et_signup_pwd,et_signup_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_signup_id = (EditText) findViewById(R.id.et_signup_id);
        et_signup_pwd = (EditText) findViewById(R.id.et_signup_pwd);
        et_signup_email = (EditText) findViewById(R.id.et_signup_email);

        btn_sign_cancel = (Button) findViewById(R.id.btn_sign_cancel);
        btn_sign_confirm = (Button) findViewById(R.id.btn_sign_confirm);

        btn_sign_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btn_sign_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    User user = new User();
                    user.setId(et_signup_id.getText().toString());
                    user.setPwd(et_signup_pwd.getText().toString());
                    user.setEmail(et_signup_email.getText().toString());
                    user.setUser_type("native");

                    Boolean b = (Boolean) new Task("join",user).execute().get();
                    if(b.booleanValue() == Boolean.TRUE)
                    {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }catch (Exception e){
                    Log.d("exception",e.getMessage());
                }
            }
        });
    }
}