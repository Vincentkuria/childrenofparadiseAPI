package com.childrenofparadiseorphanage.api.controller

import com.childrenofparadiseorphanage.api.config.security.JwtUtil
import com.childrenofparadiseorphanage.api.model.User
import com.childrenofparadiseorphanage.api.service.UserService
import jakarta.validation.Valid
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/register")
    fun register(@RequestBody @Valid user: User): ResponseEntity<User?> {
        val createdUser=userService.createUser(user)
        return ResponseEntity.ok(createdUser)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginCredentials: LoginCredentials): ResponseEntity<out Map<String, String?>?> {
        userService.getUserByEmail(loginCredentials.email)?.let {
            if (passwordEncoder.matches(loginCredentials.password,it.password)){
                return ResponseEntity.ok(mapOf("token" to jwtUtil.generateToken(loginCredentials.email) ))
            }
        }
        return ResponseEntity.badRequest().body(mapOf("message" to "wrong email or password"))
    }

}

data class LoginCredentials(
    val email: String,
    val password: String
)