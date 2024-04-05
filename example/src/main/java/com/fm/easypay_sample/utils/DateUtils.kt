package com.fm.easypay_sample.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {

    private const val USER_DATE_FORMAT = "yyyy-MM-dd"

    @SuppressLint("SimpleDateFormat")
    fun parseDate(date: String): Date? {
        val format = SimpleDateFormat(USER_DATE_FORMAT)
        return format.parse(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun parseDate(date: Date): String? {
        val format = SimpleDateFormat(USER_DATE_FORMAT)
        return format.format(date)
    }
}