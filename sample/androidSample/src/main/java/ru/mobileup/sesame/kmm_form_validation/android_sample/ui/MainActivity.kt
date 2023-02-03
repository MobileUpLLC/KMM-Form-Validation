package ru.mobileup.sesame.kmm_form_validation.android_sample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import ru.mobileup.sesame.kmm_form_validation.android_sample.ui.theme.SesameKMMFormTheme
import ru.mobileup.sesame.kmm_form_validation.sharedsample.Application

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SesameKMMFormTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FormUi(component = Application.createFormComponent(defaultComponentContext()))
                }
            }
        }
    }
}