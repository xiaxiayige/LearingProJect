package com.xiaxiayige.library.annotation

import com.xiaxiayige.library.Model

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Subscribe(val theadModle: Model =Model.MAINTHEAD)