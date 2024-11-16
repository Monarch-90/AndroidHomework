package com.example.helloworld4.ToDo

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.helloworld4.Constants
import com.example.helloworld4.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailActivity : AppCompatActivity() {
    private lateinit var getImage: ActivityResultLauncher<Intent>
    private lateinit var imageContainer: AppCompatImageView
    private var currentPhotoUri: Uri? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var etTitle: AppCompatEditText
    private lateinit var etText: AppCompatEditText
    private lateinit var tvDate: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        etTitle = findViewById(R.id.tv_title)
        etText = findViewById(R.id.tv_text)
        tvDate = findViewById(R.id.tv_date)
        imageContainer = findViewById(R.id.iv_container)
        val btnShare: AppCompatImageView = findViewById(R.id.iv_btn_share)
        val btnImage: AppCompatImageView = findViewById(R.id.iv_btn_image)
        val btnSave: AppCompatButton = findViewById(R.id.btn_save)

        val cbTitle = intent.getStringExtra(Constants.TITLE_KEY)
        val cbText = intent.getStringExtra(Constants.TEXT_KEY)
        val cbData = intent.getStringExtra(Constants.DATA_KEY)

        etTitle.setText(cbTitle)
        etText.setText(cbText)
        tvDate.text = cbData

        btnShare.setOnClickListener {
            shareNote(cbTitle, cbText, cbData)
        }

        registerOpenOrCreateImage()

        btnImage.setOnClickListener {
            handleImageSelection()
        }

        btnSave.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = etTitle.text.toString()
        val text = etText.text.toString()
        val date = getCurrentDate()
        val imageUri = currentPhotoUri?.toString()

        val note = Note(title, text, date, imageUri)

        if (title.isNotEmpty() || text.isNotEmpty() || imageUri != null) {
            val sharedPreferences = getSharedPreferences(Constants.NOTES_PREF, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(Constants.TITLE_KEY, note.title)
            editor.putString(Constants.TEXT_KEY, note.text)
            editor.putString(Constants.DATA_KEY, note.date)
            editor.putString(Constants.IMAGE_KEY, note.imageUri)
            editor.apply()

            val intent = Intent()
            intent.putExtra(Constants.TITLE_KEY, note.title)
            intent.putExtra(Constants.TEXT_KEY, note.text)
            intent.putExtra(Constants.DATA_KEY, note.date)
            intent.putExtra(Constants.IMAGE_KEY, note.imageUri)
            setResult(RESULT_OK, intent)

            finish()
        } else {
            showMessage("Fields are empty")
        }
    }

    private fun handleImageSelection() {
        if (checkPermissions()) {
            openChooser()
        } else {
            requestPermissions()
        }

    }

    private fun registerOpenOrCreateImage() {
        getImage =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    if (data?.data != null) {
                        currentPhotoUri = data.data
                        imageContainer.setImageURI(currentPhotoUri)
                    } else if (currentPhotoUri != null) {
                        val bitmap = currentPhotoUri?.let { uri ->
                            val source = ImageDecoder.createSource(contentResolver, uri)
                            ImageDecoder.decodeBitmap(source)
                        }
                        imageContainer.setImageBitmap(bitmap)
                    }
                } else {
                    showMessage("Failed to get image")
                }
            }
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[android.Manifest.permission.CAMERA] == true &&
                permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] == true &&
                permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] == true
            ) {
                openChooser()
            } else {
                showMessage("Permissions are required")
            }
        }
    }

    private fun checkPermissions(): Boolean {
        val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoUri = Uri.fromFile(this)
        }
    }

    private fun openChooser() {
        val pickPhotoIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            createImageFile()
        } catch (_: IOException) {
            showMessage("Error adding image")
            null
        }
        photoFile?.also {
            currentPhotoUri =
                FileProvider.getUriForFile(this, "com.example.helloworld4.provider", it)
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri)
        }
        val chooserIntent = Intent.createChooser(pickPhotoIntent, "Select source")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePhotoIntent))
        getImage.launch(chooserIntent)
    }

    private fun shareNote(title: String?, text: String?, data: String?) {
        val fullNote = "$title\n$text\n$data"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, fullNote)
            type = "text/plain"
        }
        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(shareIntent, "Share note via:"))
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }
}