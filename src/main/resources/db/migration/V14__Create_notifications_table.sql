create table notification
(
	id BIGINT auto_increment,
	notifier BIGINT,
	receiver BIGINT,
	outerId BIGINT,
	type int,
	gmt_create BIGINT,
	status INT default 0,
	constraint notification_pk
		primary key (id)
);