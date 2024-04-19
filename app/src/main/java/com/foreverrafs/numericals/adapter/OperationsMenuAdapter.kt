package com.foreverrafs.numericals.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter.MenuViewHolder
import com.foreverrafs.numericals.databinding.ItemMenuBinding
import com.foreverrafs.numericals.model.OperationMenu

/** Created by Rafsanjani on 6/5/2019 */
class OperationsMenuAdapter(private val menuList: List<OperationMenu>) : RecyclerView.Adapter<MenuViewHolder>() {
    private lateinit var itemClickListener: MenuItemClickListenener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setOnItemClickListenener(itemClickListenener: MenuItemClickListenener) {
        itemClickListener = itemClickListenener
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    fun interface MenuItemClickListenener {
        fun onMenuItemClicked(menuItemType: Int)
    }

    inner class MenuViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val menu = menuList[position]
            binding.imageMenuItem.setImageResource(menu.imageResource)
            binding.textMenuTitle.text = menu.title
            //only attach a listener if there are sub menus

            binding.imageMenuItem.setOnClickListener {
                val menuCategory = menu.menuCategory
                itemClickListener.onMenuItemClicked(menuCategory)
            }
        }
    }

}