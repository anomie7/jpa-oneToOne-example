package com.example.jparelationship.demo.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Post {
    @Id
    @GeneratedValue
    val id: Long? = null
    val title: String? = null

    @OneToOne(mappedBy = "post", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, optional = false)
    private var details: PostDetails? = null
        set(value) {
            if (value == null) {
                if (field != null) {
                    field?.post = null
                }
            } else {
                value
                        .post = this
            }
            field = value

        }
}

@Entity
class PostDetails(
        @Id @GeneratedValue val id: Long? = null,
        @Column val createdAt: LocalDateTime? = LocalDateTime.now(),
        @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "post_id")
        var post: Post? = null
)
