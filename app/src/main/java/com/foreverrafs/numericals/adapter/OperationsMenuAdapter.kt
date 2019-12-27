package com.foreverrafs.numericals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foreverrafs.numericals.R
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter.MenuViewHolder
import com.foreverrafs.numericals.model.OperationMenu
import kotlinx.android.synthetic.main.item_menu.view.*

/**
 * Created by Rafsanjani on 6/5/2019
 */
class OperationsMenuAdapter(private val menuList: List<OperationMenu>) : RecyclerView.Adapter<MenuViewHolder>() {
    private lateinit var itemClickListener: MenuItemClickListenener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
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

    interface MenuItemClickListenener {
        fun onMenuItemClicked(menuItemType: Int)
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val menu = menuList[position]
            itemView.image_menu_item.setImageResource(menu.imageResource)
            itemView.text_menu_title.text = menu.title
            //only attach a listener if there are sub menus

            itemView.image_menu_item!!.setOnClickListener {
                val menuCategory = menu.menuCategory
                itemClickListener.onMenuItemClicked(menuCategory)
            }
        }
    }

}