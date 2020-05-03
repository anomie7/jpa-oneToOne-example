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
    fun `일대일 연관 관계에서 자식 엔티티가 연관 관계의 주인일 떄 지연 로딩 동작함`() {
        val list = postDetailsRepository.findAll()

        list.mapNotNull { it.post }.forEach {
            Assertions.assertTrue(it is HibernateProxy)
        }
    }

    @Test
    fun `부모 엔티티의 속성 조회시 자식 엔티티가 없으면 post_details에 대한 조회 쿼리 날리지 않음`() {
        val list = postDetailsRepository.findAll()

        for (i in 0..3) {
            val title = list[i].post?.title
        }
    }

    @Test
    fun testFIndById() {
        val postDetails = postDetailsRepository.findById(1L).get()

        Assertions.assertTrue(postDetails.post is HibernateProxy)
    }
}