package com.example.jparelationship.demo.domain

import java.time.LocalDateTime

data class PostDto(val postId: Long?, val title: String?, val content: String?, val createdAt: LocalDateTime?)