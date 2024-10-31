package org.app.exam.dto

import jakarta.validation.constraints.NotEmpty

data class ExamRequestDto(
    @field:NotEmpty(message = "The name cannot be empty")
    val admNo:String,

    @field:NotEmpty(message = "The name cannot be empty")
    val semester:String,

    @field:NotEmpty(message = "The name cannot be empty")
    val assessmentType:String,

    @field:NotEmpty(message = "The name cannot be empty")
    val score:String,
)
