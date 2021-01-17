package ru.marina.contactlistviewermvp.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrefsImpl @Inject constructor(context: Context) : Prefs {

    companion object {
        const val PREFS_NAME = "contacts"

        const val PREFS_LAST_CACHE_TIME = "PREFS_LAST_CACHE_TIME"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var lastCacheTime by LongPreference(
        prefs,
        PREFS_LAST_CACHE_TIME,
        0L
    )

    class LongPreference(
        private val preferences: SharedPreferences,
        private val name: String,
        private val defaultValue: Long
    ) : ReadWriteProperty<Any, Long> {

        override fun getValue(thisRef: Any, property: KProperty<*>): Long {
            return preferences.getLong(name, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
            preferences.edit { putLong(name, value) }
        }
    }
}