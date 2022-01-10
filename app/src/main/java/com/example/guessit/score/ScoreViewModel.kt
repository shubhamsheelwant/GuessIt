package com.example.guessit.score

import android.util.Log
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int): ViewModel() {

    init {
        Log.i("shubham", "$finalScore")
    }
}