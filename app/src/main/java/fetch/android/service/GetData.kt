package fetch.android.service

import fetch.android.data.Item
import retrofit2.Call
import retrofit2.http.GET

const val URL: String = "hiring.json"
interface GetData {
    @GET(URL)
    fun fetchItems(): Call<List<Item>>
}