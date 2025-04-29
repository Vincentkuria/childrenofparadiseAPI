package com.childrenofparadiseorphanage.api.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        return http.cors {  }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/**").permitAll()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource{
        val config= CorsConfiguration().apply {
            allowedOrigins=listOf("*")
            allowedHeaders=listOf("*")
            allowedMethods=listOf("*")
            allowCredentials=false
        }

        val source= UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**",config)

        return source
    }
}