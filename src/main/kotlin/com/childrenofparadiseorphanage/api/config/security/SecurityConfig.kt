package com.childrenofparadiseorphanage.api.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig{

//    paystack whitelisted Ips to access /webhook/paystack**
    private val paystackIPsExpression = listOf(
        "52.31.139.75",
        "52.49.173.169",
        "52.214.14.220"
    ).joinToString(" or "){"hasIpAddress('$it')"}

    @Bean
    fun filterChain(
        http: HttpSecurity,
        jwtAuthFilter: JwtAuthFilter
    ): SecurityFilterChain{
        return http.cors {  }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/auth/**","/uploads/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/gallery").permitAll()
                    .requestMatchers(HttpMethod.POST,"/donate").permitAll()
                    .requestMatchers(HttpMethod.POST,"/messages").permitAll()
                    .requestMatchers(HttpMethod.GET,"/webhook/paystack/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/webhook/paystack/**")
                    .access(WebExpressionAuthorizationManager(paystackIPsExpression))
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource{
        val frontendConfig= CorsConfiguration().apply {
            allowedOrigins=listOf("https://mamaclare.org","https://www.mamaclare.org")
            allowedHeaders=listOf("*")
            allowedMethods=listOf("*")
            allowCredentials=false
        }

        val webhookConfig= CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedHeaders = listOf("*")
            allowedMethods = listOf("*")
            allowCredentials = false
        }

        val source= UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**",frontendConfig)
        source.registerCorsConfiguration("/webhook/paystack/hook",webhookConfig)

        return source
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }
}