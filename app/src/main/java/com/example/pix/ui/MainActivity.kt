package com.example.pix.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.pix.R
import com.example.pix.data.flickr.FlickrRepository
import com.example.pix.data.room.PictureDatabase
import com.example.pix.data.viewmodel.PicturesViewModel
import com.example.pix.domain.entity.Picture
import com.example.pix.ui.theme.PixTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = PicturesViewModel(FlickrRepository(PictureDatabase.getDB(this)))

        viewModel.loadAndSavePictures()
        setContent {
            PixTheme {
                val picturesState = viewModel.pictures.collectAsState()
                val pictures=picturesState.value
                if (pictures.isNotEmpty()) {
                    PictureGrid(
                        pictures = pictures,
                        onPictureClick = { url ->
                            startActivity(Intent(this, SecondScreen::class.java).apply {
                                putExtra("PATH", url)
                            })
                        }
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                        Text("Загрузка изображений...")
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun PictureGrid(pictures: List<Picture>, onPictureClick: (String) -> Unit) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(pictures) { index, picture ->
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(Color.LightGray)
                        .clickable { onPictureClick(picture.url) },
                    shape = RoundedCornerShape(0.dp)
                ) {
                    GlideImage(
                        model = picture.url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
//listOf(
//"https://s0.rbk.ru/v6_top_pics/media/img/0/61/346832026749610.webp",
//"https://storage.yandexcloud.net/yac-wh-sb-prod-s3-media-03002/uploads/article/479/986f7b060354304438c245f8f3eed143.webp"
//)

//val intent by lazy { Intent(this, SecondScreen::class.java) }
//intent.putExtra("PATH", url)
//startActivity(intent)