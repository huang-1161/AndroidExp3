package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Exp5RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimalAdapter adapter;
    private final List<AnimalAdapter.Animal> data = new ArrayList<>();

    /** 新记录的编号，用来区分"副本" */
    private int copyIndex = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exp5);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initData();

        recyclerView = findViewById(R.id.rv_animals);
        // 必须设置 LayoutManager，否则什么都不显示
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AnimalAdapter(data, (position, animal) -> {
            // 单击：显示名称
            Toast.makeText(this, "点击：" + animal.name, Toast.LENGTH_SHORT).show();

            // 单击：增加一条记录，并立即刷新列表
            int insertIndex = data.size();
            data.add(new AnimalAdapter.Animal(animal.name + " #" + (copyIndex++), animal.iconRes));
            adapter.notifyItemInserted(insertIndex);   // 只刷新新增那一行，不会整页重绘
            recyclerView.scrollToPosition(insertIndex); // 滚到新记录，方便看到效果
        });
        recyclerView.setAdapter(adapter);
    }

    /** 原动物图片资源（实验1 用的那 6 张） */
    private void initData() {
        data.add(new AnimalAdapter.Animal("Lion", R.drawable.lion));
        data.add(new AnimalAdapter.Animal("Tiger", R.drawable.tiger));
        data.add(new AnimalAdapter.Animal("Monkey", R.drawable.monkey));
        data.add(new AnimalAdapter.Animal("Dog", R.drawable.dog));
        data.add(new AnimalAdapter.Animal("Cat", R.drawable.cat));
        data.add(new AnimalAdapter.Animal("Elephant", R.drawable.elephant));
    }
}