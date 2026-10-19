package com.example.myapplication;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exp1ListActivity extends AppCompatActivity {

    /** 通知渠道 id，8.0 以上每条通知都必须指定渠道 */
    private static final String CHANNEL_ID = "list_click_channel";
    /** 通知权限请求码 */
    private static final int REQ_POST_NOTIFICATIONS = 100;

    private final String[] names = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};

    private final int[] icons = {
            R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
            R.drawable.dog, R.drawable.cat, R.drawable.elephant
    };

    private int notifyId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exp1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createNotificationChannel();
        requestNotificationPermission();

        ListView listView = findViewById(R.id.lv_animals);
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                buildData(),
                R.layout.list_item,
                new String[]{"name", "icon"},
                new int[]{R.id.tv_name, R.id.iv_icon});
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);   // ← 新增：单选模式
        listView.setOnItemClickListener((parent, view, position, id) -> {
            listView.setItemChecked(position, true);   // ← 新增：让点击的这一行保持高亮
            Map<?, ?> item = (Map<?, ?>) parent.getItemAtPosition(position);
            String name = String.valueOf(item.get("name"));

            Toast.makeText(Exp1ListActivity.this, "你选中了：" + name, Toast.LENGTH_SHORT).show();

            sendNotification(name);
        });
    }

    private List<Map<String, Object>> buildData() {
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", names[i]);
            item.put("icon", icons[i]);
            data.add(item);
        }
        return data;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "列表点击通知", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("点击列表项时发送的通知");
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    REQ_POST_NOTIFICATIONS);
        }
    }

    private void sendNotification(String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "还没授予通知权限，无法发送通知", Toast.LENGTH_SHORT).show();
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText("《Android界面组件实验》已选中 " + title + "，点击查看详情。")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat.from(this).notify(notifyId++, builder.build());
    }
}