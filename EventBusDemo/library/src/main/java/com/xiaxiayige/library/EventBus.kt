package com.xiaxiayige.library


import android.app.Activity
import android.os.Looper
import com.xiaxiayige.library.annotation.Subscribe
import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.logging.Handler
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/***
 * 仿 EventBus
 */
object  EventBus{
    val  eventList=ArrayList<Any>()
    val handler:android.os.Handler=android.os.Handler(Looper.getMainLooper())
    val executorService:ExecutorService= Executors.newCachedThreadPool()


    fun regiest(any: Any){
        //获取当前类的所有方法
        val methods=any.javaClass.declaredMethods
        println(">>> = [${methods.size}]")
        //查找带有Subscribe注解的方法
        methods.forEach { method ->
            val annotation = method.getAnnotation(Subscribe::class.java)
            if(annotation!=null){ //
                //保存方法和保存参数
                val parameterTypes = method.parameterTypes
                if(parameterTypes.size==1){
                    //保存订阅的方法
                    eventList.add(any)
                }
            }
        }

    }

     fun post(param:Any){
         eventList.forEach { obj->
             val methods=obj.javaClass.declaredMethods
             println(">>> = [${methods.size}]")
             //查找带有Subscribe注解的方法
             methods.forEach { method ->
                 val annotation = method.getAnnotation(Subscribe::class.java)
                 if(annotation!=null) { //
                     //保存方法和保存参数
                     val parameterTypes = method.parameterTypes
                     if (parameterTypes.size == 1 &&  parameterTypes[0].isAssignableFrom(param.javaClass)) {
                         val theadModle = annotation.theadModle
                         //判断当前是否在主线程
                         if(Looper.myLooper() == Looper.getMainLooper()){
                             if(theadModle ==Model.MAINTHEAD){
                                 method.invoke(obj,param)
                             }else{
                                 executorService.submit {
                                     method.invoke(obj,param)
                                 }
                             }
                         }else{
                             if(theadModle ==Model.MAINTHEAD){
                                 handler.post {
                                     method.invoke(obj,param)
                                 }
                             }else{
                                 method.invoke(obj,param)
                             }
                         }
                     }
                 }
                 }
         }
     }
}