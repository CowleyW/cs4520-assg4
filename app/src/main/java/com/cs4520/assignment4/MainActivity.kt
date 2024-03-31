package com.cs4520.assignment4

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cs4520.assignment4.database.Database
import com.cs4520.assignment4.viewmodel.MVVMProductViewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding

    private val db by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = Database::class.java,
            name = "products-db"
        ).build()
    }
    private val viewModel by viewModels<MVVMProductViewModel>(
        factoryProducer = {
            object : ViewModelProvider.AndroidViewModelFactory() {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MVVMProductViewModel() as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<FreshDataWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "fresh_data_cs4520_worker",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            periodicWorkRequest
        )

        viewModel.withDB(db)
        setContent {
            MaterialTheme {
                Navigation(viewModel = viewModel)
            }
        }
    }
}