package bai.smartdevservice.xebiaapps.presenter

import android.util.Log
import bai.smartdevservice.xebiaapps.model.data.OfferResponse
import bai.smartdevservice.xebiaapps.model.network.RestApiFacade
import bai.smartdevservice.xebiaapps.view.OffersBooksContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OffersBooksPresenter(var offersBooksContract: OffersBooksContract?) : Callback<OfferResponse> {

    var TAG = "OffersBooksPresenter"

    fun getOffersBooks(listBooks: String?) {
        val interfaceAPI = RestApiFacade.getRestApi()
        val call = interfaceAPI?.getOffresCommercialesBooks(listBooks)
        call?.enqueue(this@OffersBooksPresenter)
    }

    override fun onFailure(call: Call<OfferResponse>, t: Throwable) {
        Log.i(TAG, "onFailure : t = " + t.toString())
        offersBooksContract?.onFailure()
    }

    override fun onResponse(call: Call<OfferResponse>, response: Response<OfferResponse>) {

        Log.i(TAG, "onResponse")

        if (response.code() == 200) {
            val booksResponse = response.body()
            Log.i(TAG, "onResponse - response = " + response.toString())
            Log.i(TAG, "onResponse - booksResponse = " + booksResponse.toString())
            offersBooksContract?.onResponse(booksResponse!!)
        }
    }

}
