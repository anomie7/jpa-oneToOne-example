package com.example.jparelationship.demo.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Post(
        @Id @GeneratedValue
        val id: Long? = null,

        var title: String? = null,
        @Column(name = "created_at")
        val createdAt: LocalDateTime? = LocalDateTime.now(),

        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
        var details: PostDetails? = null
)

@Entity
class PostDetails(
        @Id @GeneratedValue val id: Long? = null,
        @Column(nullable = false) var content: String = ""
)
