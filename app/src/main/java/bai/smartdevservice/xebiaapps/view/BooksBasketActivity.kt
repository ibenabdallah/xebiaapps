package bai.smartdevservice.xebiaapps.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import bai.smartdevservice.xebiaapps.R
import bai.smartdevservice.xebiaapps.model.data.ItemBooks
import bai.smartdevservice.xebiaapps.model.data.Offer
import bai.smartdevservice.xebiaapps.model.data.OfferResponse
import bai.smartdevservice.xebiaapps.presenter.OffersBooksPresenter
import bai.smartdevservice.xebiaapps.util.Constant
import bai.smartdevservice.xebiaapps.view.adapter.MyBooksRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_books_basket.*

class BooksBasketActivity : AppCompatActivity(), OffersBooksContract {

    private var TAG = "BooksBasketActivity"
    private var PERCENTAGE = "percentage"
    private var MINUS = "minus"
    private var SLICE = "slice"

    private var price: Double = 0.0

    override fun onFailure() {
        Log.d(TAG, "onFailure")
    }

    override fun onResponse(offerBooks: OfferResponse) {

        Log.d(TAG, "onResponse")
        Log.d(TAG, "onResponse : price = $price")

        val offers = offerBooks.offers
        var priceOffer1 = this.price
        var priceOffer2 = this.price
        var priceOffer3 = this.price

        var value1: Int? = null
        var value2: Int? = null
        var value3: Int? = null
        var sliceValue3: Int? = null

        Log.d(TAG, "onResponse : offers.size = " + offers.size)

        for (offer: Offer in offers) {
            when (offer.type) {

                PERCENTAGE -> {
                    value1 = offer.value
                    priceOffer1 = price - ((price / 100) * offer.value)
                    Log.d(TAG, "onResponse : PERCENTAGE = $priceOffer1")
                }

                MINUS -> {
                    value2 = offer.value
                    priceOffer2 = price - offer.value
                    Log.d(TAG, "onResponse : MINUS = $priceOffer2")
                }

                SLICE -> {
                    value3 = offer.value
                    sliceValue3 = offer.sliceValue
                    val nbr = (price / offer.sliceValue).toInt()
                    priceOffer3 = price - (offer.value * nbr)
                    Log.d(TAG, "onResponse : SLICE = $priceOffer3")
                }
            }
        }
        books_price_before_reduction.setText(getString(R.string.price_before_reduction, price))
        if ((priceOffer1 <= priceOffer2) && (priceOffer1 <= priceOffer3)) {

            books_offers.setText(getString(R.string.offer_porcentage, value1))
            books_price_after_reduction.setText(getString(R.string.price_after_reduction, priceOffer1))

        } else if (priceOffer2 <= priceOffer1 && priceOffer2 <= priceOffer3) {

            books_offers.setText(getString(R.string.offer_minus, value2))
            books_price_after_reduction.setText(getString(R.string.price_after_reduction, priceOffer2))

        } else {

            books_offers.setText(getString(R.string.offer_slice, value3, sliceValue3))
            books_price_after_reduction.setText(getString(R.string.price_after_reduction, priceOffer3))

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_basket)

        books_recycler_view.layoutManager = LinearLayoutManager(this@BooksBasketActivity)


        val books: java.util.ArrayList<ItemBooks>? = intent?.extras?.getParcelableArrayList(Constant.KeyExtra.KEY_BOOKS)

        books_recycler_view.adapter = MyBooksRecyclerViewAdapter(this@BooksBasketActivity, books, null)


        var listIdBooks: String? = null

        for (item: ItemBooks in books!!) {
            if (listIdBooks == null) {
                listIdBooks = item.isbn
                price = item.price!!
            } else {
                listIdBooks = listIdBooks + "," + item.isbn
                price = price + item.price!!
            }
        }

        Log.d(TAG, "listIdBooks = " + listIdBooks)
        val offersBooksPresenter = OffersBooksPresenter(this@BooksBasketActivity)
        offersBooksPresenter.getOffersBooks(listIdBooks)
    }


    interface OnListBooksBasketInteractionListener {
        fun onBookClick(item: ItemBooks?)
    }


}
