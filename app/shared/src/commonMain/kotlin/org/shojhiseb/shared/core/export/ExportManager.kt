package org.shojhiseb.shared.core.export

import kotlinx.coroutines.flow.firstOrNull
import okio.FileSystem
import okio.Path.Companion.toPath
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.feature_transaction.domain.usecase.TransactionUseCases

class ExportManager(
    private val transactionUseCases: TransactionUseCases
) {
    suspend fun exportToCsv(filePath: String): Boolean {
        try {
            val transactionsResource = transactionUseCases.getTransactions().firstOrNull() ?: return false
            
            if (transactionsResource is Resource.Success) {
                val transactions = transactionsResource.data
                val path = filePath.toPath()
                
                FileSystem.SYSTEM.write(path) {
                    // Write CSV Header
                    writeUtf8("ID,Amount,Note,Type,Category ID,Date\n")
                    
                    // Write Rows
                    transactions.forEach { txn ->
                        writeUtf8("${txn.id},${txn.amount},${txn.note ?: ""},${txn.type},${txn.categoryId},${txn.date}\n")
                    }
                }
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return false
    }
}
