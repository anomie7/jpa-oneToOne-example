package com.example.jparelationship.demo.domain

import com.example.jparelationship.demo.LocalDateLongConverter
import com.example.jparelationship.demo.PeriodStringConverter
import java.time.LocalDate
import java.time.Period
import javax.persistence.*

@Entity
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    val id: Long? = null,

    @Convert(converter = PeriodStringConverter::class)
    @Column(columnDefinition = "")
    var span: Period,

    @Convert(converter = LocalDateLongConverter::class)
    @Column(columnDefinition = "")
    var tmpDate: LocalDate = LocalDate.now()
)