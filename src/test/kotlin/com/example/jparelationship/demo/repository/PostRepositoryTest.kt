package com.example.jparelationship.demo.repository

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class PostRepositoryTest(val postRepository: PostRepository) {
    @Test
    fun `일대일 단방향 연관 관계에서 자식 엔티티와 식별 관계일 때 지연 로딩 동작 안 함`() {
        val list = postRepository.findAll()

        list.map { it.details }.forEach {
            Assertions.assertTrue(it == null)
        }
    }

    @Test
    fun testFindById() {
        val post = postRepository.findById(1L).get()

        Assertions.assertTrue(post.details == null)
    }
}