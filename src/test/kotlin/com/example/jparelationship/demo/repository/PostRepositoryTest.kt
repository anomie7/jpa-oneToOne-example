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
        val post = postRepository.getOne(1L)

        Assertions.assertTrue(post.details == null)
    }

    @Test
    fun `queryDsl로 join하고 사용할 칼럼을 명시한 게시물 조회`() {
        val postDto = postRepository.findOneBy(1)

        Assertions.assertEquals(postDto.postId, 1)
        Assertions.assertEquals(postDto.title, "JPA에서 일대일 연관 관계 정의시 고려할 점1")
        Assertions.assertEquals(postDto.content, "안녕하세요. 여러분 게시물 내용1")
    }
}