package org.app.exam

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
@EnableKafka
class ExamApplication

fun main(args: Array<String>) {
    runApplication<ExamApplication>(*args)
}
