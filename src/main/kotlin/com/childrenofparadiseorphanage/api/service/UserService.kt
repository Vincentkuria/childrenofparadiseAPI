package com.childrenofparadiseorphanage.api.service

import com.childrenofparadiseorphanage.api.model.User
import com.childrenofparadiseorphanage.api.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val db: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getUserByEmail(email: String): User? {
        return db.findByEmail(email).orElse(null)
    }

    fun createUser(user: User): User {
        val encodedPassword=passwordEncoder.encode(user.password)
        user.password=encodedPassword
        return db.save(user)
    }
}