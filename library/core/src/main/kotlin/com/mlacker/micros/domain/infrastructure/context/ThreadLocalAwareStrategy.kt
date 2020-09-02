package com.mlacker.micros.domain.infrastructure.context

import com.netflix.hystrix.HystrixThreadPoolKey
import com.netflix.hystrix.HystrixThreadPoolProperties
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle
import com.netflix.hystrix.strategy.properties.HystrixProperty
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Callable
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ThreadLocalAwareStrategy(private val existingConcurrencyStrategy: HystrixConcurrencyStrategy?) : HystrixConcurrencyStrategy() {

    override fun getThreadPool(
            threadPoolKey: HystrixThreadPoolKey,
            corePoolSize: HystrixProperty<Int>,
            maximumPoolSize: HystrixProperty<Int>,
            keepAliveTime: HystrixProperty<Int>,
            unit: TimeUnit,
            workQueue: BlockingQueue<Runnable>): ThreadPoolExecutor =
            if (existingConcurrencyStrategy != null)
                existingConcurrencyStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
            else super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)

    override fun getThreadPool(threadPoolKey: HystrixThreadPoolKey, threadPoolProperties: HystrixThreadPoolProperties): ThreadPoolExecutor =
            if (existingConcurrencyStrategy != null) existingConcurrencyStrategy.getThreadPool(threadPoolKey, threadPoolProperties)
            else super.getThreadPool(threadPoolKey, threadPoolProperties)

    override fun getBlockingQueue(maxQueueSize: Int): BlockingQueue<Runnable> =
            if (existingConcurrencyStrategy != null) existingConcurrencyStrategy.getBlockingQueue(maxQueueSize)
            else super.getBlockingQueue(maxQueueSize)

    override fun <T> getRequestVariable(rv: HystrixRequestVariableLifecycle<T>): HystrixRequestVariable<T> =
            if (existingConcurrencyStrategy != null) existingConcurrencyStrategy.getRequestVariable(rv)
            else super.getRequestVariable(rv)

    override fun <T> wrapCallable(callable: Callable<T>): Callable<T> {
        val delegate = ThreadLocalCallableDelegate(callable)
        return if (existingConcurrencyStrategy != null) existingConcurrencyStrategy.wrapCallable(delegate) else super.wrapCallable(delegate)
    }
}