package com.sem.nutrix.presentation.screens.mealList

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.sem.nutrix.connectivity.ConnectivityObserver
import com.sem.nutrix.connectivity.NetworkConnectivityObserver
import com.sem.nutrix.data.repository.MongoDB
import com.sem.nutrix.data.repository.Products
import com.sem.nutrix.model.Product
import com.sem.nutrix.model.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
internal class MealListViewModel @Inject constructor(
    private val connectivity: NetworkConnectivityObserver,
): ViewModel(){
    private lateinit var allProductsJob: Job
    private lateinit var filteredProductsJob: Job

    var products: MutableState<Products> = mutableStateOf(RequestState.Idle)
    private var network by mutableStateOf(ConnectivityObserver.Status.Unavailable)
    var dateIsSelected by mutableStateOf(false)
        private set
    var uiState by mutableStateOf(UiState())
        private set

    init {
        getProducts()
        viewModelScope.launch {
            connectivity.observe().collect {
                network = it
            }
        }
    }

    fun getProducts(zonedDateTime: ZonedDateTime? = null) {
        dateIsSelected = zonedDateTime != null
        products.value = RequestState.Loading
        if (dateIsSelected && zonedDateTime != null) {
            observeFilteredProducts(zonedDateTime = zonedDateTime)
        } else {
            observeAllProducts()
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeAllProducts(){
        allProductsJob = viewModelScope.launch {
            //make sure to cancel the current Coroutine
            if (::filteredProductsJob.isInitialized){
                filteredProductsJob.cancelAndJoin()
            }
            MongoDB.getAllProducts().debounce(2000).collect { result ->
                products.value = result
            }
        }
    }

    private fun observeFilteredProducts(zonedDateTime: ZonedDateTime)  {
        filteredProductsJob = viewModelScope.launch {
            //make sure to cancel the current Coroutine
            if (::allProductsJob.isInitialized) {
                allProductsJob.cancelAndJoin()
            }
            MongoDB.getFilteredProducts(zonedDateTime = zonedDateTime).collect { result ->
                products.value = result
            }
        }
    }

    fun deleteAllProducts(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        //check Internet-connection first
        if (network == ConnectivityObserver.Status.Available) {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val imagesDirectory = "images/${userId}"
            val storage = FirebaseStorage.getInstance().reference
            storage.child(imagesDirectory)
                .listAll()
                .addOnSuccessListener {
                    it.items.forEach{ref ->
                        val imagePath = "images/${userId}/${ref.name}"
                        storage.child(imagePath).delete()
                            .addOnFailureListener{
                                viewModelScope.launch(Dispatchers.IO) {
//                                    imageToDeleteDao.addImageToDelete(
//                                        ImageToDelete(
//                                            remoteImagePath = imagePath
//                                        )
//                                    )
                                }
                            }
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        val result = MongoDB.deleteAllProducts()
                        if (result is RequestState.Success) {
                            withContext(Dispatchers.Main) {
                                onSuccess()
                            }
                        } else if (result is RequestState.Error) {
                            withContext(Dispatchers.Main) {
                                onError(result.error)
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    onError(it)
                }
        } else {
            onError(Exception("No Internet Connection!"))
        }
    }

    private var selectedProductId:String? by mutableStateOf("")

    fun changeSelectedProductIdState(testText: String?) {
        selectedProductId = testText
    }
    fun deleteProduct(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        Log.d("SelectedProductId", "Value: $selectedProductId")
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedProductId != null) {
                val result = MongoDB.deleteProduct(id = ObjectId.invoke(selectedProductId!!))
                if (result is RequestState.Success) {
                    withContext(Dispatchers.Main) {
//                        uiState.selectedProduct?.let {
//                            deleteImagesFromFirebase(images = it.images)
//                        }
                        onSuccess()
                    }
                } else if (result is RequestState.Error) {
                    withContext(Dispatchers.Main) {
                        onError(result.error.message.toString())
                    }
                }
            } else {
                Log.d("noo", "Error")
            }
        }
    }


}


//{"_id":{"$oid":"6578405af7e273e700e8f23e"},"title":"Brezel","description":"Butter Brezel aus Bayern","kcal":{"$numberLong":"234"},"amount":"1 normale portion (75g)","date":{"$date":{"$numberLong":"1702388091354"}},"ownerId":"65785046cd8866b9e9951921"}

internal data class UiState(
    val selectedProductId: String? = null,
    val selectedProduct: Product? = null,
    val title: String = "",
    val description: String = "",
    val updatedDateTime: RealmInstant? = null
)
