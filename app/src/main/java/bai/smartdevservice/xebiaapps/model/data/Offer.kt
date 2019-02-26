package bai.smartdevservice.xebiaapps.model.data

class Offer {

    lateinit var type: String
    var sliceValue: Int = 0
    var value: Int = 0

    override fun toString(): String {
        return "Offer(type=$type, sliceValue=$sliceValue, value=$value)"
    }


}
