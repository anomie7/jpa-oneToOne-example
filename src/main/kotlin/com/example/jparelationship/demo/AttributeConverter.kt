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
        return dbData?.run {
            val year = (dbData / 10000).toInt()
            val month = ((dbData % 10000) / 100).toInt()
            val dayOfWeek = (dbData % 100).toInt()
            LocalDate.of(year, month, dayOfWeek)
        } ?: LocalDate.now()
    }

}