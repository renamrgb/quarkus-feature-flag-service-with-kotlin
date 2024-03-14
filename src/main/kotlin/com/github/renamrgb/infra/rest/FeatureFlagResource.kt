package com.github.renamrgb.infra.rest

import com.github.renamrgb.application.services.FeatureFlagService
import com.github.renamrgb.infra.rest.dto.FeatureFlagRequest
import com.github.renamrgb.infra.rest.dto.FeatureFlagResponse
import com.github.renamrgb.infra.rest.dto.SellerConfigurationByFlagResponse
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.*
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
            .onItem().transform { featureFlag ->
                Response.status(Response.Status.CREATED)
                    .entity(FeatureFlagResponse.fromDomain(featureFlag))
                    .build()
            }
    }

    @Transactional
    @GET
    @Path("/{flagName}/{sellerIdentifier}")
    fun find(
        @PathParam("flagName") @Encoded flagName: String,
        @PathParam("sellerIdentifier") @Encoded sellerIdentifier: String
    ): Uni<Response> {
        return featureFlagService.findByFlagAndIdentifier(flagName, sellerIdentifier)
            .onItem().transform { featureFlag ->
                Response.status(Response.Status.OK)
                    .entity(FeatureFlagResponse.fromDomain(featureFlag))
                    .build()
            }
    }

    @Transactional
    @GET
    @Path("/{flagName}")
    fun findAllBYFlag(@PathParam("flagName") @Encoded flagName: String): Uni<Response> {
        return featureFlagService.findAllByFlag(flagName)
            .onItem().transform { featureFlags ->
                val response = featureFlags.map { SellerConfigurationByFlagResponse.fromDomain(it) }
                Response.ok(response).build()
            }
    }

    @Transactional
    @DELETE
    @Path("/{flagName}/{sellerIdentifier}")
    fun delete(
        @PathParam("flagName") @Encoded flagName: String,
        @PathParam("sellerIdentifier") @Encoded sellerIdentifier: String
    ): Uni<Response> {
        return featureFlagService.delete(flagName, sellerIdentifier)
            .onItem().transform { Response.status(Response.Status.NO_CONTENT).build() }
    }
}