package ddwu.com.mobileapp.week06.naverparsing.data.network.util

import android.util.Xml
import ddwu.com.mobileapp.week06.naverparsing.data.Book
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class NaverBookParser {
    private val ns: String? = null

    companion object {
        /*Parsing 할 태그를 상수로 지정*/

    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream?) : List<Book> {

        inputStream.use { inputStream ->
            val parser : XmlPullParser = Xml.newPullParser()

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, "UTF-8")
            while (parser.name !=  /*아이템의 상위 태그*/  ) {
                parser.next()
            }
            return readBookChannel(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readBookChannel(parser: XmlPullParser) : List<Book> {
        val movies = mutableListOf<Book>()

        parser.require(XmlPullParser.START_TAG, ns, /*아이템의 상위 태그*/ )

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == /*아이템=책 태그*/) {
                movies.add( readBookItem(parser) )
            } else {
                skip(parser)
            }
        }

        return movies
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readBookItem(parser: XmlPullParser) : Book {
        parser.require(XmlPullParser.START_TAG, ns, /*아이템=책 태그*/ )
        var title : String? = null
        var author : String? = null
        var publisher: String? = null
        var image: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                /*책의 항목 정보 저장*/



                else -> skip(parser)
            }
        }
        return /*책반환*/
    }


    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTextInTag (parser: XmlPullParser, tag: String): String {
        parser.require(XmlPullParser.START_TAG, ns, tag)
        var text = ""
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.text
            parser.nextTag()
        }
        parser.require(XmlPullParser.END_TAG, ns, tag)
        return text
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
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