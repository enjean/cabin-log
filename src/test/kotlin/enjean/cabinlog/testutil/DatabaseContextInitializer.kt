package enjean.cabinlog.testutil

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

class DatabaseContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        db.start()
        TestPropertyValues.of(
            "spring.datasource.url=${db.jdbcUrl}",
            "spring.datasource.username=${db.username}",
            "spring.datasource.password=${db.password}",
        ).applyTo(applicationContext.environment)
    }

    companion object {
        val db = PostgreSQLContainer("postgres:18")
    }
}