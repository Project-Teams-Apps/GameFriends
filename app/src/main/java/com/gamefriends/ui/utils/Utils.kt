package com.gamefriends.ui.utils

import android.view.View

fun showLoadingIndicator(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.INVISIBLE
}

fun isValidEmail(email: CharSequence): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}