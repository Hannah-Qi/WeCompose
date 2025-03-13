package com.example.wecompose.learnCoroution

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class LaunchCoroutionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
//            notUseFlow()
//            useFlow()
//            warmFlow(this)
//            stateFlow(this)
        }
    }

    suspend fun stateFlow(it: CoroutineScope) {
        val viewModel = ViewModel()
        it.apply {
            launch {
                viewModel.age.collect{
                    println("test stateFlow接收: $it")
                }
            }

            launch {
                while (true) {
                    viewModel.updateAge()
                }
            }
        }
    }

    suspend fun coldFlow() {
        (1 .. 10).asFlow()
        listOf(1,2,3).asFlow()
        flowOf(1,2,3,4)
    }



    suspend fun warmFlow(it: CoroutineScope) {

        val sharedFlow = MutableSharedFlow<Int>(
            replay = 3,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_LATEST
        )

        sharedFlow.onEach {
            delay(1000)
            println("test 接收次数: $it")
        }.launchIn(scope = it)

        delay(1000)
        repeat(times = 20) {
            println("test 发送次数: ${it + 1}")
            sharedFlow.emit(it)
        }



        println("test warmFlow res: 结束")
    }

    suspend fun useFlow() {
        val flow = flow {
            repeat(times = 10) {
                emit(it)
            }
        }

        flow.map { it * it }
            .filter { it % 2 == 0 }
            .take(2)
            .onEach { println("test FLow res: $it") }
            .collect()

        flow.onEach { println("test FLow res1: $it") }.collect()
    }

    suspend fun notUseFlow() {
        var count = 0
        repeat(times = 10) {
            val res = request().let { it * it }
            if(count == 2) {
                return
            }

            if(res % 2 != 0) return@repeat

            ++count
            println("test FLow res: $res")
        }
    }

    suspend fun request(): Int {
        delay(Random.nextLong(1000))
        return Random.nextInt(100)
    }
}

class ViewModel {
    private val _age = MutableStateFlow(0)
    val age = _age.asStateFlow()

    suspend fun updateAge() {
        _age.emit(request())
    }

    suspend fun request(): Int {
        delay(Random.nextLong(1000))
        return Random.nextInt(20,60)
    }
}

