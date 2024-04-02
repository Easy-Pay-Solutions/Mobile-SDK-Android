package com.fm.easypay.utils.validation

@Retention
@Target(AnnotationTarget.FIELD)
internal annotation class ValidateLength(val maxLength: Int)

@Retention
@Target(AnnotationTarget.FIELD)
internal annotation class ValidateRegex(val regex: String)

@Retention
@Target(AnnotationTarget.FIELD)
internal annotation class ValidateDoubleGreaterThanZero

@Retention
@Target(AnnotationTarget.FIELD)
internal annotation class ValidateNotBlank