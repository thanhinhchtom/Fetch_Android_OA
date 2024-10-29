package fetch.android.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataObject {
    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    val data: GetData by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetData::class.java)
    }
}