package org.shojhiseb.shared.feature_transaction.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.shojhiseb.shared.ui.components.CustomTextField
import org.shojhiseb.shared.ui.components.GlassmorphicCard
import org.shojhiseb.shared.ui.components.PrimaryButton

class AddTransactionScreen(
    private val screenModel: TransactionScreenModel
) : Screen {

    @Composable
    override fun Content() {
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        // Assume there is an effect observer here to pop back stack when complete

        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = "Add Transaction")

                        CustomTextField(
                            value = state.amount,
                            onValueChange = { screenModel.handleIntent(TransactionIntent.OnAmountChange(it)) },
                            label = "Amount",
                            isError = state.errorMessage != null,
                            errorMessage = state.errorMessage
                        )

                        CustomTextField(
                            value = state.note,
                            onValueChange = { screenModel.handleIntent(TransactionIntent.OnNoteChange(it)) },
                            label = "Note (Optional)"
                        )

                        // In a real app, add a dropdown for Category and a toggle for TransactionType

                        Spacer(modifier = Modifier.height(16.dp))

                        PrimaryButton(
                            text = "Save",
                            onClick = { screenModel.handleIntent(TransactionIntent.OnSubmit) },
                            enabled = !state.isLoading
                        )
                    }
                }
            }
        }
    }
}
