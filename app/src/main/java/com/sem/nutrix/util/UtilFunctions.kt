package com.sem.nutrix.util

import android.annotation.SuppressLint
import io.realm.kotlin.types.RealmInstant
import java.time.Instant

@SuppressLint("NewApi")
fun RealmInstant.toInstant(): Instant {
    val sec: Long = this.epochSeconds
    val nano: Int = this.nanosecondsOfSecond
    return if (sec >= 0) {
        Instant.ofEpochSecond(sec, nano.toLong())
    } else {
        Instant.ofEpochSecond(sec - 1, 1_000_000 + nano.toLong())
    }
}

@SuppressLint("NewApi")
fun Instant.toRealmInstant(): RealmInstant {
    val sec: Long = this.epochSecond
    //The value is always positive and lies in the range '0..999_999_999'
    val nano: Int = this.nano
    return if (sec >= 0) { //For positive timestamps, conversion can happen directly
        RealmInstant.from(sec, nano)
    } else {
        //For negative timestamps, RealmInstant starts from the higher value with negative
        //nanoseconds, while Instant starts from the lower value with positive nanoseconds
        //TODO this probably breaks at edge cases like MIN/MAX
        RealmInstant.from(sec + 1, -1_000_000 + nano)
    }
}