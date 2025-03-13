package com.example.wecompose.learnFragment

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.example.wecompose.R
import com.example.wecompose.base.BaseFragment
import com.example.wecompose.data.ACTION_UPLOAD_RESULT
import com.example.wecompose.data.KEY_RESULT
import com.example.wecompose.data.Student
import com.example.wecompose.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment :  BaseFragment(SecondFragment::class.java.simpleName) {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("test SecondFragment onCreateView")
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("test SecondFragment onViewCreated")
        val args = arguments
        val data = args?.getParcelable<Student>("data")

        binding.apply {
            buttonSecond.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }

            tvFragment.text = data?.name

            buttonSendBroadcast.setOnClickListener {
                val intent = Intent(ACTION_UPLOAD_RESULT)
                intent.putExtra(KEY_RESULT, "upload_success")
                activity?.let { it1 ->
                    println("test buttonSendBroadcast")
                    LocalBroadcastManager.getInstance(it1).sendBroadcast(intent)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("test SecondFragment onDestroyView")
        _binding = null
    }
}