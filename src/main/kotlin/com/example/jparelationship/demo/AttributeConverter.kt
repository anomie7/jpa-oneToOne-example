package com.example.jparelationship.demo

import java.time.LocalDate
import java.time.Period
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class PeriodStringConverter : AttributeConverter<Period, String> {
    override fun convertToDatabaseColumn(attribute: Period?): String {
        return attribute.toString()
    }

    override fun convertToEntityAttribute(dbData: String?): Period {
        return Period.parse(dbData)
    }
}


@Converter
class LocalDateLongConverter : AttributeConverter<LocalDate, Long> {
    override fun convertToDatabaseColumn(attribute: LocalDate?): Long {
        return attribute?.run {
            val split = attribute.toString().split("-")
            (split[0] + split[1] + split[2]).toLong()
        } ?: 0
    }

    override fun convertToEntityAttribute(dbData: Long?): LocalDate {
        dbData?.run {
            val year = dbData / 10000
            val month = (dbData % 10000).toString().substring(0, 2)
            val dayOfWeek = (dbData % 10000).toString().substring(2)
            "$year" + month + dayOfWeek
        }
        return LocalDate.now()
    }

}