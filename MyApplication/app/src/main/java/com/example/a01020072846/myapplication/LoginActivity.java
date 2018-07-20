package com.example.a01020072846.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    ProgressDialog dialog;
    Intent loginIntent;
    Intent joinIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("잠시만 기다려주세요");

        loginIntent = new Intent(this, MainActivity.class);
        joinIntent = new Intent(this, JoinActivity.class);

        Button btnLogin = (Button)findViewById(R.id.btn_login);
        Button btnJoin = (Button)findViewById(R.id.btn_join);
        final EditText etId = (EditText)findViewById(R.id.et_login_id);
        final EditText etPassword = (EditText)findViewById(R.id.et_login_password);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            String id, pw;
            @Override
            public void onClick(View v) {
                id = etId.getText().toString();
                pw = etPassword.getText().toString();

                if(id.length() == 0 ) {
                    Toast.makeText(LoginActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pw.length() == 0) {
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(LoginActivity.this,  "id : " + id + "\npw : " + pw, Toast.LENGTH_SHORT).show();

                new LoginAsyncTask().execute("http://192.168.33.136:8080/TodayMyStore/AndroidController?command=android_login", id, pw);
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(joinIntent);
            }
        });
    }

    private class LoginAsyncTask extends AsyncTask<String, Void,String>{
        OkHttpClient client= new OkHttpClient();
        Gson gson = new Gson();

        String URL, id, pw, result;
        User user;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            URL = strings[0];
            id = strings[1];
            pw = strings[2];

            // 서버로 전송할 객체 값 설정
            user = new User();
            user.setmId(id);
            user.setmPw(pw);

            // Json 데이터로 변환
            String jsonObject = gson.toJson(user);

            try{
                // 요청의 Body에 파라미터 추가
                RequestBody requestBody = new FormBody.Builder()
                        .add("user", jsonObject)
                        .build();

                // POST 요청
                Request request = new Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();

                // 요청 후 응답 받아오기
                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            dialog.dismiss();

            if(result.equals("1")){
                Toast.makeText(LoginActivity.this, id+"님 좋은 하루되세요!", Toast.LENGTH_SHORT).show();
                loginIntent.putExtra("id", id);
                startActivity(loginIntent);
                finish();
            } else if(result.equals("0")) {
                Toast.makeText(LoginActivity.this, "계정을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}