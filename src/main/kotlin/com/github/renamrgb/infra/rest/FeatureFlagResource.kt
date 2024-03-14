package com.github.renamrgb.infra.rest

import com.github.renamrgb.application.services.FeatureFlagService
import com.github.renamrgb.infra.rest.dto.FeatureFlagRequest
import com.github.renamrgb.infra.rest.dto.FeatureFlagResponse
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response


@Path("/api/feature-flag")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class FeatureFlagResource @Inject constructor(
    private val featureFlagService: FeatureFlagService
) {

    @Transactional
    @POST
    fun create(@Valid request: FeatureFlagRequest): Uni<Response> {
        return featureFlagService.create(request.toDomain())
            .onItem().transform { entity ->
                Response.status(Response.Status.CREATED)
                    .entity(FeatureFlagResponse.fromDomain(entity))
                    .build()
            }
    }
}