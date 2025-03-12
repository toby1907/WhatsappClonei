package com.example.whatsappclonei.ui.status.components

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.request.ImageResult
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest.Listener
import coil.request.SuccessResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Composable
fun VideoPlayer(videoUri: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUri))
            setMediaItem(mediaItem)
            prepare()
        }
    }

    DisposableEffect(key1 = exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                this.player = exoPlayer
            }
        },
        modifier = modifier
    )
}
@Composable
fun DownloadableImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    var downloadProgress by remember { mutableStateOf(0f) }
    var isLoading by remember { mutableStateOf(true) }

    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .listener(object : Listener {
                    override fun onStart(request: ImageRequest) {
                        isLoading = true
                    }

                    override fun onCancel(request: ImageRequest) {
                        isLoading = false
                    }

                    override fun onError(request: ImageRequest, result: ErrorResult) {
                        isLoading = false
                    }

                    override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                        isLoading = false
                    }

                })
                .build(),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            onState = { state ->
                when (state) {
                    is AsyncImagePainter.State.Loading -> {
                        isLoading = true
                    }

                    is AsyncImagePainter.State.Success -> {
                        isLoading = false
                    }

                    is AsyncImagePainter.State.Error -> {
                        isLoading = false
                    }

                    is AsyncImagePainter.State.Empty -> {
                        isLoading = false
                    }
                }
            }
        )

        if (isLoading) {
            CircularProgressIndicator(
                progress = downloadProgress,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}