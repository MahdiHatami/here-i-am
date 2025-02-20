package com.tuga.konum.di.scope

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

@Scope
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ActivityScope
