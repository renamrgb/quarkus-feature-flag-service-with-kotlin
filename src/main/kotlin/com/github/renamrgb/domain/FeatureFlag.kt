package com.github.renamrgb.domain

data class FeatureFlag(
    val id: Long?,
    val flagName: String,
    val sellerIdentifier: String
) {
    constructor(flagName: String, sellerIdentifier: String) : this(null, flagName, sellerIdentifier)

}
