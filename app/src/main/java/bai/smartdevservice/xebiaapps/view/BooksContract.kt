package bai.smartdevservice.xebiaapps.view

import bai.smartdevservice.xebiaapps.model.data.ItemBooks

interface BooksContract {

    fun onFailure()
    fun onResponse(books: ArrayList<ItemBooks>)
}
