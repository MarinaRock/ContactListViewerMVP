package ru.marina.contactlistviewermvp.network.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.marina.contactlistviewermvp.network.reponse.ContactResponse

interface ApiService {

    @GET("MarinaRock/ContactListViewerMVP/main/json/{source}")
    fun getContacts(@Path("source") source: String): Single<List<ContactResponse>>
}