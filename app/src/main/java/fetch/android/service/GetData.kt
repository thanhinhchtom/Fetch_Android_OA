package fetch.android.service

import fetch.android.data.Item
import retrofit2.Call
import retrofit2.http.GET

interface GetData {
    @GET("hiring.json")
    fun fetchItems(): Call<List<Item>>
}