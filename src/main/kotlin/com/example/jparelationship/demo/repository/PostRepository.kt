package com.example.jparelationship.demo.repository

import com.example.jparelationship.demo.domain.*
import com.querydsl.core.types.Projections
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*

interface PostRepository : JpaRepository<Post, Long?>, PostRepositoryCustom{
    @EntityGraph(attributePaths = ["details"], type = EntityGraph.EntityGraphType.LOAD)
    override fun findById(id: Long): Optional<Post>
}

interface PostRepositoryCustom {
    fun findOneBy(postId: Long): PostDto
}

class PostRepositoryImpl : QuerydslRepositorySupport(Post::class.java), PostRepositoryCustom {
    override fun findOneBy(postId: Long): PostDto {
        val post = QPost.post
        val postDetails = QPostDetails.postDetails
        return from(post).select(Projections.constructor(
                PostDto::class.java,
                post.id,
                post.title,
                postDetails.content,
                post.createdAt
        )).innerJoin(post.details, postDetails).where(post.id.eq(postId)).fetchOne()
    }
}

interface PostDetailsRepository : JpaRepository<PostDetails, Long?>