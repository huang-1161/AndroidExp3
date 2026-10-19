package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    /** 数据模型：名称 + 图片资源 */
    public static class Animal {
        public final String name;
        public final int iconRes;

        public Animal(String name, int iconRes) {
            this.name = name;
            this.iconRes = iconRes;
        }
    }

    /** 点击回调 */
    public interface OnItemClickListener {
        void onItemClick(int position, Animal animal);
    }

    private final List<Animal> data;
    private final OnItemClickListener listener;

    public AnimalAdapter(List<Animal> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    /** 创建 ViewHolder：把一个 item 布局膨胀出来 */
    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    /** 绑定数据：屏幕上滚动复用的就是这一步 */
    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = data.get(position);
        holder.tvName.setText(animal.name);
        holder.ivIcon.setImageResource(animal.iconRes);

        holder.itemView.setOnClickListener(v -> {
            // 关键：点击时才取当前位置，不要用在 onBindViewHolder 里捕获的 position
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && listener != null) {
                listener.onItemClick(pos, data.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /** ViewHolder：缓存 item 里的控件，避免每次 findViewById */
    static class AnimalViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final ImageView ivIcon;

        AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}