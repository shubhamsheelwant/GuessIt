package com.example.guessit.game

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.guessit.R
import com.example.guessit.databinding.FragmentGameBinding
import kotlin.math.absoluteValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentGame.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentGame : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentGameBinding
    lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_game, container, false)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        binding.btnSkip.setOnClickListener {
            viewModel.onSkip()
        }
        binding.btnGotIt.setOnClickListener {
            viewModel.onGotItCliked()
        }
        binding.btnEndGame.setOnClickListener {
            goToScoreScreen()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.score.observe(this, Observer { newScore ->
            binding.textScore.text = "$newScore"
        })

        viewModel.word.observe(this, Observer {
            binding.textWord.text = it
        })

        viewModel.isGameEnabled.observe(this, Observer { hasFinished ->
            if(hasFinished){
                goToScoreScreen()
            }
        })

        viewModel.currentTime.observe(this, Observer{
            binding.textTime.text = DateUtils.formatElapsedTime(it.absoluteValue)
        })
    }

    private fun goToScoreScreen() {
        view?.findNavController()?.navigate(R.id.action_fragmentGame_to_fragmentScore)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentGame.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentGame().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}