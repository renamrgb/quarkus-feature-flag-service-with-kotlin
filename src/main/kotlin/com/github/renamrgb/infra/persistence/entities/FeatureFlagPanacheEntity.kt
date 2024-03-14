package com.github.renamrgb.infra.persistence.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class FeatureFlagPanacheEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    val flagName: String,
    val sellerIdentifier: String,
) : PanacheEntityBase {
    protected constructor() : this(null, "", "")

//    fun toDomain(): FeatureFlag {
//        return FeatureFlag(flagName, sellerIdentifier, module)
//    }
}
