package com.tuga.konum

interface Mapper<in T, out R> {
    operator fun invoke(input: T): R
}
