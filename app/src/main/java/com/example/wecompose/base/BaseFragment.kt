package com.example.wecompose.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class BaseFragment(private val fragment: String): Fragment(){
    override fun onAttach(context: Context) {
        super.onAttach(context)
        println("test $fragment onAttach-base")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("test $fragment onCreate-base")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("test $fragment onCreateView-base")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("test $fragment onViewCreated-base")
    }

    override fun onStart() {
        super.onStart()
        println("test $fragment onStart-base")
    }

    override fun onResume() {
        super.onResume()
        println("test $fragment onResume-base")
    }

    override fun onPause() {
        super.onPause()
        println("test $fragment onPause-base")
    }

    override fun onStop() {
        super.onStop()
        println("test $fragment onStop-base")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("test $fragment onDestroyView-base")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("test $fragment onDestroy-base")
    }

    override fun onDetach() {
        super.onDetach()
        println("test $fragment onDetach-base")
    }
}