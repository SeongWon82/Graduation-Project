package com.seongwon.publictransportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    private ImageButton ib_kakao_login;
    private Button btn_skip,btn_login,btn_signup;
    private EditText et_id,et_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        KakaoSdk.init(this,"c80fcc8c8621a9ea8096194776a398fe");

        ib_kakao_login = (ImageButton) findViewById(R.id.ib_kakao_login);
        btn_skip = (Button) findViewById(R.id.btn_skip);

        et_id = (EditText) findViewById(R.id.et_id);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_signup = (Button) findViewById(R.id.btn_signup);


        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>()
        {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable)
            {
                //로그인 성공
                if (oAuthToken != null) {
                    // 접근 토큰 : oAuthToken.getAccessToken()
                    // 토큰 2 : oAuthToken.getRefreshToken())
                    sendKakaoLoginInfo();
                }
                // 로그인 실패
                if (throwable != null) {
                    et_id.setText("");
                    et_pwd.setText("");
                    Toast.makeText(getApplicationContext(),"로그인실패",Toast.LENGTH_LONG).show();
                }
                return null;
            }
        };
        ib_kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카카오톡이 있을 경우
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this))
                     UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                else
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.seongwon.publictransportapp.User user = new com.seongwon.publictransportapp.User();
                user.setId(et_id.getText().toString());
                user.setPwd(et_pwd.getText().toString());
                user.setUser_type("native");
                AuthenticateUser("login",user);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                finish();
                startActivity(intent);
            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
    // 카카오 유저 정보 확인
    public void sendKakaoLoginInfo() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 유저 정보가 정상 전달 되었을 경우
                if (user != null) {
                    com.seongwon.publictransportapp.User client = new com.seongwon.publictransportapp.User();
                    client.setId(String.valueOf(user.getId()));
                    client.setEmail(user.getKakaoAccount().getEmail());
                    client.setUser_type("kakao");
                    AuthenticateUser("oauth",client);
                }
                if (throwable != null) {   // 로그인 시 오류 났을 때
                    // 키해시가 등록 안 되어 있으면 오류
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                return null;
            }
        });
    }
    private void AuthenticateUser(String method,com.seongwon.publictransportapp.User user)
    {
        try {
            Boolean b = (Boolean) new Task(method,user).execute().get();
            if(b.booleanValue() == Boolean.TRUE)
            {
                Static.setUserId(user.getId());
                Static.setStatus(user.getUser_type());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }
            else
            {
                et_id.setText("");
                et_pwd.setText("");
            }
        }catch (Exception e){
            Log.d("exception",e.getMessage());
        }
    }
    private void logout()
    {
        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>()
        {
            @Override public Unit invoke(Throwable throwable)
            {
                Log.d("logout","로그 아웃됨");
                return null;
            }
        });
    }
}