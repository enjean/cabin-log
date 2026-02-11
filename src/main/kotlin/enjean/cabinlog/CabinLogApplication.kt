package enjean.cabinlog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class CabinLogApplication

fun main(args: Array<String>) {
	runApplication<CabinLogApplication>(*args)
}
