import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Value("\${cors.allowed.origins}")
    lateinit var allowedOrigins: List<String> // Injects the allowed origins

    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true

        // Add each allowed origin to the configuration
        allowedOrigins.forEach { config.addAllowedOrigin(it) }

        config.addAllowedHeader("*") // Allow all headers
        config.addAllowedMethod("OPTIONS")
        config.addAllowedMethod("GET")
        config.addAllowedMethod("POST")
        config.addAllowedMethod("PUT")
        config.addAllowedMethod("DELETE")

        config.addExposedHeader("Access-Control-Allow-Origin")
        config.addExposedHeader("Access-Control-Allow-Credentials")
        config.maxAge = 3600L // Cache preflight response for 1 hour

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config) // Apply CORS to all endpoints

        return CorsFilter(source)
    }
}
