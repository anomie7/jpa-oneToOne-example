package com.example.jparelationship.demo.domain

import org.hibernate.annotations.Formula
import javax.persistence.*

@Entity
class Person(
        @Id @GeneratedValue @Column(insertable = false, updatable = false)
        val id: Long = 0,
        @Column(unique = true) val email: String,
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = [CascadeType.ALL]) val orders: MutableList<Order> = mutableListOf(),
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = [CascadeType.ALL]) val coupons: MutableList<Coupon> = mutableListOf(),
        @Formula("(SELECT count(*) from COUPON c where c.person_id = id)") var couponCount: Int = 0
)

/**
 * 주문 내역을 조회할 때 totalCount을 표시해줘야해서
 * 항상 coupon을 불러와야함
 */
@Entity
@Table(name = "order_table")
class Order(
        @Id @GeneratedValue @Column(insertable = false, updatable = false) val id: Long = 0,
        val totalCount: Int,
        @ManyToOne( cascade = [CascadeType.ALL],fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "person_id", nullable = false) val person: Person,
        @OneToMany(mappedBy = "order", fetch = FetchType.LAZY) private val coupons: MutableList<Coupon> = mutableListOf()
) {

    fun coupon(): Coupon {
        return this.coupons[0]
    }

    fun addCoupon(coupon: Coupon) {
        this.coupons.clear()
        this.coupons.add(coupon)
        coupon.order = this
    }
}

/**
 * 회원이 쿠폰 내역을 조회할 떄 쿠폰의 사용여부를 체크하기 위해 order를 항상 불러 와야함
 */
@Entity
class Coupon(
        @Id @GeneratedValue @Column(insertable = false, updatable = false) val id: Long = 0,
        val name: String,
        val discountPercentage: Int,
        @ManyToOne(fetch = FetchType.LAZY, optional = true) @JoinColumn(name = "order_id") var order: Order? = null,
        @ManyToOne(fetch = FetchType.LAZY, optional = true) @JoinColumn(name = "person_id") var person: Person
)

