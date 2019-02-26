package bai.smartdevservice.xebiaapps.view

import bai.smartdevservice.xebiaapps.model.data.OfferResponse

interface OffersBooksContract {

    fun onFailure()
    fun onResponse(offerBooks: OfferResponse)
}
