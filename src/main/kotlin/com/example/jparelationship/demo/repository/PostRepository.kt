package com.example.jparelationship.demo.repository

import com.example.jparelationship.demo.domain.Post
import com.example.jparelationship.demo.domain.PostDetails
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long?>

interface PostDetailsRepository : JpaRepository<PostDetails, Long?>