package com.mehdik.freemotus.ui.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehdik.freemotus.R
import com.mehdik.freemotus.databinding.ActivityMainBinding
import com.mehdik.freemotus.ui.main.adapters.WordAdapter
import com.mehdik.freemotus.ui.main.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val binding = ActivityMainBinding.inflate(layoutInflater)
        mainBinding = binding
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
        initListeners()
    }

    private fun initViews() {
        mainBinding.apply {
            // Setting the recyclerView
            gameRecycler.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                wordAdapter = WordAdapter(viewModel.wordsItems)
                adapter = wordAdapter
            }
        }
    }

    private fun initListeners() {
        mainBinding.apply {

            // Checks if the game is over
            viewModel.isGameOverLive.observe(this@MainActivity) { isGameOver ->
                if (isGameOver) {
                    answerEdit.isEnabled = false
                    gameOverImage.visibility = View.VISIBLE
                }
            }

            // Checks the given answer on Enter key and clear the EditText
            answerEdit.setOnEditorActionListener { textView, i, _ ->
                if (i == EditorInfo.IME_ACTION_DONE) {
                    viewModel.setAnswer(textView.text.toString().trim().uppercase())
                    wordAdapter.notifyItemInserted(viewModel.wordsItems.size - 1)
                    answerEdit.text?.clear()
                }
                false
            }
        }
    }
}