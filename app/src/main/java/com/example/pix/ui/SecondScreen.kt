package com.example.pix.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.pix.ui.theme.PixTheme

class SecondScreen: ComponentActivity() {
    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val acceptedPath = intent.getStringExtra("PATH")
        setContent {
            PixTheme {
                Card(modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
                    .background(Color.LightGray),
                    shape = RoundedCornerShape(0.dp)) {
                    GlideImage(
                        model = acceptedPath,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

    }
}