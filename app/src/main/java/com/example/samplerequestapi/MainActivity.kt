package com.example.samplerequestapi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.samplerequestapi.ui.theme.SampleRequestApiTheme
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val apiService = createApiService()

    private val viewModel: ApiViewModel by viewModels { ApiViewModelFactory(apiService) }


    private fun createApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            println(
                "Rota Retrofit: $message"
            )
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleRequestApiTheme {
                ApiScreen(viewModel)
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ApiScreen(viewModel: ApiViewModel) {
    val apiResponse = viewModel.apiResponse.collectAsState()
    LaunchedEffect(viewModel.apiResponse.value){

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedButton(onClick = { viewModel.requestApi("88352-070")}) {
            Text(text = "Consultar")
        }

        Text(text = apiResponse.value.orEmpty())
    }

}
