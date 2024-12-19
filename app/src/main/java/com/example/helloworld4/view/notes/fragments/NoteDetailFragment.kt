package com.example.helloworld4.view.notes.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
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
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.helloworld4.Constants
import com.example.helloworld4.data.model.Note
import com.example.helloworld4.databinding.FragmentNoteDetailBinding
import com.example.helloworld4.view_model.ImageViewModel
import com.example.helloworld4.view_model.NoteViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private var getImage: ActivityResultLauncher<Intent>? = null
    private var imageContainer: AppCompatImageView? = null
    private var currentPhotoUri: Uri? = null
    private var title: AppCompatEditText? = null
    private var text: AppCompatEditText? = null
    private val noteViewModel: NoteViewModel by viewModel()
    private val imageViewModel: ImageViewModel by viewModel()

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

        imageContainer = binding.ivContainer
        title = binding.etTitle
        text = binding.etText

        binding.btnShare.setOnClickListener {
            shareNote()
        }

        registerOpenOrCreateImage()

        binding.btnImage.setOnClickListener {
            openChooser()
        }

        binding.btnSave.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val note = createNoteFromInput()

        if (note != null) {
            lifecycleScope.launch {
                noteViewModel.insertNote(note)
                requireActivity().supportFragmentManager.popBackStack()
            }
        } else {
            showMessage("All fields are empty")
        }
    }

    private fun shareNote() {
        val note = createNoteFromInput()

        if (note != null) {
            shareNoteExternally(title?.text.toString(), text?.text.toString())
        } else {
            showMessage("All fields are empty")
        }
    }

    private fun createNoteFromInput(): Note? {
        val title = title?.text.toString()
        val text = text?.text.toString()
        val date = getCurrentDate()
        val imageUri = currentPhotoUri?.toString()
        val userId = getCurrentUserId()
        return if (title.isNotEmpty() || text.isNotEmpty() || imageUri != null) {
            Note(userId = userId, title = title, text = text, date = date, imageUri = imageUri)
        } else {
            null
        }
    }

    private fun getCurrentUserId(): Long {
        val sharedPreferences = requireContext().getSharedPreferences(
            Constants.USER_PREFS,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getLong(Constants.CURRENT_USER_ID, -1)
    }

    private fun registerOpenOrCreateImage() {
        getImage =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    if (data?.data != null) {
                        currentPhotoUri = data.data
                        try {
                            currentPhotoUri?.let { uri ->
                                val localUri =
                                    imageViewModel.copyImageToLocalStorage(requireContext(), uri)
                                if (localUri != null) {
                                    currentPhotoUri = localUri
                                    imageContainer?.setImageURI(localUri)
                                } else {
                                    showMessage("Error copying image to local storage")
                                }
                            }
                        } catch (_: Exception) {
                            showMessage("Error processing image URI")
                        }
                    } else if (currentPhotoUri != null) {
                        val bitmap = currentPhotoUri?.let { uri ->
                            val source =
                                ImageDecoder.createSource(requireActivity().contentResolver, uri)
                            ImageDecoder.decodeBitmap(source)
                        }
                        imageContainer?.setImageBitmap(bitmap)
                    }
                } else {
                    showMessage("Failed to get image")
                }
            }
    }

    private fun createPhotoFile(): File {
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
            createPhotoFile()
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
        getImage?.launch(chooserIntent)
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