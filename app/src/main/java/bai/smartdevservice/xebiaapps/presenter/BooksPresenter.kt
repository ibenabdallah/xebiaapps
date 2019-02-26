package bai.smartdevservice.xebiaapps.presenter

import android.util.Log
import bai.smartdevservice.xebiaapps.model.data.ItemBooks
import bai.smartdevservice.xebiaapps.model.network.RestApiFacade
import bai.smartdevservice.xebiaapps.view.BooksContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksPresenter(var booksContract: BooksContract?) : Callback<ArrayList<ItemBooks>> {

    var TAG = "BooksPresenter"

    fun getAllBooks() {
        val interfaceAPI = RestApiFacade.getRestApi()
        val call = interfaceAPI?.getAllBooks()
        call?.enqueue(this@BooksPresenter)
    }

    override fun onFailure(call: Call<ArrayList<ItemBooks>>, t: Throwable) {
        Log.e(TAG, "onFailure : t = " + t.toString())
        booksContract?.onFailure()
    }

    override fun onResponse(call: Call<ArrayList<ItemBooks>>, response: Response<ArrayList<ItemBooks>>) {

        Log.e(TAG, "onResponse")

        if (response.code() == 200) {
            val allBooksResponse = response.body()
            booksContract?.onResponse(allBooksResponse!!)
        }
    }

}
