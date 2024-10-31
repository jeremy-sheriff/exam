package org.app.exam.clients

import feign.RequestInterceptor
import feign.RequestTemplate
import org.app.exam.dto.UserDto
import org.app.exam.services.KeyCloakTokenService
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Configuration
class FeignClientConfig(
    val keyCloakTokenService: KeyCloakTokenService
) {
    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor { template: RequestTemplate ->
            val accessToken = keyCloakTokenService.getToken()
            template.header("Authorization", "Bearer $accessToken")
        }
    }
}

@FeignClient(
    name = "user-client",
    url =  "\${student.url}",
    value =  "",
    path = "api/students"
)


interface UsersClient {

    @GetMapping("exists/id/{id}")
    fun studentExists(@PathVariable id: String):Boolean

    @GetMapping("/id/{id}")
    fun  getUserById(@PathVariable id:String): UserDto

    @GetMapping("exists/{admNo}")
    fun studentExistsByAdmNo(
        @PathVariable admNo: String
    ):Boolean

    @GetMapping("{admNo}")
    fun getUserByAdmNo(
        @PathVariable admNo:String
    ):UserDto
}