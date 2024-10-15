package ddwu.com.mobileapp.week05.networkbasic.data

import android.graphics.Bitmap
import androidx.annotation.WorkerThread
import ddwu.com.mobileapp.week05.networkbasic.data.network.NetworkService

class NetworkRepository(private val netService: NetworkService) {

    fun getConnInfoText() : String {
        return netService.getTextConnInfo()
    }

    fun getOnlineStatusText() : String {
        return if (netService.getOnlineStatus()) "online"
               else "offline"
    }

    suspend fun getNetworkText(address: String) : String {
        return netService.getTextData(address)
    }


    // NetworkService 의 이미지 다운로드 함수 호출
    // 함수명: getNetworkImage
    // 매개변수: address: String
    // 반환값: Bitmap



    // NetworkService 의 post 요청 함수 호출
    // 함수명: getNetworkPost
    // 매개변수: address: String
    // 반환값: Bitmap

}