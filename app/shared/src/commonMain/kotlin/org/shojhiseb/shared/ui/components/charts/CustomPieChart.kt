package org.shojhiseb.shared.ui.components.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import org.shojhiseb.shared.feature_analytics.presentation.ChartData

@Composable
fun CustomPieChart(
    data: List<ChartData>,
    modifier: Modifier = Modifier,
    isDonut: Boolean = true,
    strokeWidth: Float = 60f
) {
    if (data.isEmpty()) return

    val total = data.sumOf { it.value.toDouble() }.toFloat()
    if (total == 0f) return

    Canvas(modifier = modifier.fillMaxSize()) {
        var startAngle = -90f

        data.forEach { chartData ->
            val sweepAngle = (chartData.value / total) * 360f

            drawArc(
                color = Color(chartData.colorHex),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = !isDonut,
                style = if (isDonut) Stroke(width = strokeWidth) else androidx.compose.ui.graphics.drawscope.Fill
            )
            
            startAngle += sweepAngle
        }
    }
}
