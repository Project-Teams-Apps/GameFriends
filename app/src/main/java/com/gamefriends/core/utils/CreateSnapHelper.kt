package com.gamefriends.core.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class CreateSnapHelper : LinearSnapHelper() {
    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, velocityX: Int, velocityY: Int): Int {
        val layoutManager = layoutManager as LinearLayoutManager
        val targetPosition = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()


        return when {
            targetPosition == RecyclerView.NO_POSITION -> RecyclerView.NO_POSITION
            targetPosition < firstVisibleItemPosition -> firstVisibleItemPosition
            targetPosition > lastVisibleItemPosition -> lastVisibleItemPosition
            else -> targetPosition
        }
    }
}
