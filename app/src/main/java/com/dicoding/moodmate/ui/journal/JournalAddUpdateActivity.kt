package com.dicoding.moodmate.ui.journal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.moodmate.MainViewModel
import com.dicoding.moodmate.R
import com.dicoding.moodmate.data.pref.UserModel
import com.dicoding.moodmate.data.response.JournalData
import com.dicoding.moodmate.data.response.SingleJournalData
import com.dicoding.moodmate.data.response.SingleJournalResponse
import com.dicoding.moodmate.data.retrofit.remotejournal.JournalConfig
import com.dicoding.moodmate.databinding.ActivityJournalAddUpdateBinding
import com.dicoding.moodmate.ui.ViewModelFactory
import com.dicoding.moodmate.ui.chat.ChatFragment
import com.dicoding.moodmate.ui.recommendation.RecommendationActivity
import com.dicoding.moodmate.ui.recommendation.music.MusicActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JournalAddUpdateActivity : AppCompatActivity(), View.OnClickListener {
    private val viewModel: JournalViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userModel: UserModel

    private var isEdit = false
    private var journal: SingleJournalData? = null
    private var position: Int = 0

    private lateinit var binding: ActivityJournalAddUpdateBinding

    companion object {
        const val EXTRA_JOURNAL = "extra_journal"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        journal = intent.getParcelableExtra(EXTRA_JOURNAL)
        if (journal != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            journal = SingleJournalData("", "", "", "", "", "", "", 0)
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"

            journal?.let {
                binding.edtTitle.setText(it.title)
                binding.edtDescription.setText(it.text)
            }

        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.text = btnTitle

        binding.btnSubmit.setOnClickListener(this)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showAlertDialog(ALERT_DIALOG_CLOSE)
            }
        })

        viewModel.user.observe(this){
            userModel = it
            binding.btnRecommendation.setOnClickListener(){
                val intent = Intent(this, RecommendationActivity::class.java)
                intent.putExtra("TYPE", journal?.mood ?:"")
                startActivity(intent)
            }
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit) {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()

            if (title.isEmpty()) {
                binding.edtTitle.error = "Judul tidak boleh kosong"
                return
            }

            journal?.title = title
            journal?.text = description

            if (isEdit) {
                updateJournal()
            } else {
                journal?.createdAt = getCurrentDate()
                createJournal()
            }

            // Menampilkan elemen yang tersembunyi setelah tombol submit diklik
            binding.imageView.visibility = View.VISIBLE
            binding.mood.visibility = View.VISIBLE
            binding.btnRecommendation.visibility = View.VISIBLE
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    private fun updateJournal() {
        journal?.let {

            val journalService = JournalConfig.getJournalService()

            journalService.updateJournal(it.id, it, userModel.token)
                .enqueue(object : Callback<SingleJournalResponse> {
                    override fun onResponse(
                        call: Call<SingleJournalResponse>,
                        response: Response<SingleJournalResponse>
                    ) {
                        if (response.isSuccessful) {
                            // Jurnal berhasil diperbarui, tampilkan pesan sukses
                            Toast.makeText(
                                this@JournalAddUpdateActivity,
                                "Jurnal berhasil diperbarui",
                                Toast.LENGTH_SHORT
                            ).show()

                            journal = SingleJournalData(
                                response.body()?.data?.createdAt.toString(),
                                response.body()?.data?.updatedAt,
                                response.body()?.data?.title.toString(),
                                response.body()?.data?.text.toString(),
                                response.body()?.data?.mood.toString(),
                                response.body()?.data?.author.toString(),
                                response.body()?.data?.id.toString(),
                                response.body()?.data?.v!!
                            )

                            // Mengatur teks mood sesuai dengan hasil prediksi
                            journal?.let { it ->
                                Log.d("Mood Level", "onResponse: ${it.mood}")
                                val moodText = "Kamu terlihat sedang ${it.mood}"

                                binding.mood.text = moodText
                            }
                        } else {
                            Toast.makeText(
                                this@JournalAddUpdateActivity,
                                "Gagal mengupdate Jurnal",
                                Toast.LENGTH_SHORT
                            ).show()

                            Log.d("Journal Update", "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<SingleJournalResponse>, t: Throwable) {
                        Toast.makeText(
                            this@JournalAddUpdateActivity,
                            "Gagal mengupdate Jurnal",
                            Toast.LENGTH_SHORT
                        ).show()

                        t.printStackTrace()
                    }
                })
        }
    }

    private fun createJournal() {
        journal?.let {
            val journalService = JournalConfig.getJournalService()

            journalService.createJournal(it, userModel.token)
                .enqueue(object : Callback<SingleJournalResponse> {
                    override fun onResponse(
                        call: Call<SingleJournalResponse>,
                        response: Response<SingleJournalResponse>
                    ) {
                        if (response.isSuccessful) {
                            // Jurnal berhasil ditambahkan, tampilkan pesan sukses
                            Toast.makeText(
                                this@JournalAddUpdateActivity,
                                "Jurnal berhasil ditambahkan",
                                Toast.LENGTH_SHORT
                            ).show()

                            journal = SingleJournalData(
                                response.body()?.data?.createdAt.toString(),
                                response.body()?.data?.updatedAt,
                                response.body()?.data?.title.toString(),
                                response.body()?.data?.text.toString(),
                                response.body()?.data?.mood.toString(),
                                response.body()?.data?.author.toString(),
                                response.body()?.data?.id.toString(),
                                response.body()?.data?.v!!
                            )

                            Log.d("Journal Add", "onResponse: $journal")

                            // Mengatur teks mood sesuai dengan hasil prediksi
                            journal?.let { it ->
                                Log.d("Mood Level", "onResponse: ${it.mood}")
                                val moodText = "Kamu terlihat sedang ${it.mood}"

                                binding.mood.text = moodText
                            }
                        } else {
                            Toast.makeText(
                                this@JournalAddUpdateActivity,
                                "Gagal menambah Jurnal",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("Journal Add", "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<SingleJournalResponse>, t: Throwable) {
                        Toast.makeText(
                            this@JournalAddUpdateActivity,
                            "Gagal menambah Jurnal",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("Journal Add", "onFailure: ${t.message}")
                    }
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_form, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    /*
    Konfirmasi dialog sebelum proses batal atau hapus
    close = 10
    delete = 20
    */
    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada Jurnal?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus Jurnal ini?"
            dialogTitle = "Hapus Jurnal"
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton("Ya") { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    deleteJournal()
                }
            }
            setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun deleteJournal() {
        journal?.let {
            val journalService = JournalConfig.getJournalService()

            journalService.deleteJournal(it.id, userModel.token)
                .enqueue(object : Callback<SingleJournalResponse> {
                    override fun onResponse(
                        call: Call<SingleJournalResponse>,
                        response: Response<SingleJournalResponse>
                    ) {
                        if (response.isSuccessful) {
                            setResult(RESULT_DELETE, Intent().apply {
                                putExtra(EXTRA_POSITION, position)
                            })
                            Toast.makeText(
                                this@JournalAddUpdateActivity,
                                "Jurnal berhasil dihapus",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@JournalAddUpdateActivity,
                                "Gagal menghapus Jurnal",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("Journal Delete", "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<SingleJournalResponse>, t: Throwable) {
                        Toast.makeText(
                            this@JournalAddUpdateActivity,
                            "Gagal menghapus Jurnal",
                            Toast.LENGTH_SHORT
                        ).show()
                        t.printStackTrace()
                    }
                })
        }
    }
}
