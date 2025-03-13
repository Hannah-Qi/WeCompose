package com.example.wecompose.learnFragment

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.wecompose.R
import com.example.wecompose.base.BaseActivity
import com.example.wecompose.broadcast.MyBroadcastReceiver
import com.example.wecompose.data.ACTION_UPLOAD_RESULT
import com.example.wecompose.databinding.ActivityLearnFragmentBinding
import com.google.android.material.snackbar.Snackbar


class LearnFragmentActivity : BaseActivity(LearnFragmentActivity::class.java.simpleName) {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLearnFragmentBinding
    private val PERMISSION_REQUEST_CODE = 1

    private lateinit var broadcastReceiver: BroadcastReceiver

    // 注册权限请求回调
    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, this.packageName)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("test LearnFragmentActivity onCreate")

        binding = ActivityLearnFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_learn_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.FirstFragment -> {
                }
                R.id.SecondFragment -> {

                }
            }

        }

        binding.apply {
            fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .setAnchorView(R.id.fab).show()
            }
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        }

        checkAndRequestNotificationPermission()

        registerReceiver()
    }

    private fun registerReceiver() {
        broadcastReceiver = MyBroadcastReceiver()
        //注册广播
        println("test LearnFragmentActivity 注册广播")
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_UPLOAD_RESULT)
        registerReceiver(broadcastReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
    }

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 请求通知权限
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_learn_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }
}