package com.example.jparelationship.demo.domain

import javax.persistence.*

@Entity
class Person(
        @Id @GeneratedValue val id: Long,
        @Column(unique = true) val email: String,
        @OneToMany(fetch = FetchType.LAZY ,mappedBy = "person", cascade = [CascadeType.ALL]) val orders: MutableList<Order>,
        @OneToMany(fetch = FetchType.LAZY ,mappedBy = "person", cascade = [CascadeType.ALL]) val coupons: MutableList<Coupon>
)

/**
 * 주문 내역을 조회할 때 totalCount을 표시해줘야해서
 * 항상 coupon을 불러와야함
 */
@Entity
@Table(name = "order_table")
class Order(
        @Id @GeneratedValue val id: Long,
        val totalCount: Int,
        @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "person_id", nullable = false) val person: Person,
        @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, optional = true) val coupon: Coupon
)

/**
 * 회원이 쿠폰 내역을 조회할 떄 쿠폰의 사용여부를 체크하기 위해 order를 항상 불러 와야함
 */
@Entity
class Coupon(
        @Id @GeneratedValue val id: Long,
        val name: String,
        val discountPercentage: Int,
        @OneToOne(fetch = FetchType.LAZY, optional = true) @JoinColumn(name = "order_id") val order: Order,
        @ManyToOne(fetch = FetchType.LAZY, optional = true) @JoinColumn(name = "person_id") val person: Person
)

