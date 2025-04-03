package com.example.pix.data.flickr.mapper

import com.example.pix.data.room.PictureDbo
import com.example.pix.domain.entity.Picture
import java.util.UUID

fun Picture.toDbo(): PictureDbo= PictureDbo(
    id = UUID.randomUUID().toString(),
    title = title,
    url = url,
    label = ""
)