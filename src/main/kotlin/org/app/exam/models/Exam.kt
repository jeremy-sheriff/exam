package org.app.exam.models

import jakarta.persistence.*

@Table(name = "exam")
@Entity
open class Exam(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id:Long?,

    @Column(name = "studentId", nullable = false)
    open var studentId: String,

    @Column(name = "semester", nullable = false)
    open var semester: String,

    @Column(name = "assessmentType", nullable = false)
    open var assessmentType: Int,

    @Column(name = "score",  nullable = false)
    open var score: Int
) {
    constructor() : this(1,"","",1,1)
}