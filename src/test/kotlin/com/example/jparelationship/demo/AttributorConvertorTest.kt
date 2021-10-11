package com.example.jparelationship.demo

import com.example.jparelationship.demo.domain.Event
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import java.time.LocalDate
import java.time.Period
import javax.persistence.EntityManager

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class AttributeConvertorTest(val em: EntityManager) {
    @Test
    @Order(1)
    fun testSave() {
        val event = Event(
            span = Period.between(LocalDate.now(), LocalDate.now().plusDays(16)),
            tmpDate = LocalDate.now().plusMonths(2)
        )
        em.persist(event)
        em.flush()
    }

    @Test
    @Order(2)
    fun testSelect() {
        val event1 = em.find(Event::class.java, 1L)
        val event2 = em.find(Event::class.java, 2L)
        Assertions.assertEquals(event1.tmpDate, LocalDate.of(2021, 10, 4))
        Assertions.assertEquals(event2.tmpDate, LocalDate.of(2021, 11, 24))
    }
}