package ddwu.com.mobileapp.week05.networkbasic.data.network

import android.content.Context
import android.graphics.Bitmap
import ddwu.com.mobileapp.week05.networkbasic.data.network.util.NetworkUtil

class NetworkService(context: Context) {

    val TAG = "NetworkService"

    val networkUtil = NetworkUtil(context)

    fun getTextConnInfo() : String {
        return networkUtil.getNetworkInfo()
    }

    fun getOnlineStatus() : Boolean {
        return networkUtil.isOnline()
    }

    suspend fun getTextData(address: String) : String {
        return networkUtil.downloadText(address) ?: "no data"
    }

    // networkUtil 의 이미지 요청 함수 호출
    // 함수명 : getImageData
    // 매개변수 : address: String
    // 반환타입: Bitmap?


    // networkUtil 의 post 요청 함수 호출
    // 함수명 : getPostData
    // 매개변수: address: String, data: String
    // 반환타입: String

}