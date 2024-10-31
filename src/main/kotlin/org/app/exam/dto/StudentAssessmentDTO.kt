package org.app.exam.dto

data class StudentAssessmentDTO(
    val id: Int,
    val studentName: String,
    val semester: String,
    val assessmentType: String,
    val score: Int
)
