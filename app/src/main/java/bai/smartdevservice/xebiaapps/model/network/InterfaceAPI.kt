/*
 * @author B.A.Ismail
 * 
 */
package bai.smartdevservice.xebiaapps.model.network

import bai.smartdevservice.xebiaapps.model.data.ItemBooks
import bai.smartdevservice.xebiaapps.model.data.OfferResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Interface for Test API.
 *
 */
interface InterfaceAPI {

    @GET("books")
    fun getAllBooks(): Call<ArrayList<ItemBooks>>

    @GET("books/{listBooks}/commercialOffers")
    fun getOffresCommercialesBooks(@Path("listBooks") listBooks: String): Call<OfferResponse>

}
