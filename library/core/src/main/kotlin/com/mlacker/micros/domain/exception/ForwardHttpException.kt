package com.mlacker.micros.domain.exception

class ForwardHttpException(val status: Int, message: String?) : Exception(message)