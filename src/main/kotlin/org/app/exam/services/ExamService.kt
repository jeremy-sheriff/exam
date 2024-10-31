package org.app.exam.services

import org.app.exam.repositories.ExamRepository
import org.app.exam.clients.UsersClient
import org.app.exam.dto.ExamRequestDto
import org.app.exam.dto.MessageResponseDto
import org.app.exam.dto.StudentAssessmentDTO
import org.app.exam.models.Exam
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ExamService(
    private val examRepository: ExamRepository,
    private val usersClient: UsersClient,
) {

    fun getAllRecords(studentId: String? = null): MutableList<StudentAssessmentDTO> {
        val studentAssessmentList = mutableListOf<StudentAssessmentDTO>()
        println(studentId)
        val exams = if (studentId != null) {
            examRepository.findByStudentId(studentId) // Assumes a method to find exams by student admNo
        } else run {
            examRepository.findAll()
        }

        exams.forEach { exam: Exam? ->
            if (exam != null) {
                exam.id?.let {

                    val studentName = usersClient.getUserById(exam.studentId).name
                    val assessmentTypeString = when (exam.assessmentType) {
                        0 -> "CAT"
                        else -> "Main Exam"
                    }
                    val assessmentObject = StudentAssessmentDTO(
                        id = it.toInt(),
                        studentName = studentName,
                        semester = exam.semester,
                        assessmentType = assessmentTypeString,
                        score = exam.score
                    )
                    studentAssessmentList.add(assessmentObject)
                }
            }
        }
        return studentAssessmentList
    }


    fun saveExam(examRequestDto: ExamRequestDto):ResponseEntity<MessageResponseDto>{
        val studentExists = usersClient.studentExistsByAdmNo(examRequestDto.admNo)
        if (studentExists){

            val student = usersClient.getUserByAdmNo(examRequestDto.admNo)
            val examRecord = Exam(
                id = null,
                studentId = student.id.toString(),
                semester = examRequestDto.semester,
                assessmentType = examRequestDto.assessmentType.toInt(),
                score = examRequestDto.score.toInt(),
            )
            examRepository.save(examRecord)

            return ResponseEntity.ok(MessageResponseDto(("Exam record saved successfully")))
        }else{
            return ResponseEntity.badRequest().body(MessageResponseDto("Student does not exist"))
        }
    }
}