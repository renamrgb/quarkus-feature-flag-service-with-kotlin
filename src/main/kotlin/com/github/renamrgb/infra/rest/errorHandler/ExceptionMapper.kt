package com.github.renamrgb.infra.rest.errorHandler

import jakarta.validation.ValidationException
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import org.jboss.resteasy.reactive.server.ServerExceptionMapper

@Provider
class ValidationExceptionMapper {

    @ServerExceptionMapper
    fun toResponse(e: ValidationException): Response {
        val errors = HashSet<String>()

        val errorMessages = e.message?.split(", ") ?: emptyList()
        for (errorMessage in errorMessages) {
            val parts = errorMessage.split(": ")
            if (parts.size == 2) {
                errors.add(parts[1])
            }
        }

        val responseMap = mapOf("errors" to errors)

        return Response.status(Response.Status.BAD_REQUEST)
            .entity(responseMap)
            .type(MediaType.APPLICATION_JSON)
            .build()
    }
}