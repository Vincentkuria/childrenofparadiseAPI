package com.childrenofparadiseorphanage.api.config.security

import com.childrenofparadiseorphanage.api.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtUtil: JwtUtil,
    private val userService: UserService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        print("Request URI: ${request.requestURI}")
        val authHeader: String?=request.getHeader("Authorization")
        print(request.headerNames)
        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response)
            return
        }

        val token= authHeader.substring(7)

        if (!jwtUtil.isTokenValid(token)){
            filterChain.doFilter(request,response)
            return
        }

        val email= jwtUtil.getClaims(token).subject

        val user= userService.getUserByEmail(email)

        val auth= UsernamePasswordAuthenticationToken(user,null,emptyList<GrantedAuthority>())

        SecurityContextHolder.getContext().authentication=auth
        filterChain.doFilter(request,response)
    }

}