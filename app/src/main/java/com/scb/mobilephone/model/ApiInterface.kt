package com.scb.mobilephone.model



import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/api/mobiles/")
    fun getMobileList(): Call<List<Mobiles>>

    @GET("/api/mobiles/{mobile_id}/images/")
    fun getImage(@Path("mobile_id") mobile_id: String):Call<List<MobileDetail>>

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
