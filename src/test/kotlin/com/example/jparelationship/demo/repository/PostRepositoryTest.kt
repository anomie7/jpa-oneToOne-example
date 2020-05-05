package com.example.jparelationship.demo.repository

import com.example.jparelationship.demo.domain.PostDto
import org.hibernate.proxy.HibernateProxy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class PostRepositoryTest(val postRepository: PostRepository) {
    @Test
    fun `일대일 연관관계에서 부모 엔티티가 연관 관계의 주인이고 외래키 null 허용시 지연 로딩 동작함`() {
        val list = postRepository.findAll()

        list.mapNotNull { it.details }.forEach {
            Assertions.assertTrue(it is HibernateProxy)
        }
    }

    @Test
    fun `자식 엔티티의 속성 조회시 자식 엔티티가 없으면 post_details에 대한 조회 쿼리 날리지 않음`() {
        val list = postRepository.findAll()

        for (i in 0..3) {
            val content = list[i].details?.content
        }
    }

    @Test
    fun testFindById() {
        val post = postRepository.getOne(1L)

        Assertions.assertTrue(post.details is HibernateProxy)
    }

    @Test
    fun `join을 이용한 게시물 조회`() {
        val postDto = postRepository.findOneBy(1)

        Assertions.assertEquals(postDto.postId, 1)
        Assertions.assertEquals(postDto.title, "JPA에서 일대일 연관 관계 정의시 고려할 점1")
        Assertions.assertEquals(postDto.content, "안녕하세요. 여러분 게시물 내용1")
    }

    @Test
    fun `lazy loading을 이용한 게시물 조회`() {
        val post = postRepository.getOne(1L)
        val postDto = PostDto(postId = post.id, title = post.title, content = post.details?.content, createdAt = post.createdAt)

        Assertions.assertEquals(postDto.postId, 1)
        Assertions.assertEquals(postDto.title, "JPA에서 일대일 연관 관계 정의시 고려할 점1")
        Assertions.assertEquals(postDto.content, "안녕하세요. 여러분 게시물 내용1")
    }

    @Test
    fun `EntityGraph를 이용한 게시물 조회`() {
        val post = postRepository.findById(1L).get()
        val postDto = PostDto(postId = post.id, title = post.title, content = post.details?.content, createdAt = post.createdAt)

        Assertions.assertEquals(postDto.postId, 1)
        Assertions.assertEquals(postDto.title, "JPA에서 일대일 연관 관계 정의시 고려할 점1")
        Assertions.assertEquals(postDto.content, "안녕하세요. 여러분 게시물 내용1")
    }
}