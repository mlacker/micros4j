package com.mlacker.micros.domain.infrastructure.context

import com.mlacker.micros.domain.context.Principal
import com.mlacker.micros.domain.infrastructure.context.PrincipalHolder.context
import com.mlacker.micros.domain.infrastructure.context.PrincipalHolder.removeContext
import org.springframework.web.filter.OncePerRequestFilter
import java.net.URLDecoder
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class PrincipalAwareFilter : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val userId = request.getHeader(PrincipalConstants.USER_ID)

        if (userId != null && userId != "") {
            context = Principal(
                    userId.toLong(),
                    URLDecoder.decode(request.getHeader(PrincipalConstants.USER_NAME), Charsets.UTF_8),
                    request.getHeader(PrincipalConstants.APPLICATION_ID)?.toLong())
        }
        filterChain.doFilter(request, response)
        removeContext()
    }
}