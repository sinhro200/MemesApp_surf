package com.sinhro.memesapp_surf.ui.main.addMeme

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.database.MemeViewModel
import com.sinhro.memesapp_surf.model.memes.MemeInfo
import java.io.File
import kotlin.random.Random


class AddMemeActivity : AppCompatActivity() {

    private lateinit var addMemeToolbar: Toolbar
    private lateinit var addMemeTitleTied: TextInputEditText
    private lateinit var addMemeDescriptionTied: TextInputEditText
    private lateinit var addMemeAttachIv: ImageView
    private lateinit var addMemeImageIv: ImageView
    private lateinit var memeViewModel: MemeViewModel
    private lateinit var createMenuItem: MenuItem
    private var imageUrl: String? = null
    private val pickImageId = 2
    private lateinit var chooserDialog: AlertDialog
    private lateinit var imageSaver: ImageSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meme)
        findViews()

        setSupportActionBar(addMemeToolbar)
        addMemeToolbar.setNavigationOnClickListener(onCloseClickListener())

        memeViewModel = MemeViewModel(application)

        imageSaver = ImageSaver(this)
        chooserDialog = createChooserDialog()

        addMemeAttachIv.setOnClickListener { onChooseImageClick() }

        addMemeTitleTied.addTextChangedListener(object : TextWatcher {
            private var prevText: CharSequence? = null
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val len = it.length
                    if (it.length > resources.getInteger(R.integer.maxCountCharactersInMemeTitle)) {
                        it.replace(0, it.length, prevText ?: "")
                    }
                }
                toCheckReadyButton()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                prevText = s?.trim()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        addMemeDescriptionTied.addTextChangedListener(object : TextWatcher {
            private var prevText: CharSequence? = null
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val len = it.length
                    if (it.length > resources.getInteger(R.integer.maxCountCharactersInMemeDescription)) {
                        it.replace(0, it.length, prevText ?: "")
                    }
                }
                toCheckReadyButton()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                prevText = s?.trim()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

    }

    private fun onCloseClickListener() = View.OnClickListener {
        deletePhoto()
        finish()
    }

    private fun onMemeReady() {
        val meme = MemeInfo(
            Random.Default.nextLong(),
            addMemeTitleTied.text.toString(),
            addMemeDescriptionTied.text.toString(),
            false,
            System.currentTimeMillis() * 1000,
            imageUrl ?: ""
        )
        memeViewModel.insert(meme)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.add_meme_toolbar, it)
            createMenuItem = menu.getItem(0)
            setMemeReady(false)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_meme_ready)
            onMemeReady()
        return true
    }

    private fun choosePhoto() {
        val photoPickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT);
        photoPickerIntent.type = "image/*";
        startActivityForResult(photoPickerIntent, pickImageId);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageId && resultCode == RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let {
                imageUrl = it.toString()
                chooserDialog.hide()
                Glide.with(applicationContext)
                    .load(imageUrl)
                    .into(addMemeImageIv)
                toCheckReadyButton()
            }
        }

        imageSaver.onPicResult(requestCode, resultCode, data
        ) {
            imageUrl = it
            chooserDialog.hide()
            toCheckReadyButton()
            Glide.with(applicationContext)
                .load(imageUrl)
                .into(addMemeImageIv)
        }
    }

    private fun toCheckReadyButton() {
        setMemeReady(
            !addMemeTitleTied.text.toString().isBlank() &&
                    !addMemeDescriptionTied.text.toString().isBlank() &&
                    imageUrl != null
        )
    }

    private fun setMemeReady(ready: Boolean) {
        createMenuItem.isEnabled = ready
    }

    /**
     * TODO
     * Сделать оформление для диалога
     */
    private fun createChooserDialog(): AlertDialog {
        val arr = arrayOf(
            getString(R.string.fromGallery),
            getString(R.string.toMakeAPhoto)
        )
        val builder = AlertDialog.Builder(ContextThemeWrapper(this,R.style.AlertDialogCustom))

        builder.setTitle(R.string.choosePhoto)

        builder.setSingleChoiceItems(
            arr,
            -1,
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when (which) {
                        0 -> choosePhoto()
                        1 -> imageSaver.takeAPhoto()
                    }
                }
            }
        )
        return builder.create()
    }

    private fun onChooseImageClick() {
        chooserDialog.show()
    }

    private fun deletePhoto() {
        imageUrl?.let {
            val fdelete = File(imageUrl)
            if (fdelete.exists()) {
                fdelete.delete()
            }
        }

    }


    private fun findViews() {
        addMemeAttachIv = findViewById(R.id.add_meme_attach_iv)
        addMemeDescriptionTied = findViewById(R.id.add_meme_description_tied)
        addMemeTitleTied = findViewById(R.id.add_meme_title_tied)
        addMemeToolbar = findViewById(R.id.add_meme_toolbar)
        addMemeImageIv = findViewById(R.id.add_meme_image_iv)
    }


}