package org.shojhiseb.shared.feature_transaction.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.feature_transaction.domain.models.Category
import org.shojhiseb.shared.feature_transaction.domain.repository.CategoryRepository

class GetCategoriesUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<Resource<List<Category>>> {
        return repository.getAllCategories()
    }
}

class InsertCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category): Resource<Unit> {
        if (category.nameResId.isBlank()) {
            return Resource.Error("Category name cannot be empty")
        }
        return repository.insertCategory(category)
    }
}

class DeleteCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(id: String): Resource<Unit> {
        return repository.softDeleteCategory(id)
    }
}

data class CategoryUseCases(
    val getCategories: GetCategoriesUseCase,
    val insertCategory: InsertCategoryUseCase,
    val deleteCategory: DeleteCategoryUseCase
)
