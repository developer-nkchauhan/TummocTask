package com.app.tummoctask.presentation.ui.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class TableDividerDecoration(
    context: Context,
    color: Int,
    strokeWidthDp: Int,
    private val columnCount: Int
) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        this.color = color
        this.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            strokeWidthDp.toFloat(),
            context.resources.displayMetrics
        )
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val top = child.top
            val bottom = child.bottom
            val left = child.left
            val right = child.right

            // Outer border
            canvas.drawLine(left.toFloat(), top.toFloat(), right.toFloat(), top.toFloat(), paint)
            canvas.drawLine(left.toFloat(), bottom.toFloat(), right.toFloat(), bottom.toFloat(), paint)
            canvas.drawLine(left.toFloat(), top.toFloat(), left.toFloat(), bottom.toFloat(), paint)
            canvas.drawLine(right.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)

            // Inner column dividers
            val cellWidth = (right - left) / columnCount
            for (j in 1 until columnCount) {
                val x = left + j * cellWidth
                canvas.drawLine(x.toFloat(), top.toFloat(), x.toFloat(), bottom.toFloat(), paint)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        // to prevent last item being overlapped by Bottom Button
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if(position == itemCount-1) {
            outRect.bottom = 100
        }
    }
}


