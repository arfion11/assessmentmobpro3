package org.d3if0072.assessmentmobpro3.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if0072.assessmentmobpro3.model.Laptop
import org.d3if0072.assessmentmobpro3.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://unspoken.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface LaptopApiService {
    @GET("laptop.php")
    suspend fun getLaptop(
        @Header ("Authorization") userId: String
    ): List<Laptop>


    @Multipart
    @POST("laptop.php")
    suspend fun postLaptop(
        @Header("Authorization") userId: String,
        @Part("brand") brand: RequestBody,
        @Part("namaLaptop") namaLaptop: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("laptop.php")
    suspend fun deleteLaptop(
        @Header("Authorization") userId: String,
        @Query("id") laptopId: String,
    ): OpStatus
}
object LaptopApi {
    val service: LaptopApiService by lazy {
        retrofit.create(LaptopApiService::class.java)
    }

    fun getLaptopUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class ApiStatus {LOADING, SUCCES, FAILED}