package org.shojhiseb.shared.ui.navigation.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
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
import org.shojhiseb.shared.feature_settings.presentation.SettingsIntent
import org.shojhiseb.shared.feature_settings.presentation.SettingsScreenModel
import org.shojhiseb.shared.generated.resources.Res
import org.shojhiseb.shared.generated.resources.tab_settings
import org.shojhiseb.shared.ui.components.GlassmorphicCard

object SettingsTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.tab_settings)
            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = null
                )
            }
        }

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<SettingsScreenModel>()
        val state by screenModel.state.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(Res.string.tab_settings),
                style = MaterialTheme.typography.headlineMedium
            )

            GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Dark Mode")
                    Switch(
                        checked = state.theme == "Dark",
                        onCheckedChange = { isDark ->
                            val newTheme = if (isDark) "Dark" else "Light"
                            screenModel.handleIntent(SettingsIntent.OnThemeChanged(newTheme))
                        }
                    )
                }
            }

            GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Language")
                    Text(
                        text = state.language,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(8.dp)
                    )
                    // In a real app, this would open a dropdown or dialog
                }
            }

            GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Currency")
                    Text(
                        text = state.currency,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(8.dp)
                    )
                    // In a real app, this would open a dropdown or dialog
                }
            }

            androidx.compose.material3.Button(
                onClick = { 
                    // In a real app, you would use a file picker to get the path
                    val dummyPath = "export.csv" 
                    screenModel.handleIntent(SettingsIntent.ExportData(dummyPath))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Export Data (CSV)")
            }
        }
    }
}
