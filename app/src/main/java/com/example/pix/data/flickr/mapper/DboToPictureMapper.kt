package com.example.pix.data.flickr.mapper

import com.example.pix.data.room.PictureDbo
import com.example.pix.domain.entity.Picture

fun PictureDbo.toPicture(): Picture = Picture(
    title = title,
    url = url
)
