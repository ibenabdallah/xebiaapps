package bai.smartdevservice.xebiaapps.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import bai.smartdevservice.xebiaapps.R
import bai.smartdevservice.xebiaapps.model.data.ItemBooks
import bai.smartdevservice.xebiaapps.view.BooksActivity.OnListBooksInteractionListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_book.view.*
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [ItemPlayer] and makes a call to the
 * specified [OnListBooksInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyBooksRecyclerViewAdapter(
    private val mContext: Context,
    private val mValues: ArrayList<ItemBooks>?,
    private val mListener: OnListBooksInteractionListener?
) : RecyclerView.Adapter<MyBooksRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnLongClickListener

    init {
        mOnClickListener = View.OnLongClickListener { v ->
            val item = v.tag as ItemBooks
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onBookLongClick(item)
            return@OnLongClickListener true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues?.get(position)

        holder.mNameTextView.text = item?.title
        holder.mPriceTextView.text = mContext.getString(R.string.book_price, item?.price.toString())
        holder.mDescriptionTextView.text = mContext.getString(R.string.book_description, item?.synopsis!![0])

        Glide
            .with(mContext)
            .load(item.cover)
            .centerInside()
            .into(holder.mLogoImageView);

        with(holder.mView) {
            tag = item
            setOnLongClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues?.size!!

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mLogoImageView: ImageView = mView.book_logo_image_view
        val mNameTextView: TextView = mView.book_name_text_view
        val mPriceTextView: TextView = mView.book_price_text_view
        val mDescriptionTextView: TextView = mView.book_description_text_view


        override fun toString(): String {
            return super.toString()
        }
    }
}
