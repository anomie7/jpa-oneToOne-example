insert into post (id, title, created_at) values (1, 'JPA에서 일대일 연관 관계 정의시 고려할 점1', now())
insert into post_details (content, post_id) values ('안녕하세요. 여러분 게시물 내용1', 1)

insert into post (id, title, created_at) values (2, 'JPA에서 일대일 연관 관계 정의시 고려할 점2', now())
insert into post_details (content, post_id) values ('안녕하세요. 여러분 게시물 내용2', 2)

insert into post (id, title, created_at) values (3, 'JPA에서 일대일 연관 관계 정의시 고려할 점3', now())
insert into post_details (content, post_id) values ('안녕하세요. 여러분 게시물 내용3', 3)

insert into post (id, title, created_at) values (4, 'JPA에서 일대일 연관 관계 정의시 고려할 점4', now())
insert into post_details (content, post_id) values ('안녕하세요. 여러분 게시물 내용4', 4)

insert into event (tmp_date, span, id) values (20211004, 'P6D', 1)
insert into event (tmp_date, span, id) values (20211124, 'P6D', 2)