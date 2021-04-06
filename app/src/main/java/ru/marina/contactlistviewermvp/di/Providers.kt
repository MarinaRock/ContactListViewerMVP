package ru.marina.contactlistviewermvp.di

import android.content.Context
import androidx.room.Room
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.marina.contactlistviewermvp.BuildConfig
import ru.marina.contactlistviewermvp.data.db.AppDb
import ru.marina.contactlistviewermvp.data.db.dao.ContactsDao
import ru.marina.contactlistviewermvp.network.ErrorInterceptor
import ru.marina.contactlistviewermvp.network.api.ApiService
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class AppDbProvider @Inject constructor(private val context: Context) : Provider<AppDb> {

    override fun get(): AppDb =
        Room.databaseBuilder(context, AppDb::class.java, "app.db")
            .fallbackToDestructiveMigration()
            .build()
}

class ContactsDaoProvider @Inject constructor(private val appDb: AppDb) : Provider<ContactsDao> {

    override fun get(): ContactsDao = appDb.contactsDao()
}

class OkHttpProvider @Inject constructor(private val context: Context) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ErrorInterceptor(context))
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .build()
}

class ApiServiceProvider @Inject constructor(private val okHttpClient: OkHttpClient) :
    Provider<ApiService> {

    override fun get(): ApiService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
}