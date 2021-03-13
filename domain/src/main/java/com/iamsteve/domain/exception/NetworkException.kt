package com.iamsteve.domain.exception

import java.lang.RuntimeException

class NetworkException(override val message: String) : RuntimeException()