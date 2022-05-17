@file:Suppress("unused")

package com.sliidepoc.common.utils.formater

import java.util.*

/**
 * Utility class to format a string. These methods must be general implementation. No concrete implementation.
 *
 * @author Ioan Hagau
 * @since 2020.11.25
 */
object StringUtils {

    const val EMPTY_STRING = ""

    const val TITLE_SEPARATOR = " - "

    fun capitalizeFirstLetter(original: String?): String? {
        return if (original == null || original.isEmpty()) {
            original
        } else original.substring(0, 1).uppercase(Locale.ROOT) + original.substring(1)
    }

    fun join(delimiter: CharSequence?, quote: String?, tokens: Array<Any?>): String {
        val sb = StringBuilder()
        var firstTime = true
        for (token in tokens) {
            if (firstTime) {
                firstTime = false
            } else {
                sb.append(delimiter)
            }
            if (quote != null) {
                sb.append(quote).append(token).append(quote)
            } else {
                sb.append(token)
            }
        }
        return sb.toString()
    }

    fun getRandomId(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
