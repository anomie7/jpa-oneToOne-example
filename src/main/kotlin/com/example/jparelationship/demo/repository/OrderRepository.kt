package com.example.jparelationship.demo.repository

import com.example.jparelationship.demo.domain.Coupon
import com.example.jparelationship.demo.domain.Order
import com.example.jparelationship.demo.domain.Person
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PersonRepository : JpaRepository<Person, Long>

interface OrderRepository : JpaRepository<Order, Long>{
    @EntityGraph(attributePaths = ["coupons", "person"], type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "select o from Order o where o.id = ?1")
    fun findByIdUsingEntityGraph(id: Long): Order?
}

interface CouponRepository : JpaRepository<Coupon, Long>{
    @EntityGraph(attributePaths = ["order"], type = EntityGraph.EntityGraphType.LOAD)
    fun findOneByPerson(person : Person) : List<Coupon>
}