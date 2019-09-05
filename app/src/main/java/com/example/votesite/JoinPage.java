package com.example.votesite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.votesite.Constant.ERR_NULL;

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
    }

    private void showAlert(int code){
        String msg="";

        switch(code){
            case ERR_NULL: msg = "모든 항목을 입력해야합니다."; break;
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
    public void onBtnCancelClicked(View view){
        finish();
    }

}
