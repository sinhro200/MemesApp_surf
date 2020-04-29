package com.sinhro.memesapp_surf.ui

import android.content.Context
import com.sinhro.memesapp_surf.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateUtils {

    companion object {
        private const val datePattern = "dd.MM.yy HH:mm"
        private val format = SimpleDateFormat(datePattern)

        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            return format.format(date)
        }

        fun currentTimeToLong(): Long {
            return System.currentTimeMillis()
        }

        fun convertDateToLong(date: String): Long {
            val df = SimpleDateFormat(datePattern)
            return df.parse(date).time
        }

        fun getCorrectCalendarDelta(time: Long): Calendar {
            val memtime = Date(time*1000)
            val calTime = Calendar.getInstance()
            calTime.time = memtime

            val now = Date(currentTimeToLong())
            val calNow = Calendar.getInstance()
            calNow.time = now

            calNow.add(Calendar.MONTH,-1)
            calNow.before(calTime)

            return calNow
        }

        }

    class TimeAgo(private val ctx: Context) {

        val times: List<Long> = listOf(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1)
        )
        private val timesString: List<String> =
            listOf(
                ctx.getString(R.string.years),
                ctx.getString(R.string.months),
                ctx.getString(R.string.days),
                ctx.getString(R.string.hours),
                ctx.getString(R.string.minutes),
                ctx.getString(R.string.seconds)
            )

        fun toDuration(duration: Long): String {
            val res = StringBuffer()
            for (i in times.indices) {
                val current = times[i]
                val temp = duration / current
                if (temp > 0) {
                    res.append(temp)
                        .append(" ")
                        .append(timesString[i])
                        .append(" ")
//                    .append(if (temp != 1L) "s" else "")
                        .append(ctx.getString(R.string.ago))
                    break
                }
            }
            return if ("" == res.toString())
                "0 " + ctx.getString(R.string.seconds) + ctx.getString(R.string.ago)
            else
                res.toString()
        }
    }
}