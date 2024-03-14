package com.github.renamrgb.infra.persistence.repository

import com.github.renamrgb.infra.persistence.entities.FeatureFlagPanacheEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase

interface FeatureFlagRepository : PanacheRepositoryBase<FeatureFlagPanacheEntity, Long> {

}