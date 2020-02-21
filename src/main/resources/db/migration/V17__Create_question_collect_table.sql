create table question_collect
(
	id BIGINT,
	user_id BIGINT,
	question_id BIGINT,
	gmt_create BIGINT,
	constraint question_collect_pk
		primary key (id)
);
