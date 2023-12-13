package com.sem.nutrix.data.repository

import com.sem.nutrix.model.Product
import com.sem.nutrix.model.RequestState
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import java.time.LocalDate
import java.time.ZonedDateTime

typealias Products = RequestState<Map<LocalDate, List<Product>>>

internal interface MongoRepository {
    fun configureTheRealm() //makes error, primary Key _id not found
    fun getAllProducts(): Flow<Products>
    fun getFilteredProducts(zonedDateTime: ZonedDateTime): Flow<Products>
    fun getSelectedProduct(productId: ObjectId): Flow<RequestState<Product>>
    suspend fun insertProduct(product: Product): RequestState<Product>
    suspend fun updateProduct(product: Product): RequestState<Product>
    suspend fun deleteProduct(id: ObjectId): RequestState<Boolean>
    suspend fun deleteAllProducts(): RequestState<Boolean>

}