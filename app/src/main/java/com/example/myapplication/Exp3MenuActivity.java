package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Exp3MenuActivity extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exp3);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 把 Toolbar 当 ActionBar 用，右上角才会出现 ⋮ 溢出按钮
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("实验3  XML 定义菜单");

        tvTest = findViewById(R.id.tv_test);
    }

    /** 加载 res/menu/menu_exp3.xml —— 这就是"使用 XML 定义菜单"的入口 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exp3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // 字体大小：小 10、中 16、大 20
        if (id == R.id.font_size_small) {
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            item.setChecked(true);
            return true;
        } else if (id == R.id.font_size_medium) {
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            item.setChecked(true);
            return true;
        } else if (id == R.id.font_size_large) {
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            item.setChecked(true);
            return true;

            // 普通菜单项：只弹 Toast
        } else if (id == R.id.menu_normal_item) {
            Toast.makeText(this, "你点击了普通菜单项", Toast.LENGTH_SHORT).show();
            return true;

            // 字体颜色：红 / 黑
        } else if (id == R.id.font_color_red) {
            tvTest.setTextColor(Color.RED);
            item.setChecked(true);
            return true;
        } else if (id == R.id.font_color_black) {
            tvTest.setTextColor(Color.BLACK);
            item.setChecked(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}