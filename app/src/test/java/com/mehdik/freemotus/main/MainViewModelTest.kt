package com.mehdik.freemotus.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mehdik.freemotus.R
import com.mehdik.freemotus.repositories.MainRepository
import com.mehdik.freemotus.ui.main.models.LetterData
import com.mehdik.freemotus.ui.main.models.WordData
import com.mehdik.freemotus.ui.main.viewmodels.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: MainRepository

    @Before
    fun setup() {
        // Mocking instances
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun is_game_over_after_right_answer() {
        // When
        viewModel.setAnswer("MACHIN")

        // Then
        assertTrue(viewModel.isGameOverLive.value ?: false)
    }

    @Test
    fun is_game_over_after_wrong_answer() {
        // When
        viewModel.setAnswer("MIROIR")

        // Then
        assertFalse(viewModel.isGameOverLive.value ?: false)
    }

    @Test
    fun is_game_over_default_value() {
        // Then
        assertFalse(viewModel.isGameOverLive.value ?: false)
    }

    @Test
    fun setAnswer_wrong_answer_on_seventh_try_is_game_over() {
        // When
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MIROIR")

        // Then
        assertTrue(viewModel.isGameOverLive.value ?: false)
    }

    @Test
    fun setAnswer_right_answer_on_seventh_try_is_game_over() {
        // When
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MIROIR")
        viewModel.setAnswer("MACHIN")

        // Then
        assertTrue(viewModel.isGameOverLive.value ?: false)
    }

    @Test
    fun setAnswer_right_answer_data() {
        // When
        viewModel.setAnswer("MACHIN")

        // Then
        assertEquals(
            listOf(
                WordData(
                    listOf(
                        LetterData(
                            letterToDisplay = 'M',
                            backgroundRes = R.drawable.bg_letter_right
                        ),
                        LetterData(
                            letterToDisplay = 'A',
                            backgroundRes = R.drawable.bg_letter_right
                        ),
                        LetterData(
                            letterToDisplay = 'C',
                            backgroundRes = R.drawable.bg_letter_right
                        ),
                        LetterData(
                            letterToDisplay = 'H',
                            backgroundRes = R.drawable.bg_letter_right
                        ),
                        LetterData(
                            letterToDisplay = 'I',
                            backgroundRes = R.drawable.bg_letter_right
                        ),
                        LetterData(
                            letterToDisplay = 'N',
                            backgroundRes = R.drawable.bg_letter_right
                        )
                    )
                )
            ),
            viewModel.wordsItems
        )
    }

    @Test
    fun setAnswer_misplaced_letters_data() {
        // When
        viewModel.setAnswer("MANHIC")

        // Then
        assertEquals(
            listOf(
                WordData(
                    listOf(
                        LetterData(
                            letterToDisplay = 'M',
                            backgroundRes = R.drawable.bg_letter_right
                        ),
                        LetterData(
                            letterToDisplay = 'A',
                            backgroundRes = R.drawable.bg_letter_right
                        ),
                        LetterData(
                            letterToDisplay = 'N',
                            backgroundRes = R.drawable.bg_letter_misplaced
                        ),
                        LetterData(
                            letterToDisplay = 'H',
                            backgroundRes = R.drawable.bg_letter_right
                        ),
                        LetterData(
                            letterToDisplay = 'I',
                            backgroundRes = R.drawable.bg_letter_right
                        ),
                        LetterData(
                            letterToDisplay = 'C',
                            backgroundRes = R.drawable.bg_letter_misplaced
                        )
                    )
                )
            ),
            viewModel.wordsItems
        )
    }

    @Test
    fun setAnswer_wrong_answer_data() {
        // When
        viewModel.setAnswer("WXPVBZ")

        // Then
        assertEquals(
            listOf(
                WordData(
                    listOf(
                        LetterData(
                            letterToDisplay = 'W',
                            backgroundRes = null
                        ),
                        LetterData(
                            letterToDisplay = 'X',
                            backgroundRes = null
                        ),
                        LetterData(
                            letterToDisplay = 'P',
                            backgroundRes = null
                        ),
                        LetterData(
                            letterToDisplay = 'V',
                            backgroundRes = null
                        ),
                        LetterData(
                            letterToDisplay = 'B',
                            backgroundRes = null
                        ),
                        LetterData(
                            letterToDisplay = 'Z',
                            backgroundRes = null
                        )
                    )
                )
            ),
            viewModel.wordsItems
        )
    }

    @Test
    fun setAnswer_empty_answer_data() {
        // When
        viewModel.setAnswer("")

        // Then
        assertEquals(
            listOf(
                WordData(
                    listOf(
                        LetterData(
                            letterToDisplay = '.',
                            backgroundRes = null
                        ),
                        LetterData(
                            letterToDisplay = '.',
                            backgroundRes = null
                        ),
                        LetterData(
                            letterToDisplay = '.',
                            backgroundRes = null
                        ),
                        LetterData(
                            letterToDisplay = '.',
                            backgroundRes = null
                        ),
                        LetterData(
                            letterToDisplay = '.',
                            backgroundRes = null
                        ),
                        LetterData(
                            letterToDisplay = '.',
                            backgroundRes = null
                        )
                    )
                )
            ),
            viewModel.wordsItems
        )
    }
}