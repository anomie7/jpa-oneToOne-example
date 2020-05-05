package com.example.jparelationship.demo.repository

import com.example.jparelationship.demo.domain.PostDto
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
        val postDetails = postDetailsRepository.getOne(1L)

        Assertions.assertTrue(postDetails.post is HibernateProxy)
    }

    @Test
    fun `lazy loading을 이용한 게시물 조회`() {
        val postDetails = postDetailsRepository.getOne(1L)
        val post = postDetails.post!!
        val postDto = PostDto(postId = post.id, title = post.title, content = postDetails.content, createdAt = post.createdAt)

        Assertions.assertEquals(postDto.postId, 1)
        Assertions.assertEquals(postDto.title, "JPA에서 일대일 연관 관계 정의시 고려할 점1")
        Assertions.assertEquals(postDto.content, "안녕하세요. 여러분 게시물 내용1")
    }

    @Test
    fun `EntityGraph를 이용한 게시물 조회`() {
        val postDetails = postDetailsRepository.findById(1L).get()
        val post = postDetails.post!!
        val postDto = PostDto(postId = post.id, title = post.title, content = postDetails.content, createdAt = post.createdAt)

        Assertions.assertEquals(postDto.postId, 1)
        Assertions.assertEquals(postDto.title, "JPA에서 일대일 연관 관계 정의시 고려할 점1")
        Assertions.assertEquals(postDto.content, "안녕하세요. 여러분 게시물 내용1")
    }
}