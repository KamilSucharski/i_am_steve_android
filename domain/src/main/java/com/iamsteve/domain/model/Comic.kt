package com.iamsteve.domain.model

import java.io.Serializable

data class Comic(
    val number: Int,
    val title: String,
    val date: String
) : Serializable