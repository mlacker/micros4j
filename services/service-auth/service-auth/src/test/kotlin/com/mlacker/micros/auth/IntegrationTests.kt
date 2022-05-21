package com.mlacker.micros.auth

import com.mlacker.micros.auth.domain.user.Account
import com.mlacker.micros.auth.domain.user.AccountPublisher
import org.junit.jupiter.api.Disabled
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class IntegrationTests {

    @Autowired
    private lateinit var context: ApplicationContext

    @Disabled
    fun `accounts register publisher`() {
        val publisher = context.getBean<AccountPublisher>()

        publisher.register(Account(0L, "Test", "Test", "Test"))
    }
}