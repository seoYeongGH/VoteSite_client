package com.example.votesite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.votesite.Retro.RetroController;
import com.example.votesite.Retro.UserService;
import com.example.votesite.VO.ResponseData;
import com.example.votesite.VO.UserVO;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.votesite.Constant.DUP_ID;
import static com.example.votesite.Constant.DUP_USER;
import static com.example.votesite.Constant.ERR_DIFF;
import static com.example.votesite.Constant.ERR_NULL;
import static com.example.votesite.Constant.SUCCESS;

public class JoinPage extends AppCompatActivity {
    protected EditText iptName;
    protected EditText iptId;
    protected EditText iptPw;
    protected EditText iptChkPw;
    protected EditText iptEmail;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        iptName = findViewById(R.id.iptName);
        iptId = findViewById(R.id.iptId);
        iptPw = findViewById(R.id.iptPw);
        iptChkPw = findViewById(R.id.iptChkPw);
        iptEmail = findViewById(R.id.iptEmail);

        alertDialog = makeAlert();

        return;
    }

    public void onBtnJoinClicked(View view){
        String name = iptName.getText().toString();
        String id = iptId.getText().toString();
        String pw = iptPw.getText().toString();
        String chkPw = iptChkPw.getText().toString();
        String email = iptEmail.getText().toString();

        if(name.length()==0 || id.length()==0 || pw.length()==0 || chkPw.length()==0|| email.length()==0) {
            showAlert(ERR_NULL);
            return;
        }

        if(!pw.equals(chkPw)){
        }
        else{
            saveMember(new UserVO(id,pw,name,email,0,0));
        }

        return;
    }

    private void showAlert(int code){
        String msg="";

        switch(code){
            case ERR_NULL: msg = "모든 항목을 입력해야합니다."; break;
            case DUP_ID: msg = "이미 존재하는 아이디 입니다."; break;
            case DUP_USER: msg = "이미 가입되어있는 회원입니다."; break;
            default: msg = "ERR!!"; break;
        }

        alertDialog.setMessage(msg);
        alertDialog.show();

        return ;
    }

    private AlertDialog makeAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sorry");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void saveMember(UserVO userVO){
        Retrofit retrofit = RetroController.getInstance().getRetrofit();
        UserService userService = retrofit.create(UserService.class);

        Call<ResponseData> doService = userService.doService("join",userVO);
        doService.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()){
                    checkResponse(response.body().getCode());
                }
                else{
                    Log.d("JOIN_ERR","Join Retrofit Err");
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void checkResponse(int code){
        switch(code){
            case SUCCESS: Snackbar.make(findViewById(R.id.joinLayout),"JOIN SUCCESS!!", Snackbar.LENGTH_LONG).show();
                          Intent intent = new Intent(this, LoginPage.class);
                          intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                          startActivity(intent);
                          finish(); break;
            case DUP_ID:
            case DUP_USER: showAlert(code); break;
        }
    }
    public void onBtnCancelClicked(View view){
        finish();
    }

}
