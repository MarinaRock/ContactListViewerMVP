package ru.marina.contactlistviewermvp.ui.extensions

import android.widget.ImageView
import androidx.appcompat.widget.SearchView

fun SearchView.enable() {
    this.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn).isEnabled = true
    this.findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text).isEnabled =
        true
}

fun SearchView.disable() {
    this.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn).isEnabled = false
    this.findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text).isEnabled =
        false
}