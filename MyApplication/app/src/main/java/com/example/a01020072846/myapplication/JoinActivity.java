package com.example.a01020072846.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JoinActivity extends AppCompatActivity {
    // 중복 처리를 위한 플래그
    private int FLAG = 0;

    Intent loginIntent;
    ProgressDialog dialog;
    EditText etJoinID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        loginIntent = new Intent(this, LoginActivity.class);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("잠시만 기다려주세요");
        dialog.setCancelable(false);

        etJoinID = (EditText)findViewById(R.id.et_join_id);
        final EditText etJoinPw = (EditText)findViewById(R.id.et_join_pw);
        final EditText etJoinPwCheck = (EditText)findViewById(R.id.et_join_pw_check);
        final EditText etJoinName = (EditText)findViewById(R.id.et_join_name);
        final EditText etJoinEmail = (EditText)findViewById(R.id.et_join_email);
        final EditText etJoinPhone = (EditText)findViewById(R.id.et_join_phone);
        Button btnIdCheck = (Button) findViewById(R.id.btn_idCheck);
        Button btnJoin = (Button) findViewById(R.id.btn_join);
        Button btnCancel = (Button)findViewById(R.id.btn_cancel);

        btnIdCheck.setOnClickListener(new View.OnClickListener() {
            String id;
            @Override
            public void onClick(View v) {
                id = etJoinID.getText().toString();
                Toast.makeText(JoinActivity.this, id, Toast.LENGTH_SHORT).show();
                
                if(id.length() < 5) {
                    Toast.makeText(JoinActivity.this, "아이디는 4자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                new IdCheckAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_IdCheck", id);
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            String id, pw, pwCheck, name, email, phone;

            @Override
            public void onClick(View v) {
                id = etJoinID.getText().toString();
                pw = etJoinPw.getText().toString();
                pwCheck = etJoinPwCheck.getText().toString();
                name = etJoinName.getText().toString();
                email = etJoinEmail.getText().toString();
                phone = etJoinPhone.getText().toString();

                if(id.length() < 5) {
                    Toast.makeText(JoinActivity.this, "아이디 중복 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호와 비밀번호 확인이 다를 경우
                if(!pw.equals(pwCheck)) {
                    Toast.makeText(JoinActivity.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pw.length() < 5 && pwCheck.length() <5) {
                    Toast.makeText(JoinActivity.this, "비밀번호는 4글자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.length() < 4)  {
                    Toast.makeText(JoinActivity.this, "점포명은 3글자 이상어야 합니다.", Toast.LENGTH_SHORT).show();
                }
                if(email.length() < 8) {
                    Toast.makeText(JoinActivity.this, "정확한 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phone.length() < 10) {
                    Toast.makeText(JoinActivity.this, "정확한 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(FLAG != 1) {
                    Toast.makeText(JoinActivity.this, "아이디 중복 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                new JoinAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_join",id,pw,name,email,phone);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void idCheck(String id) {
        final String userId = id;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("아이디 중복확인");
        builder.setMessage(id + "를 사용하시겠습니까?");
        builder.setIcon(R.drawable.ic_person_add_black_24dp);

        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FLAG = 1;
                Toast.makeText(JoinActivity.this, userId + "를 사용합니다.", Toast.LENGTH_SHORT).show();
                etJoinID.setEnabled(false);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FLAG = 0;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class IdCheckAsyncTask extends AsyncTask<String, Void,String> {
        OkHttpClient client= new OkHttpClient();
        Gson gson = new Gson();

        String URL, id, result;
        User user;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            URL = strings[0];
            id = strings[1];

            // 서버로 전송할 객체 값 설정
            user = new User();
            user.setmId(id);

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
                Toast.makeText(JoinActivity.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
            } else if(result.equals("0")) {
                Toast.makeText(JoinActivity.this, "사용 가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                idCheck(id);
            }
        }
    }

    private class JoinAsyncTask extends AsyncTask<String, Void, String> {
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

            } catch(Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();

            if(result.equals("1")) {
                Toast.makeText(JoinActivity.this, "가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                startActivity(loginIntent);
            } else {
                Toast.makeText(JoinActivity.this, "입력을 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}