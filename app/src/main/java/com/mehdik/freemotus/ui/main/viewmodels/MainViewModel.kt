package com.mehdik.freemotus.ui.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehdik.freemotus.R
import com.mehdik.freemotus.repositories.MainRepository
import com.mehdik.freemotus.ui.main.models.LetterData
import com.mehdik.freemotus.ui.main.models.WordData

const val DEFAULT_LETTER: Char = '.'

class MainViewModel(
    private val mainRepository: MainRepository = MainRepository
) : ViewModel() {

    var wordsItems: MutableList<WordData> = mutableListOf()
    private var answer: String = "MACHIN" // TODO to get from repository/assets/json
    private var answerTried: Int = 0
    private val isGameOver: MutableLiveData<Boolean> = MutableLiveData(false)
    val isGameOverLive: LiveData<Boolean> = isGameOver

    fun setAnswer(givenAnswer: String) {
        // Checking if the answer is a valid word
        var leftLetters = answer
        val lettersList: List<LetterData> = answer.indices.map {
            val rightLetter = answer[it]
            val givenLetter = givenAnswer.getOrNull(it)

            val res = (rightLetter == givenLetter)
            if (res) {
                // We remove the found letter from what we still have to find
                leftLetters
                    .indexOfFirst { it == rightLetter }
                    .takeIf { it > -1 }
                    ?.let { leftLetters = leftLetters.removeRange(it, it + 1) }

                // The letter is right
                LetterData(
                    letterToDisplay = rightLetter,
                    backgroundRes = R.drawable.bg_letter_right
                )
            } else {
                if (leftLetters.contains(givenLetter ?: DEFAULT_LETTER)) {
                    // We remove the found letter from what we still have to find
                    leftLetters
                        .indexOfFirst { it == givenLetter }
                        .takeIf { it > -1 }
                        ?.let { leftLetters = leftLetters.removeRange(it, it + 1) }

                    // The letter is misplaced
                    LetterData(
                        letterToDisplay = givenLetter ?: DEFAULT_LETTER,
                        backgroundRes = R.drawable.bg_letter_misplaced
                    )
                } else {
                    // The letter is wrong
                    LetterData(
                        letterToDisplay = givenLetter ?: DEFAULT_LETTER,
                        backgroundRes = null
                    )
                }
            }
        }

        wordsItems.add(
            WordData(lettersList = lettersList)
        )
        answerTried++

        // The game is over if we found the word or if we tried 7 times
        isGameOver.postValue((answerTried >= 7) || answer == givenAnswer)
    }
}
