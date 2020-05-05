package com.example.jparelationship.demo.repository

import com.example.jparelationship.demo.domain.*
import com.querydsl.core.types.Projections
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface PostRepository : JpaRepository<Post, Long?>, PostRepositoryCustom

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
        )).innerJoin(postDetails).on(post.id.eq(postDetails.id)).where(post.id.eq(postId)).fetchOne()
    }
}

interface PostDetailsRepository : JpaRepository<PostDetails, Long?>