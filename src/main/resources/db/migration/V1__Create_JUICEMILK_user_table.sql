create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    TOKEN        VARCHAR(36),
    NAME         VARCHAR(70),
    LOGIN        VARCHAR(70),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);