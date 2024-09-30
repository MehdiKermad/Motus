package com.mehdik.freemotus.ui.main.holders

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mehdik.freemotus.databinding.ItemWordBinding
import com.mehdik.freemotus.ui.main.models.WordData

class WordViewHolder(
    private val binding: ItemWordBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val letterTextViews: List<TextView> = listOf(
        binding.firstLetter,
        binding.secondLetter,
        binding.thirdLetter,
        binding.fourthLetter,
        binding.fifthLetter,
        binding.sixthLetter
    )

    // All the logic is handled in the viewModel, we only display here
    fun bind(data: WordData) {
        binding.apply {
            data.lettersList.forEachIndexed { i, letterData ->
                // We display the letter
                letterTextViews[i].text = letterData.letterToDisplay.toString()

                // We display the background
                letterData
                    .backgroundRes
                    ?.let {
                        letterTextViews[i].setBackgroundResource(it)
                    }
                    ?: run {
                        letterTextViews[i].background = null
                    }
            }
        }
    }
}
