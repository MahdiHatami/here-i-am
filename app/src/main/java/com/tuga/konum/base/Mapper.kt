package com.tuga.konum.base

interface Mapper<in T, out R> {
    operator fun invoke(input: T): R
}
