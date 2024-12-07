package ddwu.com.mobileapp.week06.moviexmlparsing.data.network.util

import android.util.Xml
import ddwu.com.mobileapp.week06.naverxmlparsing.data.Movie
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class MovieParser {
    private val ns: String? = null

    companion object { // 목록에 해당되는 태그들을 정적 변수로 선언
        /*Parsing 에 사용할 TAG 정적상수 선언*/
//        val UPPER_TAG =
//        val ITEM_TAG =
//        ...
        // var FAULT_RESULT = "faultResult"
        val UPPER_TAG = "dailyBoxOfficeList"
        val ITEM_TAG = "dailyBoxOffice"
        val RANK_TAG = "rank"
        val TITLE_TAG = "movieNm"
        val OPEN_DATE_TAG = "openDt"

    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream?) : List<Movie> {
        // InputStream을 Text로 변환하지 않고 바로 parse에 삽입

        // use: kotlin의 범위 함수 (inputStream 사용 후 자동 close)
        inputStream.use { inputStream ->
            // XmlPullParser 객체 생성
            val parser : XmlPullParser = Xml.newPullParser()

            /*Parser 의 동작 정의, next() 호출 전 반드시 호출 필요*/
            // setFeature 사용
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)

            /* Paring 대상이 되는 inputStream 설정 */
            // inputStream 삽입
            parser.setInput(inputStream, null)

            /*Parsing 대상 태그의 상위 태그까지 이동*/
            while (parser.name != UPPER_TAG) {
                parser.next()
            }

            return readBoxOffice(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readBoxOffice(parser: XmlPullParser) : List<Movie> {
        // 값이 변할 수 있는 mutableList 생성
        val movies = mutableListOf<Movie>()

        // UPPER_TAG가 맞는지 확인
        parser.require(XmlPullParser.START_TAG, ns,  UPPER_TAG)
        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == ITEM_TAG) {
                // DTO 저장
                movies.add( readDailyBoxOffice(parser) ) // 내부 읽음 -> 목록에 추가 (DTO 한개)
            } else {
                skip(parser)
            }
        }

        // 최종 DTO 반환
        return movies
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readDailyBoxOffice(parser: XmlPullParser) : Movie {
        parser.require(XmlPullParser.START_TAG, ns, ITEM_TAG)

        /*Parsing 한 TEXT 값을 저장할 변수 선언*/
        var rank: Int? = null
        var title: String? = null
        var openData: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            // TAG 확인
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {

                /*TAG 명에 따라 변수에 TEXT 저장*/
                RANK_TAG -> rank = readTextInTag(parser, RANK_TAG).toInt() // TAG 값 읽음
                TITLE_TAG -> title = readTextInTag(parser, TITLE_TAG)
                OPEN_DATE_TAG -> openData = readTextInTag(parser, OPEN_DATE_TAG)

                else -> skip(parser)
            }
        }
        return Movie(rank, title, openData)/*저장한 변수로 Movie 생성*/
    }


    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTextInTag (parser: XmlPullParser, tag: String): String {
        // XML 문제 있는지 확인
        // 해당 태그가 맞는지 확인
        parser.require(XmlPullParser.START_TAG, ns, tag)
        var text = ""
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.text // TEXT 확인
            parser.nextTag() // 다음 태그로 넘어가기 -> END TAG로
        }
        // XML 문제 있는지 확인
        // END_TAG 맞는지 확인
        parser.require(XmlPullParser.END_TAG, ns, tag)
        return text
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) { // 필요 없는 태그 일 경우
        if (parser.eventType != XmlPullParser.START_TAG) {
            // START_TAG가 아닐 경우에 오류 발생
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}