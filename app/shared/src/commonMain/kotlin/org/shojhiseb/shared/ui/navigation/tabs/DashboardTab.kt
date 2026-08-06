package org.shojhiseb.shared.ui.navigation.tabs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.stringResource
import org.shojhiseb.shared.feature_dashboard.presentation.DashboardScreenModel
import shojhiseb.app.shared.generated.resources.Res
import shojhiseb.app.shared.generated.resources.tab_dashboard
import org.shojhiseb.shared.ui.components.GlassmorphicCard

object DashboardTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.tab_dashboard)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = null
                )
            }
        }

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<DashboardScreenModel>()
        val state by screenModel.state.collectAsState()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }
                state.errorMessage != null -> {
                    Text(text = "Error: ${state.errorMessage}")
                }
                else -> {
                    AnimatedVisibility(
                        visible = !state.isLoading,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { 50 }),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item {
                                GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text("Total Income: ${state.totalIncome}", style = MaterialTheme.typography.titleMedium)
                                        Text("Total Expense: ${state.totalExpense}", style = MaterialTheme.typography.titleMedium)
                                        Text("Current Balance: ${state.balance}", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            }
                            
                            item {
                                Text("Quick Templates", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                                androidx.compose.foundation.lazy.LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    item {
                                        androidx.compose.material3.ElevatedSuggestionChip(
                                            onClick = {
                                                screenModel.addQuickTransaction(50.0, "Coffee", org.shojhiseb.shared.feature_transaction.domain.models.TransactionType.EXPENSE)
                                            },
                                            label = { Text("☕ Coffee - 50") }
                                        )
                                    }
                                    item {
                                        androidx.compose.material3.ElevatedSuggestionChip(
                                            onClick = {
                                                screenModel.addQuickTransaction(30.0, "Transport", org.shojhiseb.shared.feature_transaction.domain.models.TransactionType.EXPENSE)
                                            },
                                            label = { Text("🚌 Transport - 30") }
                                        )
                                    }
                                }
                            }
                            
                            item {
                                Text("Recent Transactions", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                            }
                            
                            items(state.recentTransactions) { txn ->
                                GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
                                    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Column {
                                            Text(text = "Amount: ${txn.amount}")
                                            Text(text = "Type: ${txn.type}")
                                        }
                                        Text(text = txn.date.toString())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
