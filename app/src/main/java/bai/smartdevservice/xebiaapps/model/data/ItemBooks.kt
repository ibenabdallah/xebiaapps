package bai.smartdevservice.xebiaapps.model.data

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class ItemBooks protected constructor(`in`: Parcel) : Parcelable {

    internal var isbn: String? = null
    internal var title: String? = null
    internal var price: Double? = null
    internal var cover: String? = null
    internal var synopsis: ArrayList<String>? = null

    init {
        isbn = `in`.readString()
        title = `in`.readString()
        if (`in`.readByte().toInt() == 0) {
            price = null
        } else {
            price = `in`.readDouble()
        }
        cover = `in`.readString()
        synopsis = `in`.createStringArrayList()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(isbn)
        parcel.writeString(title)
        if (price == null) {
            parcel.writeByte(0.toByte())
        } else {
            parcel.writeByte(1.toByte())
            parcel.writeDouble(price!!)
        }
        parcel.writeString(cover)
        parcel.writeStringList(synopsis)
    }

    companion object CREATOR : Parcelable.Creator<ItemBooks> {
        override fun createFromParcel(parcel: Parcel): ItemBooks {
            return ItemBooks(parcel)
        }

        override fun newArray(size: Int): Array<ItemBooks?> {
            return arrayOfNulls(size)
        }
    }

}