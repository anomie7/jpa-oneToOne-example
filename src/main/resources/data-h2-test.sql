insert into post (id, title, created_at) values (1, 'JPA에서 일대일 연관 관계 정의시 고려할 점1', now())
insert into post_details (content, post_id) values ('안녕하세요. 여러분 게시물 내용1', 1)

insert into post (id, title, created_at) values (2, 'JPA에서 일대일 연관 관계 정의시 고려할 점2', now())
insert into post_details (content, post_id) values ('안녕하세요. 여러분 게시물 내용2', 2)

insert into post (id, title, created_at) values (3, 'JPA에서 일대일 연관 관계 정의시 고려할 점3', now())
insert into post_details (content, post_id) values ('안녕하세요. 여러분 게시물 내용3', 3)

insert into post (id, title, created_at) values (4, 'JPA에서 일대일 연관 관계 정의시 고려할 점4', now())
insert into post_details (content, post_id) values ('안녕하세요. 여러분 게시물 내용4', 4)

insert into person (id, email) values (1, 'minu@example.com')
insert into person (id, email) values (2, 'anomie7777@gmail.com')

insert into order_table (id, total_count, person_id) values (1, 10000, 1)
insert into order_table (id, total_count, person_id) values (2, 20000, 1)
insert into order_table (id, total_count, person_id) values (3, 30000, 2)

insert into coupon (id, name, discount_percentage, order_id, person_id) values (1, 'vip 회원 감사 쿠폰', 5, null, 1)
insert into coupon (id, name, discount_percentage, order_id, person_id) values (2, 'vip 회원 감사 쿠폰', 5, 1, 1)
insert into coupon (id, name, discount_percentage, order_id, person_id) values (3, 'vip 회원 감사 쿠폰', 5, 2, 1)
insert into coupon (id, name, discount_percentage, order_id, person_id) values (4, 'vip 회원 감사 쿠폰', 5, null, 2)