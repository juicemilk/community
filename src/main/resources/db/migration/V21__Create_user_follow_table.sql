create table user_follow
(
	id BIGINT auto_increment,
	idol BIGINT,
	fan BIGINT,
	gmt_create BIGINT,
	constraint user_follow_pk
		primary key (id)
);
