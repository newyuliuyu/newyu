DROP TABLE IF EXISTS data_exam;
CREATE TABLE data_exam(
	id bigint not null comment'考试ID 由系统业务系统生成',
	name varchar(255) not null comment'考试名字',
	examState int default 0 comment'考试状态',
	PRIMARY KEY (id)
);