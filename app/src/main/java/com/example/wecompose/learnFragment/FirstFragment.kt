package com.example.wecompose.learnFragment

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wecompose.R
import com.example.wecompose.base.BaseFragment
import com.example.wecompose.data.Student
import com.example.wecompose.databinding.FragmentFirstBinding
import com.example.wecompose.service.MyBindService
import com.example.wecompose.service.MyBinder
import com.example.wecompose.service.MyService

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment(FirstFragment::class.java.simpleName) {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mBinder: MyBinder

    private var conn = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBinder = service as MyBinder
            println("test onServiceConnected")
            mBinder.test()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            println("test onServiceDisconnected")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        println("test FirstFragment onCreateView")
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("test FirstFragment onViewCreated")

        binding.apply {
            buttonFirst.setOnClickListener {
                val data = Student(name = "Alice")
                val bundle = Bundle()
                bundle.putParcelable("data",data)

                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }

            buBindService.setOnClickListener {
                println("test 开启一个非绑定service")
                val intent = Intent(requireContext(), MyService::class.java)
                intent.putExtra("key_stop","stop")
                requireContext().startService(intent)
            }

            buStopService.setOnClickListener {
                println("test 停止一个非绑定service")
                val intent = Intent(requireContext(), MyService::class.java)
                requireContext().stopService(intent)
            }

            buBindService2.setOnClickListener {
                println("test 开启一个绑定service")
                val intent = Intent(requireContext(), MyBindService::class.java)
                requireContext().bindService(intent, conn, BIND_AUTO_CREATE)
            }

            buStopService2.setOnClickListener {
                println("test 停止一个绑定service")

                requireContext().unbindService(conn)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("test FirstFragment onDestroyView")
        _binding = null
    }
}