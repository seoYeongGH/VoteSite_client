package com.example.votesite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.votesite.Retro.RetroController;
import com.example.votesite.Retro.UserService;
import com.example.votesite.VO.ResponseData;
import com.example.votesite.VO.UserVO;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.votesite.Constant.DIFF_PW;
import static com.example.votesite.Constant.DUP_ID;
import static com.example.votesite.Constant.DUP_USER;
import static com.example.votesite.Constant.ERR_NULL;
import static com.example.votesite.Constant.SUCCESS;

public class JoinPage extends AppCompatActivity {
    protected EditText iptName;
    protected EditText iptId;
    protected EditText iptPw;
    protected EditText iptChkPw;
    protected EditText iptEmail;
    protected TextView warningPw;
    private int warningCode = -1;

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
        warningPw  =findViewById(R.id.txtWarning);

        alertDialog = makeAlert();

        TextWatcher pwWatcher = getWatcher(iptChkPw);
        iptPw.addTextChangedListener(pwWatcher);

        TextWatcher chkWatcher = getWatcher(iptPw);
        iptChkPw.addTextChangedListener(chkWatcher);

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

        if(warningCode==1){
            showAlert(DIFF_PW);
        }
        if(warningCode == 2){
            saveMember(id,pw,name,email);
        }
        return;
    }

    private void showAlert(int code){
        String msg="";

        switch(code){
            case ERR_NULL: msg = "모든 항목을 입력해야합니다."; break;
            case DUP_ID: msg = "이미 존재하는 아이디 입니다."; break;
            case DUP_USER: msg = "이미 가입되어있는 회원입니다."; break;
            case DIFF_PW: msg = "비밀번호가 일치하지 않습니다."; break;
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

    private void saveMember(String id, String pw, String name, String email){
        Retrofit retrofit = RetroController.getInstance().getRetrofit();
        UserService userService = retrofit.create(UserService.class);

        HashMap hashMap = new HashMap();
        hashMap.put("doing","join");
        hashMap.put("id",id);
        hashMap.put("password",pw);
        hashMap.put("name",name);
        hashMap.put("email",email);

        Call<Integer> doService = userService.doService(hashMap);
        doService.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    Log.d("CHkCHK",""+response.body().intValue());
                    checkResponse(response.body().intValue());
                }
                else{
                    Log.d("JOIN_ERR","Join Retrofit Err");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

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
            default: showAlert(code); break;
        }
    }

    private TextWatcher getWatcher(final EditText editText){
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String comparePw = editText.getText().toString();

                if(!charSequence.toString().equals(comparePw)){
                    if(warningCode != 1) {
                        warningPw.setTextColor(Color.parseColor("#B90000"));
                        warningPw.setText("비밀번호가 일치하지 않습니다.");
                        warningCode = 1;
                    }
                }
                else{
                    if(warningCode != 2){
                        warningPw.setTextColor(Color.parseColor("#0C7900"));
                        warningPw.setText("사용할 수 있는 비밀번호입니다.");

                        warningCode = 2;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        return watcher;
    }

    public void onBtnCancelClicked(View view){
        finish();
    }

}
