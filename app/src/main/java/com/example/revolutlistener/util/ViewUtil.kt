package com.example.revolutlistener.util

import android.view.View
import android.view.ViewGroup

fun getViewsByTag(root: ViewGroup, tag: String): ArrayList<View>? {
    val views: ArrayList<View> = ArrayList<View>()
    val childCount = root.childCount
    for (i in 0 until childCount) {
        val child: View = root.getChildAt(i)
        if (child is ViewGroup) {
            views.addAll(getViewsByTag(child, tag)!!)
        }
        val tagObj: Any = child.tag
        if (tagObj != null && tagObj == tag) {
            views.add(child)
        }
    }
    return views
}
