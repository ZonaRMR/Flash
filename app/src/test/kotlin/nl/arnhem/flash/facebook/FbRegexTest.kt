package nl.arnhem.flash.facebook

import org.apache.commons.text.StringEscapeUtils
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by Allan Wang on 24/12/17.
 **/
class FbRegexTest {
    @Test
    fun userIdRegex() {
        val id = 12349876L
        val cookie = "wd=1366x615; c_user=$id; act=1234%2F12; m_pixel_ratio=1; presence=hello; x-referer=asdfasdf"
        assertEquals(id, FB_USER_MATCHER.find(cookie)[1]?.toLong())
    }

    @Test
    fun fbDtsgRegex() {
        val fb_dtsg = "readme"
        val input = "data-sigil=\"mbasic_inline_feed_composer\">\u003Cinput type=\"hidden\" name=\"fb_dtsg\" value=\"$fb_dtsg\" autocomplete=\"off\" \\/>\u003Cinput type=\"hidden\" name=\"privacyx\" value=\"12345\""
        assertEquals(fb_dtsg, FB_DTSG_MATCHER.find(input)[1])
    }

    @Test
    fun ppRegex() {
        val img = "https\\3a //scontent-yyz1-1.xx.fbcdn.net/v/asdf1234.jpg?efg\\3d 333\\26 oh\\3d 77\\26 oe\\3d 444"
        val imgUnescaped = StringEscapeUtils.unescapeCsv(img)
        val ppStyleSingleQuote = "background:#d8dce6 url('$img') no-repeat center;"
        val ppStyleDoubleQuote = "background:#d8dce6 url(\"$img\") no-repeat center;"
        val ppStyleNoQuote = "background:#d8dce6 url($img) no-repeat center;"
        listOf(ppStyleSingleQuote, ppStyleDoubleQuote, ppStyleNoQuote).forEach {
            assertEquals(imgUnescaped, StringEscapeUtils.unescapeCsv(FB_CSS_URL_MATCHER.find(it)[1]))
        }
    }

    @Test
    fun msgNotifIdRegex() {
        val id = 1273491646093428L
        val data = "threadlist_row_other_user_fbid_thread_fbid_$id"
        assertEquals(id, FB_MESSAGE_NOTIF_ID_MATCHER.find(data)[1]?.toLong(), "thread_fbid mismatch")
        val userData = "threadlist_row_other_user_fbid_${id}thread_fbid_"
        assertEquals(id, FB_MESSAGE_NOTIF_ID_MATCHER.find(userData)[1]?.toLong(), "user_fbid mismatch")
    }

    @Test
    fun jsonUrlRegex() {
        val url = "https://www.hello.world"
        val data = "\"uri\":\"$url\"}"
        assertEquals(url, FB_JSON_URL_MATCHER.find(data)[1])
    }

    @Test
    fun imageIdRegex() {
        val id = 123456L
        val img = "https://scontent-yyz1-1.xx.fbcdn.net/v/t31.0-8/fr/cp0/e15/q65/89056_${id}_98239_o.jpg"
        assertEquals(id, FB_IMAGE_ID_MATCHER.find(img)[1]?.toLongOrNull())
        }
}