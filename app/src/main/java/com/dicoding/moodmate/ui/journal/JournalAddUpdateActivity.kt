package com.dicoding.moodmate.ui.journal

import android.content.ContentValues
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.moodmate.R
import com.dicoding.moodmate.databinding.ActivityJournalAddUpdateBinding
import com.dicoding.moodmate.ui.journal.db.DatabaseContract
import com.dicoding.moodmate.ui.journal.db.JournalHelper
import com.dicoding.moodmate.ui.journal.entitiy.Journal
import java.util.Date
import java.util.Locale

class JournalAddUpdateActivity : AppCompatActivity(), View.OnClickListener {
    private var isEdit = false
    private var journal: Journal? = null
    private var position: Int = 0
    private lateinit var journalHelper: JournalHelper

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

        journalHelper = JournalHelper.getInstance(applicationContext)
        journalHelper.open()

        journal = intent.getParcelableExtra(EXTRA_JOURNAL)
        if (journal != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            journal = Journal()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"

            journal?.let {
                binding.edtTitle.setText(it.title)
                binding.edtDescription.setText(it.description)
            }

        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.text = btnTitle

        binding.btnSubmit.setOnClickListener(this)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showAlertDialog(ALERT_DIALOG_CLOSE)
            }
        })
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit) {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()

            /*
            Jika fieldnya masih kosong maka tampilkan error
             */
            if (title.isEmpty()) {
                binding.edtTitle.error = "Field can not be blank"
                return
            }

            journal?.title = title
            journal?.description = description

            val intent = Intent()
            intent.putExtra(EXTRA_JOURNAL, journal)
            intent.putExtra(EXTRA_POSITION, position)

            // Gunakan contentvalues untuk menampung data
            val values = ContentValues()
            values.put(DatabaseContract.JournalColumns.TITLE, title)
            values.put(DatabaseContract.JournalColumns.DESCRIPTION, description)

            /*
            Jika merupakan edit maka setresultnya UPDATE, dan jika bukan maka setresultnya ADD
            */
            if (isEdit) {
                val result = journalHelper.update(journal?.id.toString(), values)
                if (result > 0) {
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@JournalAddUpdateActivity,
                        "Gagal mengupdate data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                journal?.date = getCurrentDate()
                values.put(DatabaseContract.JournalColumns.DATE, getCurrentDate())
                val result = journalHelper.insert(values)

                if (result > 0) {
                    journal?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@JournalAddUpdateActivity,
                        "Gagal menambah data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
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
    deleteJournal = 20
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
            dialogTitle = "Hapus Journal"
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    val result = journalHelper.deleteById(journal?.id.toString()).toLong()
                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        setResult(RESULT_DELETE, intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@JournalAddUpdateActivity,
                            "Gagal menghapus data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}