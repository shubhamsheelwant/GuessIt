package com.example.guessit.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    // used for internal purpose by view model
    private var _score = MutableLiveData<Int>()

    // used for external purpose, encapsulation is used when we don't want user to modify its value outside view model.
    val score: LiveData<Int>
        get() = _score

    private var _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word
    var wordList = mutableListOf<String>()

    private var _isGameEnabled = MutableLiveData<Boolean>()
    val isGameEnabled: LiveData<Boolean>
        get() = _isGameEnabled

    private var timer: CountDownTimer

    private var _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    init {
        _isGameEnabled.value = false
        _score.value = 0
        createWordList()
        handleNextWord()

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished
            }

            override fun onFinish() {
                _currentTime.value = DONE
                navigateToScoreScreen()
            }
        }

        timer.start()
    }

    private fun createWordList() {
        wordList.add("Ship")
        wordList.add("Aeroplane")
        wordList.add("car")
        wordList.add("bike")
        wordList.add("scooter")
        wordList.add("cycle")
        wordList.add("pen")
        wordList.add("book")
        wordList.add("laptop")
        wordList.add("mobile")
        wordList.add("charger")
        wordList.shuffle()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("shubham", "viewmodel cleared")
    }

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        handleNextWord()
    }

    fun onGotItCliked() {
        _score.value = (score.value)?.plus(1)
        handleNextWord()
    }

    private fun handleNextWord() {
        if (wordList.isEmpty()) {
            navigateToScoreScreen()
            _isGameEnabled.value = false
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    fun navigateToScoreScreen() {
        _isGameEnabled.value = true
    }

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L

        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

}