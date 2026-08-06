package org.shojhiseb.shared.feature_transaction.domain.repository

import kotlinx.coroutines.flow.Flow
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.feature_transaction.domain.models.Category

interface CategoryRepository {
    fun getAllCategories(): Flow<Resource<List<Category>>>
    suspend fun insertCategory(category: Category): Resource<Unit>
    suspend fun softDeleteCategory(id: String): Resource<Unit>
}
