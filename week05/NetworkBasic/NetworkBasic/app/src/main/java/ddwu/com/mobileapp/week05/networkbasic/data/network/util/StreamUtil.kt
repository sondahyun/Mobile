package ddwu.com.mobileapp.week05.networkbasic.data.network.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

class StreamUtil {
    
    // InputStream을 String으로 가공
    fun readStreamToString(iStream : InputStream?) : String {
        val resultBuilder = StringBuilder()

        // InputStreamReader로 집어넣음
        // Stream -> text로 바꿈 : Reader
        val inputStreamReader = InputStreamReader(iStream)
        // 버퍼에 집어넣음 (한번에 여러개)
        val bufferedReader = BufferedReader(inputStreamReader)

        // 한줄에 읽어서 readLine에 넣음
        var readLine : String? = bufferedReader.readLine()
        // 문자열로 만듦
        while (readLine != null) {
            // System.LineSeparator: \n
            resultBuilder.append(readLine + System.lineSeparator())
            readLine = bufferedReader.readLine()
        }

        bufferedReader.close()
        
        // 문자열 반환
        return resultBuilder.toString()
    }


    fun readStreamToImage(iStream: InputStream?) : Bitmap {
        // 비트맵 만들어서 반환
        val bitmap = BitmapFactory.decodeStream(iStream)
        return bitmap
    }

}