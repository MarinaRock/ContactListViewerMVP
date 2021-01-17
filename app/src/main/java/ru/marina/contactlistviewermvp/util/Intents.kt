package ru.marina.contactlistviewermvp.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

object Intents {

    fun callPhone(context: Context, phone: String) {
        val intent = Intent(
            Intent.ACTION_CALL,
            Uri.parse("tel:+" + phone.replace(Regex("\\D"), ""))
        )
        context.startActivity(intent)
    }

    fun openPermissionsSetting(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + context.packageName)
        context.startActivity(intent)
    }
}