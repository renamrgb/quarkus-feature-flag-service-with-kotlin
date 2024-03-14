package com.github.renamrgb.infra.rest.errorHandler

import com.github.renamrgb.domain.exceptions.UnprocessableEntityException
import jakarta.validation.ValidationException
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import org.jboss.resteasy.reactive.server.ServerExceptionMapper

@Provider
class ExceptionMapper {

    @ServerExceptionMapper
    fun validationException(e: ValidationException): Response {
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
    @ServerExceptionMapper
    fun unprocessableEntityException(e: UnprocessableEntityException): Response {
        return Response.status(422)
            .entity(e.message)
            .type(MediaType.APPLICATION_JSON)
            .build()
    }

    @ServerExceptionMapper
    fun notFoundException(e: NotFoundException): Response {
        return Response.status(Response.Status.NOT_FOUND)
            .entity(e.message)
            .type(MediaType.APPLICATION_JSON)
            .build()
    }
}