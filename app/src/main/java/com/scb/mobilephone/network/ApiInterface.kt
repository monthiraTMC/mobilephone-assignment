package com.scb.mobilephone.network



import com.scb.mobilephone.model.Mobiles
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("/api/mobiles/")
    fun getMobileList(): Call<List<Mobiles>>
//    @GET("/api/mobiles/{mobile_id}/images/")
//    fun getPokemonImg(@Path("mobile_id") mobileID: String): Call<Mobile>

    companion object Factory {
        private val BASE_URL = "https://scb-test-mobile.herokuapp.com/"
        //private val BASE_URL = "https://jsonplaceholder.typicode.com/"

        private var retrofit: Retrofit? = null

        fun getClient(): ApiInterface {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!.create(ApiInterface::class.java)
        }
    }

}
