package org.app.exam.controllers


//import org.springframework.security.oauth2.jwt.Jwt
import jakarta.validation.Valid
import org.app.exam.clients.UsersClient
import org.app.exam.dto.ExamRequestDto
import org.app.exam.dto.MessageResponseDto
import org.app.exam.dto.StudentAssessmentDTO
import org.app.exam.models.Exam
import org.app.exam.services.ExamService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/exams")
@CrossOrigin(origins = ["http://localhost:3000"])
class ExamsController(
    private val examService: ExamService,
    private val usersClient: UsersClient
) {

    @GetMapping("")
    fun getAllRecords(@AuthenticationPrincipal jwt: Jwt): MutableList<StudentAssessmentDTO> {

        if(jwt.claims.containsKey("admNo")){
            val admNo = jwt.claims["admNo"] as String? ?: "Unknown"
            val student = usersClient.getUserByAdmNo(admNo)
           return examService.getAllRecords(student.id.toString())
        }else run{
            return examService.getAllRecords()
        }
    }

    @PostMapping("save")
    fun saveExam(@RequestBody @Valid examRequestDto: ExamRequestDto): ResponseEntity<MessageResponseDto> {
        return examService.saveExam(examRequestDto)
    }

    @GetMapping("my-exams")
    @PreAuthorize("hasAnyAuthority('student-access-role')")
    fun studentExams(@AuthenticationPrincipal jwt: Jwt): ResponseEntity<String> {
        val admNo = jwt.claims["admNo"] as String? ?: "Unknown"
        println(admNo)
        return ResponseEntity.ok("My Exams")
    }
}