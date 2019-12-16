/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tuga.konum.view.material

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tuga.konum.databinding.NavDividerItemLayoutBinding
import com.tuga.konum.databinding.NavEmailFolderItemLayoutBinding
import com.tuga.konum.databinding.NavMenuItemLayoutBinding
import com.tuga.konum.view.material.NavigationModelItem.NavDivider
import com.tuga.konum.view.material.NavigationModelItem.NavEmailFolder
import com.tuga.konum.view.material.NavigationModelItem.NavMenuItem

sealed class NavigationViewHolder<T : NavigationModelItem>(
  view: View
) : RecyclerView.ViewHolder(view) {

  abstract fun bind(navItem: T)

  class NavMenuItemViewHolder(
    private val binding: NavMenuItemLayoutBinding,
    private val listener: NavigationAdapter.NavigationAdapterListener
  ) : NavigationViewHolder<NavMenuItem>(binding.root) {

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
  ) : NavigationViewHolder<NavDivider>(binding.root) {
    override fun bind(navItem: NavigationModelItem.NavDivider) {
      binding.navDivider = navItem
      binding.executePendingBindings()
    }
  }

  class EmailFolderViewHolder(
    private val binding: NavEmailFolderItemLayoutBinding,
    private val listener: NavigationAdapter.NavigationAdapterListener
  ) : NavigationViewHolder<NavEmailFolder>(binding.root) {

    override fun bind(navItem: NavigationModelItem.NavEmailFolder) {
      binding.run {
        navEmailFolder = navItem
        navListener = listener
        executePendingBindings()
      }
    }
  }
}