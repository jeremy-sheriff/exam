package org.app.exam.repositories

import org.app.exam.models.Exam
import org.springframework.data.jpa.repository.JpaRepository

interface ExamRepository:JpaRepository<Exam,Long>{
    fun findByStudentId(studentId: String):List<Exam>
}