package com.loyalty.player.ui.player.supportClass.extension

import androidx.core.net.toUri
import com.loyalty.player.ui.player.supportClass.argument.VideoSubtitle
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes



fun List<VideoSubtitle>?.toSubtitleMediaItem(): List<MediaItem.SubtitleConfiguration> {
    return this?.map { subtitle ->
        MediaItem.SubtitleConfiguration.Builder(subtitle.url.toUri())
            .setMimeType(MimeTypes.TEXT_VTT)
            .setLabel(subtitle.title)
            .setSelectionFlags(C.SELECTION_FLAG_AUTOSELECT)
            .build()
    }.orEmpty()
}