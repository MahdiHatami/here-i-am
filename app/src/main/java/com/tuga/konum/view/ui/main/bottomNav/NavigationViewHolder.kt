package com.tuga.konum.view.ui.main.bottomNav

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tuga.konum.databinding.NavDividerItemLayoutBinding
import com.tuga.konum.databinding.NavMenuItemLayoutBinding

sealed class NavigationViewHolder<T : NavigationModelItem>(
  view: View
) : RecyclerView.ViewHolder(view) {

  abstract fun bind(navItem: T)

  class NavMenuItemViewHolder(
    private val binding: NavMenuItemLayoutBinding,
    private val listener: NavigationAdapter.NavigationAdapterListener
  ) : NavigationViewHolder<NavigationModelItem.NavMenuItem>(binding.root) {

    override fun bind(navItem: NavigationModelItem.NavMenuItem) {
      binding.run {
        navMenuItem = navItem
        navListener = listener
        executePendingBindings()
      }
    }
  }

  class NavDividerViewHolder(
    private val binding: NavDividerItemLayoutBinding
  ) : NavigationViewHolder<NavigationModelItem.NavDivider>(binding.root) {
    override fun bind(navItem: NavigationModelItem.NavDivider) {
      binding.navDivider = navItem
      binding.executePendingBindings()
    }
  }
}
