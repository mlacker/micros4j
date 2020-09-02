package com.mlacker.micros.config

import com.mlacker.micros.domain.infrastructure.context.PrincipalAwareFilter
import com.mlacker.micros.domain.infrastructure.context.PrincipalInjectInterceptor
import com.mlacker.micros.domain.infrastructure.context.ThreadLocalAwareStrategy
import com.netflix.hystrix.strategy.HystrixPlugins
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy
import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import javax.annotation.PostConstruct

@Configuration
class PrincipalConfig {

    private var existingConcurrentStrategy: HystrixConcurrencyStrategy? = null

    @Autowired(required = false)
    fun setExistingConcurrentStrategy(existingConcurrentStrategy: HystrixConcurrencyStrategy?) {
        this.existingConcurrentStrategy = existingConcurrentStrategy
    }

    @PostConstruct
    fun init() {
        val concurrencyStrategy = ThreadLocalAwareStrategy(existingConcurrentStrategy)
        val plugins = HystrixPlugins.getInstance()
        val eventNotifier = plugins.eventNotifier
        val metricsPublisher = plugins.metricsPublisher
        val propertiesStrategy = plugins.propertiesStrategy
        val commandExecutionHook = plugins.commandExecutionHook
        HystrixPlugins.reset()
        plugins.registerConcurrencyStrategy(concurrencyStrategy)
        plugins.registerEventNotifier(eventNotifier)
        plugins.registerMetricsPublisher(metricsPublisher)
        plugins.registerPropertiesStrategy(propertiesStrategy)
        plugins.registerCommandExecutionHook(commandExecutionHook)
    }

    @Bean
    fun principalAwareFilter(): OncePerRequestFilter {
        return PrincipalAwareFilter()
    }

    @Bean
    fun principalInjectInterceptor(): RequestInterceptor {
        return PrincipalInjectInterceptor()
    }
}