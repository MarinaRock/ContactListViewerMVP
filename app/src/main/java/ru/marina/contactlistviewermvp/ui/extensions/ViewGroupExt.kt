package ru.marina.contactlistviewermvp.ui.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.reflect.KClass
import kotlin.reflect.cast

fun <T : View> ViewGroup.findViewsByClass(
    clazz: KClass<T>
): List<T> {
    val views: MutableList<T> = mutableListOf()
    this.children.forEach { v ->
        if (clazz.isInstance(v)) {
            views.add(clazz.cast(v))
        } else if (v is ViewGroup) {
            views.addAll(v.findViewsByClass(clazz))
        }
    }
    return views
}

fun <T : View> ViewGroup.findFirstViewByClass(
    clazz: KClass<T>
): T? {
    val views: List<T> = this.findViewsByClass(clazz)
    return if (views.isNotEmpty()) views.first() else null
}

fun ViewGroup.showChildrenViews() {
    this.children.forEach { child -> child.visible() }
}

fun ViewGroup.hideChildrenViews() {
    this.children.forEach { child -> child.gone() }
}