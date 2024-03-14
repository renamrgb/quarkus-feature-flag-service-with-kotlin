package com.github.renamrgb.infra.persistence.repositories

import com.github.renamrgb.domain.FeatureFlag
import com.github.renamrgb.infra.persistence.entities.FeatureFlagPanacheEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.Uni

interface FeatureFlagRepository : PanacheRepositoryBase<FeatureFlagPanacheEntity, Long> {
    fun save(featureFlag: FeatureFlag): Uni<FeatureFlag>
    fun findByFlagAndIdentifier(flagName: String, sellerIdentifier: String): Uni<FeatureFlag>
    fun delete(flagName: String, sellerIdentifier: String): Uni<Boolean>
    fun findAllByFlag(flagName: String): Uni<List<FeatureFlag>>
}