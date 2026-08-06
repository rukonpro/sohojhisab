package org.shojhiseb.shared.ui.navigation.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.stringResource
import org.shojhiseb.shared.feature_analytics.presentation.AnalyticsScreenModel
import shojhiseb.app.shared.generated.resources.Res
import shojhiseb.app.shared.generated.resources.tab_analytics
import org.shojhiseb.shared.ui.components.charts.CustomBarChart
import org.shojhiseb.shared.ui.components.charts.CustomPieChart

object AnalyticsTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.tab_analytics)
            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = null
                )
            }
        }

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<AnalyticsScreenModel>()
        val state by screenModel.state.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.errorMessage != null) {
                Text(text = "Error: ${state.errorMessage}")
            } else {
                Text("Cash Flow (Income vs Expense)")
                CustomBarChart(
                    data = state.cashFlowData,
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )

                Text("Expense by Category")
                CustomPieChart(
                    data = state.categoryBreakdown,
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )
            }
        }
    }
}
