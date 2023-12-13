package com.sem.nutrix.data.repository

import android.annotation.SuppressLint
import com.sem.nutrix.model.Product
import com.sem.nutrix.model.RequestState
import com.sem.nutrix.util.Constants.APP_ID
import com.sem.nutrix.util.toInstant
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.query.Sort
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

//Interaction with MongoDB + later local Storage at user Phone -> update when Network Connection
object MongoDB: MongoRepository {
    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

    init {
        configureTheRealm()
    }

    override fun configureTheRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(user, setOf(Product::class))
                .initialSubscriptions { sub ->
                    add(
                        query = sub.query<Product>(query = "ownerId == $0", user.id), //$0 -> first parameter, $1 -> ...
                        name = "User's Products"
                    )
                }
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }

    @SuppressLint("NewApi")
    override fun getAllProducts(): Flow<Products> {
        return if (user != null){
            try{
                realm.query<Product>(query = "ownerId == $0", user.id)
                    .sort(property = "date", sortOrder = Sort.DESCENDING)
                    .asFlow()
                    .map { result ->
                        RequestState.Success(
                            data = result.list.groupBy {
                                it.date.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                        )
                    }
            }catch (e: Exception) {
                flow { emit(RequestState.Error(e)) }
            }
        }else {
            flow { emit(RequestState.Error(UserNotAuthenticatedException())) }
        }
    }


    @SuppressLint("NewApi")
    override fun getFilteredProducts(zonedDateTime: ZonedDateTime): Flow<Products> {
        return if (user != null){
            try {
                realm.query<Product>(
                    "ownerId == $0 AND date < $1 AND date > $2",
                    user.id,
                    RealmInstant.from(
                        LocalDateTime.of(
                            zonedDateTime.toLocalDate().plusDays(1),
                            LocalTime.MIDNIGHT
                        ).toEpochSecond(zonedDateTime.offset), 0
                    ),
                    RealmInstant.from(
                        LocalDateTime.of(
                            zonedDateTime.toLocalDate(),
                            LocalTime.MIDNIGHT
                        ).toEpochSecond(zonedDateTime.offset), 0
                    ),
                ).asFlow().map {result ->
                    RequestState.Success(
                        data = result.list.groupBy {
                            it.date.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        }
                    )
                }
            } catch (e: Exception) {
                flow { emit(RequestState.Error(e)) }
            }
        }else {
            flow { emit(RequestState.Error(UserNotAuthenticatedException())) }
        }
    }

    override fun getSelectedProduct(productId: ObjectId): Flow<RequestState<Product>> {
        return if (user != null){
            try {
                realm.query<Product>(query = "_id == $0", productId).asFlow().map {
                    RequestState.Success(data = it.list.first())
                }
            } catch (e: Exception){
                flow { emit(RequestState.Error(e)) }
            }
        } else {
            flow { emit(RequestState.Error(UserNotAuthenticatedException())) }
        }
    }

    override suspend fun insertProduct(product: Product): RequestState<Product> {
        return if (user != null){
            realm.write {
                try {
                    val addedProduct = copyToRealm(product.apply { ownerId = user.id })
                    RequestState.Success(data = addedProduct)
                } catch (e: Exception){
                    RequestState.Error(e)
                }
            }
        } else {
            RequestState.Error(UserNotAuthenticatedException())
        }
    }


    override suspend fun updateProduct(product: Product): RequestState<Product> {
        return if (user != null){
            realm.write {
                val queriedProduct = query<Product>(query = "_id == $0", product._id).first().find()
                if (queriedProduct != null){
                    queriedProduct.title = product.title
                    queriedProduct.description = product.description
                    queriedProduct.date = product.date
                    RequestState.Success(data = queriedProduct)
                } else {
                    RequestState.Error(error = Exception("Queried Diary does not exist."))
                }
            }
        } else {
            RequestState.Error(UserNotAuthenticatedException())
        }
    }

    override suspend fun deleteProduct(id: ObjectId): RequestState<Boolean> {
        return if (user != null){
            realm.write {
                val product = query<Product>(query= "_id == $0 AND ownerId == $1", id, user.id)
                    .first().find()
                if (product != null){
                    try {
                        delete(product)
                        RequestState.Success(data = true)
                    }catch (e: Exception){
                        RequestState.Error(e)
                    }
                } else {
                    RequestState.Error(Exception("Product does not exist."))
                }

            }
        } else {
            RequestState.Error(UserNotAuthenticatedException())
        }
    }

    override suspend fun deleteAllProducts(): RequestState<Boolean> {
        return if (user != null){
            realm.write {
                val products = this.query<Product>("ownerId == $0", user.id).find()
                try {
                    delete(products)
                    RequestState.Success(data = true)
                }catch (e: Exception){
                    RequestState.Error(e)
                }
            }
        } else {
            RequestState.Error(UserNotAuthenticatedException())
        }
    }
}

private class UserNotAuthenticatedException : Exception("User is not Logged in.")