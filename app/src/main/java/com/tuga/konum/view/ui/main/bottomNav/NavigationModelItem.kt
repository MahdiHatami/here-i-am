package com.tuga.konum.view.ui.main.bottomNav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil

/**
 * A sealed class which encapsulates all objects [NavigationAdapter] is able to display.
 */
sealed class NavigationModelItem {

  /**
   * A class which represents a checkable, navigation destination such as 'Inbox' or 'Sent'.
   */
  data class NavMenuItem(
    val id: Int,
    @DrawableRes val icon: Int,
    @StringRes val titleRes: Int,
    var checked: Boolean
  ) : NavigationModelItem()

  /**
   * A class which is used to show a section divider (a subtitle and underline) between
   * sections of differen NavigationModelItem types.
   */
  data class NavDivider(val title: String) : NavigationModelItem()

  object NavModelItemDiff : DiffUtil.ItemCallback<NavigationModelItem>() {
    override fun areItemsTheSame(
      oldItem: NavigationModelItem,
      newItem: NavigationModelItem
    ): Boolean {
      return when {
        oldItem is NavMenuItem && newItem is NavMenuItem ->
          oldItem.id == newItem.id
        else -> oldItem == newItem
      }
    }

    override fun areContentsTheSame(
      oldItem: NavigationModelItem,
      newItem: NavigationModelItem
    ): Boolean {
      return when {
        oldItem is NavMenuItem && newItem is NavMenuItem ->
          oldItem.icon == newItem.icon &&
              oldItem.titleRes == newItem.titleRes &&
              oldItem.checked == newItem.checked
        else -> false
      }
    }
  }
}
