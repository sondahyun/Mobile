package ddum.com.mobile.week09.naverretrofitsample.data.network

import android.content.Context
import ddum.com.mobile.week09.naverretrofitsample.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NVService(val context: Context) {
    private val TAG = "NVService"
    private val service : INaverBookSearch

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.url))
            .addConverterFactory( GsonConverterFactory.create() )
            .build()

        service = retrofit.create(INaverBookSearch::class.java)
    }


    // Naver OpenAPI 를 이용하여 책 검색결과 반환
    suspend fun getBooks(query: String) : List<Book>? {
        val clientID = context.resources.getString(R.string.client_id)
        val clientSecret = context.resources.getString(R.string.client_secret)

        val bookRoot = service.getBooksByKeyword(query, clientID, clientSecret)
        return bookRoot.items
    }

    // Glide 를 사용하여 책 이미지를 가져와 Bitmap 으로 반환
//    suspend fun getImage(url: String?, view: ImageView) : Bitmap {
//
//
//
//
//    }

}