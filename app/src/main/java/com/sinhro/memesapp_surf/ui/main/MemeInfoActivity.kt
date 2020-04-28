package com.sinhro.memesapp_surf.ui.main

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.model.memes.MemeInfo
import com.sinhro.memesapp_surf.storage.StorageMemeHelper
import com.sinhro.memesapp_surf.ui.DateUtils
import com.sinhro.memesapp_surf.ui.DateUtils.*
import com.sinhro.memesapp_surf.ui.ShareUtil
import java.lang.StringBuilder
import java.util.*

class MemeInfoActivity : AppCompatActivity() {
    private lateinit var title_tv: TextView
    private lateinit var description_tv: TextView
    private lateinit var date_tv: TextView
    private lateinit var fav_iv: ImageView
    private lateinit var mainPic_iv: ImageView

    private lateinit var storageMemeHelper: StorageMemeHelper
    private lateinit var meme : MemeInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme_info)
        setSupportActionBar(findViewById(R.id.meme_info_toolbar))

        findViews()
        storageMemeHelper = StorageMemeHelper(applicationContext)


        val memeStr = intent.extras?.get("meme") as String
        val meme = Gson().fromJson(memeStr, MemeInfo::class.java)
        meme?.let {
            setViews(meme)
            this.meme = meme
        }
    }

    private fun findViews() {
        title_tv = findViewById(R.id.meme_info_title_tv)
        description_tv = findViewById(R.id.meme_info_description_tv)
        date_tv = findViewById(R.id.meme_info_date_tv)
        fav_iv = findViewById(R.id.meme_info_fav_iv)
        mainPic_iv = findViewById(R.id.meme_info_pic_iv)
    }

    private fun setViews(meme: MemeInfo) {
        title_tv.text = meme.title
//        title_tv.visibility = View.GONE
        title = meme.title

        description_tv.text = parseDescription(meme.description)
        date_tv.text = parseDate2(meme.createdDate)
        Glide.with(applicationContext)
            .load(meme.photoUrl)
            .into(mainPic_iv)

        fav_iv.isActivated = storageMemeHelper.isFav(meme)

        fav_iv.setOnClickListener {
            it as ImageView
            it.isActivated = !it.isActivated
            meme.isFavorite = it.isActivated
            storageMemeHelper.saveIfFav(meme)
        }
    }

    private fun parseDescription(text: String?): String {
        val sb = StringBuilder()
        text?.split("<br/>")
            ?.forEach {
                sb.append(it)
                sb.append("\n")
            }
        return sb.toString()
    }

    private fun parseDate(date: Long): String {
        val calendar = DateUtils.getCorrectCalendarDelta(date)
        val yearAgo = calendar.get(Calendar.YEAR)
        val monthAgo = calendar.get(Calendar.MONTH)
        val daysAgo = calendar.get(Calendar.DAY_OF_MONTH)
        val hoursAgo = calendar.get(Calendar.HOUR)
        val minutesAgo = calendar.get(Calendar.MINUTE)
        val secondsAgo = calendar.get(Calendar.SECOND)
        when {
            yearAgo != 0 -> return yearAgo.toString() + " " + resources.getString(R.string.years)
            monthAgo != 0 -> return monthAgo.toString() + " " + resources.getString(R.string.months)
            daysAgo != 0 -> return daysAgo.toString() + " " + resources.getString(R.string.days)
            hoursAgo != 0 -> return hoursAgo.toString() + " " + resources.getString(R.string.hours)
            minutesAgo != 0 -> return minutesAgo.toString() + " " + resources.getString(R.string.minutes)
            secondsAgo != 0 -> return secondsAgo.toString() + " " + resources.getString(R.string.seconds)
        }
        return ""
    }

    private fun parseDate2(date: Long): String {
        /**
         * TODO
         * Попробовать через OffsetDateTime
         */
        val ta = TimeAgo(applicationContext)
        return ta.toDuration(date)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.meme_info_toolbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_share) {
            ShareUtil.share(meme)
        }
        return true
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }
}