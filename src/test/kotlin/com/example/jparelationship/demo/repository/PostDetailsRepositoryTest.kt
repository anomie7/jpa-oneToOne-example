package com.example.jparelationship.demo.repository

import org.hibernate.proxy.HibernateProxy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class PostDetailsRepositoryTest(val postDetailsRepository: PostDetailsRepository) {
    @Test
    fun `일대일 단방향 연관 관계에서 부모 엔티티와 식별 관계일 때 지연 로딩 동작함`() {
        val list = postDetailsRepository.findAll()

        list.mapNotNull { it.post }.forEach {
            Assertions.assertTrue(it is HibernateProxy)
        }
    }

    @Test
    fun testFIndById() {
        val postDetails = postDetailsRepository.findById(1L).get()

        Assertions.assertTrue(postDetails.post is HibernateProxy)
    }
}