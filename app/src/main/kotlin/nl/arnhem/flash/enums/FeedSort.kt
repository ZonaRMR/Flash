package nl.arnhem.flash.enums

import android.support.annotation.StringRes
import nl.arnhem.flash.R
import nl.arnhem.flash.facebook.FbItem

/**
 * Created by Allan Wang on 2017-06-23.
 **/
enum class FeedSort(@StringRes val textRes: Int, val item: FbItem) {
    DEFAULT(R.string.kau_default, FbItem.FEED),
    MOST_RECENT(R.string.most_recent, FbItem.FEED_MOST_RECENT),
    TOP(R.string.top_stories, FbItem.FEED_TOP_STORIES);

    companion object {
        val values = values() //save one instance
        operator fun invoke(index: Int) = values[index]
    }
}