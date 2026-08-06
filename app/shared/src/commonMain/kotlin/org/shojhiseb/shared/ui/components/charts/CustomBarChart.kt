package org.shojhiseb.shared.ui.components.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import org.shojhiseb.shared.feature_analytics.presentation.ChartData

@Composable
fun CustomBarChart(
    data: List<ChartData>,
    modifier: Modifier = Modifier,
    barSpacing: Float = 40f
) {
    if (data.isEmpty()) return

    val maxVal = data.maxOf { it.value }

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val totalBars = data.size
        val availableWidth = canvasWidth - (barSpacing * (totalBars + 1))
        val barWidth = availableWidth / totalBars

        var currentX = barSpacing

        data.forEach { chartData ->
            val barHeight = if (maxVal > 0) (chartData.value / maxVal) * (canvasHeight * 0.8f) else 0f
            val startY = canvasHeight - barHeight

            drawRoundRect(
                color = Color(chartData.colorHex),
                topLeft = Offset(currentX, startY),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(12f, 12f)
            )

            // Simplistic: real app would draw text for labels here using TextMeasurer
            
            currentX += barWidth + barSpacing
        }
    }
}
