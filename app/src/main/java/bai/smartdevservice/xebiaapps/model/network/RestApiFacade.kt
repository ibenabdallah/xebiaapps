/**
 * @author B.A.Ismail
 */
package bai.smartdevservice.xebiaapps.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RestApiFacade {

    private var mInterfaceAPI: InterfaceAPI? = null
    private val BASE_API_URL = "http://henri-potier.xebia.fr/"

    fun getRestApi(): InterfaceAPI? {

        if (mInterfaceAPI == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            mInterfaceAPI = retrofit.create(InterfaceAPI::class.java)
        }
        return mInterfaceAPI
    }
}
