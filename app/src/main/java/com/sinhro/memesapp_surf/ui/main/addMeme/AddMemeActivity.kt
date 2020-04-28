package com.sinhro.memesapp_surf.ui.main.addMeme

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.database.MemeViewModel
import com.sinhro.memesapp_surf.model.memes.MemeInfo
import java.io.FileNotFoundException


class AddMemeActivity : AppCompatActivity() {

    private lateinit var addMemeToolbar: Toolbar
    private lateinit var addMemeTitleTv: TextView
    private lateinit var addMemeDescriptionTv: TextView
    private lateinit var addMemeAttachIv: ImageView
    private lateinit var addMemeImageIv: ImageView
    private lateinit var memeViewModel: MemeViewModel
    private var photoUrl: String? = null
    private val pickImageId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meme)
        findViews()
        setSupportActionBar(addMemeToolbar)
        memeViewModel = MemeViewModel(application)
        addMemeToolbar.setNavigationOnClickListener(onCloseClickListener())
        addMemeAttachIv.setOnClickListener{choosePhoto()}

    }

    private fun onCloseClickListener() = View.OnClickListener { finish() }
    private fun onMemeReady() {
        val meme = MemeInfo(
            1,
            addMemeTitleTv.text.toString(),
            addMemeDescriptionTv.text.toString(),
            false,
            System.currentTimeMillis() * 1000,
            photoUrl ?: ""
        )
        memeViewModel.insert(meme)
        finish()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.add_meme_toolbar, it)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_meme_ready)
            onMemeReady()
        return true
    }

    private fun choosePhoto() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK);
        photoPickerIntent.type = "image/*";
        startActivityForResult(photoPickerIntent, pickImageId);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickImageId && resultCode == RESULT_OK) {
            try {
                val imageUri = data?.data
                if (imageUri != null) {
                    photoUrl = imageUri.toString()
                    Glide.with(applicationContext)
                        .load(photoUrl)
                        .into(addMemeImageIv)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

    }



    private fun findViews() {
        addMemeAttachIv = findViewById(R.id.add_meme_attach_iv)
        addMemeDescriptionTv = findViewById(R.id.add_meme_description_tv)
        addMemeTitleTv = findViewById(R.id.add_meme_title_tv)
        addMemeToolbar = findViewById(R.id.add_meme_toolbar)
        addMemeImageIv = findViewById(R.id.add_meme_image_iv)
    }


}