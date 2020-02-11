create table comment
(
	parent_id BIGINT not null,
	id BIGINT auto_increment,
	type int not null,
	commentator int not null,
	gmt_create BIGINT not null,
	gmt_modified BIGINT not null,
	like_count BIGINT default 0,
	constraint comment_pk
		primary key (id)
);