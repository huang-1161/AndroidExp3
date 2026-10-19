package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Exp2AlertDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp2);

        findViewById(R.id.btn_show_dialog).setOnClickListener(v -> showLoginDialog());
        // 想让进入本页就直接弹窗（方便截图），把上面这行换成 showLoginDialog();
    }

    private void showLoginDialog() {
        // 1. 把自定义布局膨胀成 View
        View content = getLayoutInflater().inflate(R.layout.dialog_login, null);

        // 2. 用 setView() 把布局塞进 AlertDialog（题目要求的核心 API）
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(content)
                .create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        // 3. 从自定义布局里取控件
        EditText etUsername = content.findViewById(R.id.et_username);
        EditText etPassword = content.findViewById(R.id.et_password);

        content.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());

        content.findViewById(R.id.btn_sign_in).setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty()) {
                etUsername.setError("请输入用户名");
                etUsername.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("请输入密码");
                etPassword.requestFocus();
                return;
            }

            Toast.makeText(this, "登录成功：" + username, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }
}