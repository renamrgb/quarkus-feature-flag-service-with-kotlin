package com.github.renamrgb.infra.rest.dto

import com.github.renamrgb.domain.FeatureFlag
import jakarta.validation.constraints.NotBlank

class FeatureFlagRequest {

    @field:NotBlank(message = "'flagName' cannot be null or empty")
    var flagName: String = ""

    @field:NotBlank(message = "'sellerIdentifier' cannot be null or empty")
    var sellerIdentifier: String = ""

    fun toDomain(): FeatureFlag {
        return FeatureFlag(flagName, sellerIdentifier)
    }
}
