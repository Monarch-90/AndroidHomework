package com.example.helloworld4.notes.view

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.helloworld4.databinding.FragmentNoteDetailBinding
import com.example.helloworld4.notes.data.Note
import com.example.helloworld4.notes.intent.NoteIntent
import com.example.helloworld4.notes.view_model.NoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var getImage: ActivityResultLauncher<Intent>
    private lateinit var imageContainer: AppCompatImageView
    private var currentPhotoUri: Uri? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var title: AppCompatEditText
    private lateinit var text: AppCompatEditText
    private val noteViewModel: NoteViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNoteDetailBinding.bind(view)

        imageContainer = binding.ivContainer
        title = binding.etTitle
        text = binding.etText

        binding.btnShare.setOnClickListener {
            shareNote()
        }

        registerOpenOrCreateImage()

        binding.btnImage.setOnClickListener {
            handleImageSelection()
        }

        binding.btnSave.setOnClickListener {
            performTaskWithDelay()
        }
    }

    private fun showProgressBar() {
        binding.prBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.prBar.visibility = View.GONE
    }

    private fun performTaskWithDelay() {
        showProgressBar()
        lifecycleScope.launch(Dispatchers.Main) {
            updateProgressBar()
            saveNote()
            hideProgressBar()
        }
    }

    private suspend fun updateProgressBar() {
        val totalTime = 3000L
        val intervalTime = 100L
        val totalSteps = totalTime / intervalTime

        for (step in 1..totalSteps) {
            delay(intervalTime)
            val progress = (step.toFloat() / totalSteps * 100).toInt()
            binding.prBar.progress = progress
        }
    }

    private fun saveNote() {
        val note = createNoteFromInput()

        if (note != null) {
            noteViewModel.processIntent(NoteIntent.AddNote(note))
            requireActivity().supportFragmentManager.popBackStack()
        } else {
            showMessage("All fields are empty")
        }
    }

    private fun shareNote() {
        val note = createNoteFromInput()

        if (note != null) {
            noteViewModel.processIntent(NoteIntent.ShareNote(note))
            shareNoteExternally(title.text.toString(), text.text.toString())
        } else {
            showMessage("All fields are empty")
        }
    }

    private fun createNoteFromInput(): Note? {
        val title = title.text.toString()
        val text = text.text.toString()
        val date = getCurrentDate()
        val imageUri = currentPhotoUri?.toString()

        return if (title.isNotEmpty() || text.isNotEmpty() || imageUri != null) {
            Note(title, text, date, imageUri)
        } else {
            null
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
                            val source =
                                ImageDecoder.createSource(requireActivity().contentResolver, uri)
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
            if (permissions[Manifest.permission.CAMERA] == true &&
                permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true &&
                permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true
            ) {
                openChooser()
            } else {
                showMessage("Permissions are required")
            }
        }
    }

    private fun checkPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    private fun createImageFile(): File {
        val timeStamp: String =
            SimpleDateFormat("ddMMyyyy_HHmm", Locale.getDefault()).format(Date())
        val storageDir: File =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
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
                FileProvider.getUriForFile(requireContext(), "com.example.helloworld4.provider", it)
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri)
        }
        val chooserIntent = Intent.createChooser(pickPhotoIntent, "Select source")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePhotoIntent))
        getImage.launch(chooserIntent)
    }

    private fun shareNoteExternally(title: String?, text: String?) {
        val date = getCurrentDate()
        val fullNote = "$title\n$text\n$date"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, fullNote)
            type = "text/plain"
        }
        if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(Intent.createChooser(shareIntent, "Share note via:"))
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}