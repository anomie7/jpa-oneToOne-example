package com.example.jparelationship.demo.repository

import org.hibernate.collection.internal.PersistentBag
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class OrderRepositoryTest(
        val personRepository: PersonRepository,
        val orderRepository: OrderRepository,
        val couponRepository: CouponRepository
){

    @Test
    fun `회원이 주문 목록을 조회하는 경우`() {
        val person = personRepository.findById(1).get()

        Assertions.assertTrue(person.orders is PersistentBag)
        Assertions.assertTrue(person.coupons is PersistentBag)
    }

    @Test
    fun `회원이 상세한 주문 내역을 EntityGraph로 조회하는 경우`() {
        val order = orderRepository.findByIdUsingEntityGraph(1)?.run {
            Assertions.assertEquals(totalCount, 10000)
            Assertions.assertEquals(id, 1)
            Assertions.assertEquals(person.id, 1)
            Assertions.assertEquals(person.email, "minu@example.com")
            Assertions.assertEquals(coupon.id, 2)
            Assertions.assertEquals(coupon.name, "vip 회원 감사 쿠폰")
            Assertions.assertEquals(coupon.discountPercentage, 5)
            Assertions.assertEquals(coupon.order.id, 1)
        }
        Assertions.assertNotNull(order)
    }

    @Test
    fun `회원이 상세한 주문 내역을 기본 메소드로 조회하는 경우`() {
        //run 범위 지정 함수를 사용하는 경우 lazy loading 작동하지 않음
        val order = orderRepository.findById(1).get().let {
            Assertions.assertEquals(it.totalCount, 10000)
            Assertions.assertEquals(it.id, 1)
            Assertions.assertEquals(it.person.id, 1)
            Assertions.assertEquals(it.person.email, "minu@example.com")
            Assertions.assertEquals(it.coupon.id, 2)
            Assertions.assertEquals(it.coupon.name, "vip 회원 감사 쿠폰")
            Assertions.assertEquals(it.coupon.discountPercentage, 5)
            Assertions.assertEquals(it.coupon.order.id, 1)
        }
    }


    @Test
    fun `회원이 쿠폰의 상세한 내역을 EntityGraph로 조회하는 경우`() {
        val person = personRepository.getOne(1)
        val coupon = couponRepository.findOneByPerson(person)

        Assertions.assertEquals(coupon[0].id, 1)
        Assertions.assertEquals(coupon[0].order, null)

        Assertions.assertEquals(coupon[1].id, 2)
        Assertions.assertEquals(coupon[1].order.id, 1)

        Assertions.assertEquals(coupon[2].id, 3)
        Assertions.assertEquals(coupon[2].order.id, 2)

        coupon.forEach {
            Assertions.assertEquals(it.name, "vip 회원 감사 쿠폰")
            Assertions.assertEquals(it.discountPercentage, 5)
        }
    }

    @Test
    fun `회원이 쿠폰의 상세한 내역을 기본 메소드로 조회하는 경우`() {
        val person = personRepository.getOne(1)
        val coupon = person.coupons

        Assertions.assertEquals(coupon[0].id, 1)
        Assertions.assertEquals(coupon[0].order, null)

        Assertions.assertEquals(coupon[1].id, 2)
        Assertions.assertEquals(coupon[1].order.id, 1)

        Assertions.assertEquals(coupon[2].id, 3)
        Assertions.assertEquals(coupon[2].order.id, 2)

        coupon.forEach {
            Assertions.assertEquals(it.name, "vip 회원 감사 쿠폰")
            Assertions.assertEquals(it.discountPercentage, 5)
        }
    }
}