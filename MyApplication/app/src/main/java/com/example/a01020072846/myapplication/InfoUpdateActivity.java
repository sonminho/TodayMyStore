package com.example.a01020072846.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InfoUpdateActivity extends AppCompatActivity {
    Intent mainIntent;
    ProgressDialog dialog;
    EditText etUpdateID, etUpdatePw, etUpdatePwCheck, etUpdateName, etUpdateEmail, etUpdatePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_update);

        Intent intent = getIntent();

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("잠시만 기다려주세요");
        dialog.setCancelable(false);

        etUpdateID = (EditText) findViewById(R.id.et_update_id);
        etUpdateID.setText(intent.getStringExtra("id"));
        etUpdateID.setEnabled(false);

        new UserInfoAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_user_info", intent.getStringExtra("id"));

        etUpdatePw  = (EditText)findViewById(R.id.et_update_pw);
        etUpdatePwCheck = (EditText)findViewById(R.id.et_update_pw_check);
        etUpdateName = (EditText)findViewById(R.id.et_update_name);
        etUpdateEmail = (EditText)findViewById(R.id.et_update_email);
        etUpdatePhone = (EditText)findViewById(R.id.et_update_phone);

        Button btnUpdate = (Button)findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            String id, pw, pwCheck, name, email, phone = null;

            @Override
            public void onClick(View v) {
                id = etUpdateID.getText().toString();
                pw = etUpdatePw.getText().toString();
                pwCheck = etUpdatePwCheck.getText().toString();
                name = etUpdateName.getText().toString();
                email = etUpdateEmail.getText().toString();
                phone = etUpdatePhone.getText().toString();

                // 비밀번호와 비밀번호 확인이 다를 경우
                if(!pw.equals(pwCheck)){
                    Toast.makeText(InfoUpdateActivity.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pw.length() < 5 && pwCheck.length() <5) {
                    Toast.makeText(InfoUpdateActivity.this, "비밀번호는 4글자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.length() < 4)  {
                    Toast.makeText(InfoUpdateActivity.this, "점포명은 3글자 이상어야 합니다.", Toast.LENGTH_SHORT).show();
                }
                if(email.length() < 8) {
                    Toast.makeText(InfoUpdateActivity.this, "정확한 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phone.length() < 10) {
                    Toast.makeText(InfoUpdateActivity.this, "정확한 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                new UpdateAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_info_update", id, pw, name, email, phone);
            }
        });

        Button btnCancle = (Button)findViewById(R.id.btn_cancel);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class UpdateAsyncTask extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        String URL, id, pw, name, email, phone, result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            URL = strings[0];
            id = strings[1];
            pw = strings[2];
            name = strings[3];
            email = strings[4];
            phone = strings[5];

            User user = new User(id, pw, name, email, phone);

            String jsonObject = gson.toJson(user);

            try {
                RequestBody requestBody = new FormBody.Builder()
                        .add("user", jsonObject)
                        .build();

                Request request = new Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();

            }catch(Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();

            if(result.equals("1")) {
                Toast.makeText(InfoUpdateActivity.this, "정보 수정 완료!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(InfoUpdateActivity.this, "입력을 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UserInfoAsyncTask extends  AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        String URL, id, result;

        @Override
        protected String doInBackground(String... strings) {
            URL = strings[0];
            id = strings[1];

            User user = new User();
            user.setmId(id);

            String jsonObject = gson.toJson(user);

            try {
                RequestBody requestBody = new FormBody.Builder()
                        .add("user", jsonObject)
                        .build();

                Request request = new Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch(Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String str) {
             User user = gson.fromJson(str, User.class);

            etUpdateName.setText(user.getmName());
            etUpdateEmail.setText(user.getmEmail());
            etUpdatePhone.setText(user.getmPhone());
        }

    }
}