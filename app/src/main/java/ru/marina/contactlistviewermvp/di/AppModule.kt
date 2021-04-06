package ru.marina.contactlistviewermvp.di

import android.content.Context
import okhttp3.OkHttpClient
import ru.marina.contactlistviewermvp.data.db.AppDb
import ru.marina.contactlistviewermvp.data.db.dao.ContactsDao
import ru.marina.contactlistviewermvp.data.mapper.ContactsMapper
import ru.marina.contactlistviewermvp.data.mapper.ContactsMapperImpl
import ru.marina.contactlistviewermvp.data.prefs.Prefs
import ru.marina.contactlistviewermvp.data.prefs.PrefsImpl
import ru.marina.contactlistviewermvp.network.api.ApiService
import ru.marina.contactlistviewermvp.network.remote.ContactsRemote
import ru.marina.contactlistviewermvp.network.remote.ContactsRemoteImpl
import ru.marina.contactlistviewermvp.repository.ContactsRepository
import ru.marina.contactlistviewermvp.repository.ContactsRepositoryImpl
import toothpick.ktp.binding.module

fun appModule(context: Context) = module {
    bind(Context::class.java).toInstance(context)

    bind(AppDb::class.java).toProvider(AppDbProvider::class.java).providesSingleton()
    bind(ContactsDao::class.java).toProvider(ContactsDaoProvider::class.java).providesSingleton()

    bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java).providesSingleton()
    bind(ApiService::class.java).toProvider(ApiServiceProvider::class.java).providesSingleton()

    bind(ContactsMapper::class.java).to(ContactsMapperImpl::class.java).singleton()
    bind(ContactsRemote::class.java).to(ContactsRemoteImpl::class.java).singleton()
    bind(ContactsRepository::class.java).to(ContactsRepositoryImpl::class.java).singleton()

    bind(Prefs::class.java).to(PrefsImpl::class.java).singleton()
}