package com.github.renamrgb.infra.rest.dto

import com.github.renamrgb.domain.FeatureFlag

data class FeatureFlagResponse(
    val id: Long?,
    val flagName: String,
    val sellerIdentifier: String,
) {
    companion object {
        fun fromDomain(featureFlag: FeatureFlag): FeatureFlagResponse {
            return FeatureFlagResponse(
                id = featureFlag.id,
                flagName = featureFlag.flagName,
                sellerIdentifier = featureFlag.sellerIdentifier
            )
        }
    }
}
