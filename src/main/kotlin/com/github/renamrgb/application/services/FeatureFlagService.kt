package com.github.renamrgb.application.services

import com.github.renamrgb.domain.FeatureFlag
import com.github.renamrgb.infra.persistence.repositories.FeatureFlagRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class FeatureFlagService @Inject constructor(
    private val repository: FeatureFlagRepository
) {

    fun create(featureFlag: FeatureFlag): Uni<FeatureFlag> {
        return repository.save(featureFlag);
    }

    fun findByFlagAndIdentifier(flagName: String, sellerIdentifier: String): Uni<FeatureFlag> {
        return repository.findByFlagAndIdentifier(flagName, sellerIdentifier);
    }

    fun delete(flagName: String, sellerIdentifier: String): Uni<Boolean> {
        return repository.delete(flagName, sellerIdentifier);
    }

    fun findAllByFlag(flagName: String): Uni<List<FeatureFlag>> {
        return repository.findAllByFlag(flagName);
    }
}