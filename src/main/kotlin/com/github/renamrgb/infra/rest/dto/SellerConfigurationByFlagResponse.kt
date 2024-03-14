package com.github.renamrgb.infra.rest.dto

import com.github.renamrgb.domain.FeatureFlag

data class SellerConfigurationByFlagResponse(
    val sellerIdentifier: String
) {
    companion object {
        fun fromDomain(featureFlag: FeatureFlag): SellerConfigurationByFlagResponse {
            return SellerConfigurationByFlagResponse(
                sellerIdentifier = featureFlag.sellerIdentifier
            )
        }
    }
}