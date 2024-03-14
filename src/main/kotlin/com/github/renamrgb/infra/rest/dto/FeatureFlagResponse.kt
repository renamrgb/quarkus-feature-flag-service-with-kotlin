package com.github.renamrgb.infra.rest.dto

import com.github.renamrgb.domain.FeatureFlag
import jakarta.validation.constraints.NotBlank

data class FeatureFlagRequest(
    @field:NotBlank(message = "'flagName' cannot be null or empty")
    val flagName: String,

    @field:NotBlank(message = "'sellerIdentifier' cannot be null or empty")
    val sellerIdentifier: String,
) {
    fun toDomain(): FeatureFlag = FeatureFlag(flagName, sellerIdentifier)
}
