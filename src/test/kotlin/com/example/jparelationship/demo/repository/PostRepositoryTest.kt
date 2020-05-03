package com.example.jparelationship.demo.repository

import org.hibernate.proxy.HibernateProxy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class PostRepositoryTest(val postRepository: PostRepository) {
    @Test
    fun `일대일 연관관계에서 부모 엔티티가 연관 관계의 주인이고 외래키 null 허용 안하면 지연 로딩 동작함`() {
        val list = postRepository.findAll()

        list.forEach {
            Assertions.assertTrue(it.details is HibernateProxy)
        }
    }

    @Test
    internal fun testFindById() {
        val findById = postRepository.findById(1L)

        val map = findById.map { it.details }

        Assertions.assertTrue(map.get() is HibernateProxy)
        Assertions.assertEquals(map.get().content, "안녕하세요. 여러분 게시물 내용1")
    }

    @AfterEach
    fun deleteAll(): Unit {
        postRepository.deleteAll()
    }
}