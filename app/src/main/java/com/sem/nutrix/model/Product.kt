package com.sem.nutrix.model

import android.annotation.SuppressLint
import com.sem.nutrix.util.toRealmInstant
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.Instant

open class Product: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var ownerId: String = ""
    var title: String = ""
    var description: String = ""
    var kcal: Int = 0
    var amount: String = ""
    @SuppressLint("NewApi")
    var date: RealmInstant = Instant.now().toRealmInstant()
}