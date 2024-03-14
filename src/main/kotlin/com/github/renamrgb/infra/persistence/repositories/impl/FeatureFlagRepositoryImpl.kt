package com.github.renamrgb.infra.persistence.repositories.impl

import com.github.renamrgb.domain.FeatureFlag
import com.github.renamrgb.domain.exceptions.UnprocessableEntityException
import com.github.renamrgb.infra.persistence.entities.FeatureFlagPanacheEntity
import com.github.renamrgb.infra.persistence.repositories.FeatureFlagRepository
import io.quarkus.panache.common.Parameters
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class FeatureFlagRepositoryImpl : FeatureFlagRepository {

    override fun save(featureFlag: FeatureFlag): Uni<FeatureFlag> {
        return hasFlag(featureFlag.flagName, featureFlag.sellerIdentifier)
            .flatMap { exist ->
                if (!exist) {
                    val entity = FeatureFlagPanacheEntity(null, featureFlag.flagName, featureFlag.sellerIdentifier)
                    persist(entity)
                    Uni.createFrom().item(FeatureFlag(entity.id, entity.flagName, entity.sellerIdentifier))
                } else {
                    Uni.createFrom().failure(UnprocessableEntityException("Feature flag with the same constraint already exists."))
                }
            }
    }

    override fun findByFlagAndIdentifier(flagName: String, sellerIdentifier: String): Uni<FeatureFlag> {
        val query = "flagName = :flagName and sellerIdentifier = :sellerIdentifier"
        val params = Parameters.with("flagName", flagName)
            .and("sellerIdentifier", sellerIdentifier)

        return Uni.createFrom().item(
            Optional.ofNullable(find(query, params).firstResult())
                .map { (id, flagName, sellerIdentifier) ->
                    FeatureFlag(id, flagName, sellerIdentifier)
                }
                .orElseThrow({ NotFoundException("Feature flag not found with the specified parameters") })
        )
    }

    override fun delete(flagName: String, sellerIdentifier: String): Uni<Boolean> {
        val query = "flagName = :flagName and sellerIdentifier = :sellerIdentifier"
        val params = Parameters.with("flagName", flagName)
            .and("sellerIdentifier", sellerIdentifier)
        if (delete(query, params) < 1) {
            throw NotFoundException("Feature flag not found with the specified parameters");
        }

        return Uni.createFrom().item(true);
    }

    override fun findAllByFlag(flagName: String): Uni<List<FeatureFlag>> {
        val query = "flagName = :flagName"
        val params = Parameters.with("flagName", flagName)

        return Uni.createFrom().item(find(query, params).list()
            .map { (id, flagName, sellerIdentifier) -> FeatureFlag(id, flagName, sellerIdentifier) }
            .toList())
    }

    private fun hasFlag(flagName: String, sellerIdentifier: String): Uni<Boolean> {
        val query = "flagName = :flagName and sellerIdentifier = :sellerIdentifier"
        val params = Parameters.with("flagName", flagName)
            .and("sellerIdentifier", sellerIdentifier)

        return Uni.createFrom().item(Optional.ofNullable(find(query, params).firstResult()).isPresent)
    }
}