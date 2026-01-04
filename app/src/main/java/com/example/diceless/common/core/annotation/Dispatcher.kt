package com.example.diceless.common.core.annotation

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: Dispatchers)

enum class Dispatchers {
    IO,
    MAIN,
    DEFAULT,
    UNCONFINED;
}