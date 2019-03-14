package bai.smartdevservice.xebiaapps.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import bai.smartdevservice.xebiaapps.R
import bai.smartdevservice.xebiaapps.model.data.ItemBooks
import bai.smartdevservice.xebiaapps.presenter.BooksPresenter
import bai.smartdevservice.xebiaapps.util.Constant
import bai.smartdevservice.xebiaapps.view.adapter.MyBooksRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_books.*


class BooksActivity : AppCompatActivity(), BooksContract {

    private var TAG = "BooksActivity"

    private lateinit var textCartItemCount: TextView
    private var mCartItemCount: Int = 0
    private var mBookBasket = ArrayList<ItemBooks>()

    override fun onFailure() {
        Log.d(TAG, "onFailure")
    }

    override fun onResponse(books: ArrayList<ItemBooks>) {

        Log.d(TAG, "onResponse")
        val adapter = MyBooksRecyclerViewAdapter(this@BooksActivity, books, object : OnListBooksInteractionListener {
            override fun onBookLongClick(item: ItemBooks?) {

                item?.let { mBookBasket.add(it) }
                mCartItemCount++
                setupBadge()
            }
        })
        books_recycler_view.adapter = adapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        books_recycler_view.layoutManager = LinearLayoutManager(this@BooksActivity)


        val booksPresenter = BooksPresenter(this@BooksActivity)

        booksPresenter.getAllBooks()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.panier_menu, menu)

        val menuItem = menu?.findItem(R.id.action_cart)

        textCartItemCount = menuItem?.actionView?.findViewById(R.id.cart_badge) as TextView
        menuItem.actionView.setOnClickListener { onOptionsItemSelected(menuItem) }

        setupBadge()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        Log.d(TAG, "on click")
        Log.d(TAG, "on click -- item.itemId = " + item.itemId)
        return when (item.itemId) {
            R.id.action_cart -> {
                // Action goes here
                Log.d(TAG, "on click")
                openBasket()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun setupBadge() {

        if (mCartItemCount == 0) {

            textCartItemCount.setVisibility(View.GONE)

        } else {
            textCartItemCount.setText((Math.min(mCartItemCount, 99).toString()))

            textCartItemCount.setVisibility(View.VISIBLE)

        }

    }

    fun openBasket() {

        if(mBookBasket.size ==0){

            Toast.makeText(this, "Votre panier est vide !!!", Toast.LENGTH_LONG).show()
            return
        }
        intent = Intent(this@BooksActivity, BooksBasketActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelableArrayList(Constant.KeyExtra.KEY_BOOKS, mBookBasket)
        intent.putExtras(bundle)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    interface OnListBooksInteractionListener {
        fun onBookLongClick(item: ItemBooks?)
    }


}
