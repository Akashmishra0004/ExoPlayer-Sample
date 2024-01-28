package com.loyalty.player.ui.player.supportClass.util.track

data class TrackEntity(
    val title: String,
    val index: Int,
    val isSelected: Boolean,
    val onItemClick: (Int) -> Unit
)