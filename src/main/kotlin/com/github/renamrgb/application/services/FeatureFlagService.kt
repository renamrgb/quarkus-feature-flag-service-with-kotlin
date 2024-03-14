package com.github.renamrgb.application.services

import com.github.renamrgb.domain.FeatureFlag
import com.github.renamrgb.infra.persistence.repositories.FeatureFlagRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@ApplicationScoped
class FeatureFlagService @Inject constructor(
    private val repository: FeatureFlagRepository
) {

    @Transactional
    fun create(featureFlag: FeatureFlag): Uni<FeatureFlag> {
        return repository.save(featureFlag);
    }

}