package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_exp1).setOnClickListener(v ->
                startActivity(new Intent(this, Exp1ListActivity.class)));

        findViewById(R.id.btn_exp2).setOnClickListener(v ->
                startActivity(new Intent(this, Exp2AlertDialogActivity.class)));

        findViewById(R.id.btn_exp3).setOnClickListener(v ->
                startActivity(new Intent(this, Exp3MenuActivity.class)));
        findViewById(R.id.btn_exp4).setOnClickListener(v ->
                startActivity(new Intent(this, Exp4ActionModeActivity.class)));

        findViewById(R.id.btn_exp5).setOnClickListener(v ->
                startActivity(new Intent(this, Exp5RecyclerViewActivity.class)));
    }
}