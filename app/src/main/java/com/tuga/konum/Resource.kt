package com.tuga.konum

import com.tuga.konum.Status.EMPTY
import com.tuga.konum.Status.ERROR
import com.tuga.konum.Status.LOADING
import com.tuga.konum.Status.SUCCESS

data class Resource<out T>(val status: Status, val result: T?, val message: String?) {
    companion object {
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(ERROR, null, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(LOADING, data, null)
        }

        fun <T> empty(msg: String): Resource<T> {
            return Resource(EMPTY, null, msg)
        }
    }
}
