@file:Suppress("unused")

package com.sliidepoc.common.utils.formater

import android.text.format.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Utility class to work with date time, different formatting types. Methods present here must be as general as possible.
 * No concrete implementation here.
 *
 * @author Ioan Hagau
 * @since 2020.11.24
 */
@Suppress("MemberVisibilityCanBePrivate")
object DateTimeUtils {

    /**
     * Instance calendar.
     */
    private var calendar: Calendar? = null
    private const val DATE_TIME_FORMAT = "dd.mm.yyyy HH:mm"

    init {
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    }

    fun isSameDay(time1: Long, time2: Long): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.timeInMillis = time1
        cal2.timeInMillis = time2
        return cal1[Calendar.ERA] == cal2[Calendar.ERA] && cal1[Calendar.YEAR] == cal2[Calendar.YEAR] && cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR]
    }

    /**
     * @param time1 the time that we want to check if it's the day before time2
     * @param time2 check above
     * @return true if time1 is one day before time2, false otherwise
     */
    fun isYesterday(time1: Long, time2: Long): Boolean {
        return isSameDay(time1, time2 - TimeUnit.DAYS.toMillis(1))
    }

    /**
     * @param time1 the time that we want to check if it's the day before time2
     * @param time2 check above
     * @return true if time1 is one day before time2, false otherwise
     */
    fun isTomorrow(time1: Long, time2: Long): Boolean {
        return isSameDay(time1, time2 + TimeUnit.DAYS.toMillis(1))
    }

    /**
     * Date and time parsing method for the fixed format `yyyy-MM-dd HH:mm:ss` (e.g. 2012-12-01 18:40:00Z,
     * 2013-03-15T18:00:00Z).<br></br>
     * This method was created for fast parsing date time strings in format during JSON response parsing.
     *
     * @param dateTime Date and time string in the fixed format `yyyy-MM-dd HH:mm:ss`
     * (e.g. 2012-12-01 18:40:00Z).
     * @return the parsed number of milliseconds since Jan. 1, 1970, midnight GMT.
     * @throws NumberFormatException
     */
    @Throws(NumberFormatException::class)
    fun parse(dateTime: String): Long? {
        calendar?.clear()
        calendar?.set(Calendar.YEAR, dateTime.substring(0, 4).toInt())
        calendar?.set(Calendar.MONTH, dateTime.substring(5, 7).toInt() - 1)
        calendar?.set(Calendar.DAY_OF_MONTH, dateTime.substring(8, 10).toInt())
        calendar?.set(Calendar.HOUR_OF_DAY, dateTime.substring(11, 13).toInt())
        calendar?.set(Calendar.MINUTE, dateTime.substring(14, 16).toInt())
        calendar?.set(Calendar.SECOND, dateTime.substring(17, 19).toInt())
        return calendar?.time?.time
    }

    /**
     * Return formatted airing time, E.g: 19:50 - 20:30
     *
     * @param time - the time as milliseconds
     */
    fun getFormattedTime(time: Long): String {
        return DateFormat.format(
            DATE_TIME_FORMAT,
            time
        ).toString()
    }
}
