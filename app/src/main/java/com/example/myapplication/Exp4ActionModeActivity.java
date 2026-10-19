package com.example.myapplication;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exp4ActionModeActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    /** CAB 自定义标题里显示 "N selected" 的那个 TextView */
    private TextView tvSelected;

    /** 和 PDF 图一致：One ~ Five */
    private final List<String> data = new ArrayList<>(
            Arrays.asList("One", "Two", "Three", "Four", "Five"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exp4);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("实验4  上下文操作模式");

        // 自定义列表项：机器人图标 + 名称
        adapter = new ArrayAdapter<>(this, R.layout.item_action, R.id.tv_name, data);
        listView = findViewById(R.id.lv_action);
        listView.setAdapter(adapter);

        // 多选模态：长按某项进入 ActionMode
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_exp4, menu);

                // ★ 关键：用 setCustomView 把 "✓  N selected" 放到标题位置
                View titleView = getLayoutInflater()
                        .inflate(R.layout.action_mode_title, null);
                tvSelected = titleView.findViewById(R.id.tv_selected);
                titleView.findViewById(R.id.iv_done).setOnClickListener(v -> mode.finish());
                mode.setCustomView(titleView);

                updateTitle();
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                updateTitle();
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.action_delete) {
                    deleteSelected();
                    Toast.makeText(Exp4ActionModeActivity.this,
                            "已删除", Toast.LENGTH_SHORT).show();
                    mode.finish();

                } else if (itemId == R.id.action_select_all) {
                    for (int i = 0; i < data.size(); i++) {
                        listView.setItemChecked(i, true);
                    }
                    updateTitle();
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                tvSelected = null;
                listView.clearChoices();
            }
        });
    }

    /** 更新 "N selected"（和图中文案一致） */
    private void updateTitle() {
        if (tvSelected != null) {
            tvSelected.setText(listView.getCheckedItemCount() + " selected");
        }
    }

    private String getSelectedNames() {
        StringBuilder sb = new StringBuilder();
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            if (checked.valueAt(i)) {
                sb.append(data.get(checked.keyAt(i))).append(" ");
            }
        }
        return sb.toString().trim();
    }

    private void deleteSelected() {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        List<String> toRemove = new ArrayList<>();
        for (int i = 0; i < checked.size(); i++) {
            if (checked.valueAt(i)) {
                toRemove.add(data.get(checked.keyAt(i)));
            }
        }
        data.removeAll(toRemove);
        adapter.notifyDataSetChanged();
        listView.clearChoices();
    }
}