package org.shojhiseb.shared.feature_transaction.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.database.ShojhisebDatabase
import org.shojhiseb.shared.feature_transaction.data.mapper.toDomain
import org.shojhiseb.shared.feature_transaction.domain.models.Category
import org.shojhiseb.shared.feature_transaction.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val database: ShojhisebDatabase
) : CategoryRepository {
    private val queries = database.categoryQueries

    override fun getAllCategories(): Flow<Resource<List<Category>>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { entities ->
                Resource.Success(entities.map { it.toDomain() }) as Resource<List<Category>>
            }
            .catch { e ->
                emit(Resource.Error("Failed to fetch categories", e))
            }
    }

    override suspend fun insertCategory(category: Category): Resource<Unit> {
        return try {
            queries.insertCategory(
                id = category.id,
                nameResId = category.nameResId,
                type = category.type.name,
                icon = category.icon,
                color = category.color,
                priority = category.priority.toLong(),
                isDefault = if (category.isDefault) 1L else 0L,
                created_at = category.createdAt,
                updated_at = category.updatedAt,
                deleted_at = category.deletedAt
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to insert category", e)
        }
    }

    override suspend fun softDeleteCategory(id: String): Resource<Unit> {
        return try {
            val timestamp = 0L 
            queries.softDeleteCategory(deleted_at = timestamp, id = id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to delete category", e)
        }
    }
}
