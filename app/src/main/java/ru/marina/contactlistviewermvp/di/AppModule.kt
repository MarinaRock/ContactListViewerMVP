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
import ru.marina.contactlistviewermvp.data.mapper.ContactsMapper
import ru.marina.contactlistviewermvp.data.mapper.ContactsMapperImpl
import ru.marina.contactlistviewermvp.data.prefs.Prefs
import ru.marina.contactlistviewermvp.data.prefs.PrefsImpl
import ru.marina.contactlistviewermvp.network.ErrorInterceptor
import ru.marina.contactlistviewermvp.network.api.ApiService
import ru.marina.contactlistviewermvp.network.remote.ContactsRemote
import ru.marina.contactlistviewermvp.network.remote.ContactsRemoteImpl
import ru.marina.contactlistviewermvp.repository.ContactsRepository
import ru.marina.contactlistviewermvp.repository.ContactsRepositoryImpl
import toothpick.ktp.binding.module
import java.util.concurrent.TimeUnit

fun appModule(context: Context) = module {
    bind(Context::class.java).toInstance(context)

    val db = Room
        .databaseBuilder(context, AppDb::class.java, "app.db")
        .fallbackToDestructiveMigration()
        .build()
    bind(AppDb::class.java).toInstance(db)
    val contactsDao = db.contactsDao()
    bind(ContactsDao::class.java).toInstance(contactsDao)

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ErrorInterceptor(context))
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build()
    bind(OkHttpClient::class.java).toInstance(okHttpClient)
    val apiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BaseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiService::class.java)
    bind(ApiService::class.java).toInstance(apiService)

    bind(ContactsMapper::class.java).to(ContactsMapperImpl::class.java).singleton()
    bind(ContactsRemote::class.java).to(ContactsRemoteImpl::class.java).singleton()
    bind(Prefs::class.java).to(PrefsImpl::class.java).singleton()
    bind(ContactsRepository::class.java).to(ContactsRepositoryImpl::class.java).singleton()
}