package com.example.jparelationship.demo.repository

import org.hibernate.proxy.HibernateProxy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class PostRepositoryTest(val postRepository: PostRepository) {
    @Test
    fun `일대일 양방향 연관 관계에서 자식 엔티티가 연관 관계의 주인일 때 지연 로딩 동작 안 함`() {
        val list = postRepository.findAll()

        list.mapNotNull { it.details }.forEach {
            Assertions.assertFalse(it is HibernateProxy)
        }
    }

    @Test
    fun testFindById() {
        val post = postRepository.findById(1L).get()

        Assertions.assertFalse(post.details is HibernateProxy)
    }
}