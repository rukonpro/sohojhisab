package org.shojhiseb.shared.ui.navigation.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TabRow
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
import org.shojhiseb.shared.feature_ledger.presentation.LedgerIntent
import org.shojhiseb.shared.feature_ledger.presentation.LedgerScreenModel
import shojhiseb.app.shared.generated.resources.Res
import shojhiseb.app.shared.generated.resources.tab_ledger
import org.shojhiseb.shared.ui.components.GlassmorphicCard

object LedgerTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.tab_ledger)
            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = null
                )
            }
        }

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<LedgerScreenModel>()
        val state by screenModel.state.collectAsState()

        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = state.selectedTab) {
                androidx.compose.material3.Tab(
                    selected = state.selectedTab == 0,
                    onClick = { screenModel.handleIntent(LedgerIntent.OnTabSelected(0)) },
                    text = { Text("To Pay") }
                )
                androidx.compose.material3.Tab(
                    selected = state.selectedTab == 1,
                    onClick = { screenModel.handleIntent(LedgerIntent.OnTabSelected(1)) },
                    text = { Text("To Receive") }
                )
            }

            if (state.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.errorMessage != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Error: ${state.errorMessage}")
                }
            } else {
                val listToDisplay = if (state.selectedTab == 0) state.toPayLedgers else state.toReceiveLedgers
                
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.slideInVertically(initialOffsetY = { 50 }),
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (listToDisplay.isEmpty()) {
                            item {
                                Text("No ledgers found.", modifier = Modifier.padding(16.dp))
                            }
                        }
                        items(listToDisplay) { ledger ->
                            GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(text = ledger.personName, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                                    Text(text = "Amount: ${ledger.amount}")
                                    Text(text = "Status: ${ledger.status}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
