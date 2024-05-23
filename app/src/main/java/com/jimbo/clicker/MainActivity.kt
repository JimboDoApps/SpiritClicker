package com.jimbo.clicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jimbo.clicker.storage.IStorage
import com.jimbo.clicker.storage.room.DatabaseStorage
import com.jimbo.clicker.ui.MainScreen
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class MainActivity : ComponentActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = lifecycleScope.coroutineContext
    private val viewModel: ClickerViewModel by viewModels()
    private lateinit var storage: IStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = DatabaseStorage(applicationContext)
        viewModel.setStorage(storage)

        setContent {
            MainScreen(viewModel)
        }
    }
}