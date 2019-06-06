package com.foreverrafs.numericals.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.model.OperationMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rafsanjani on 6/5/2019
 */
public class OperationsMenuAdapter extends RecyclerView.Adapter<OperationsMenuAdapter.MenuViewHolder> {
    private final List<OperationMenu> menuList;


    public OperationsMenuAdapter(List<OperationMenu> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        OperationMenu menu = menuList.get(position);

        holder.menuImage.setImageResource(menu.getImageResource());
        holder.menuTitle.setText(menu.getTitle());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_menu_item)
        ImageView menuImage;

        @BindView(R.id.text_menu_title)
        TextView menuTitle;

        MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
