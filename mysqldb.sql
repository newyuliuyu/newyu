DROP TABLE IF EXISTS data_exam;
CREATE TABLE data_exam(
	id bigint not null comment'考试ID 由系统业务系统生成',
	name varchar(255) not null comment'考试名字',
	examState int default 0 comment'考试状态 0新建考试 1分析成功 2正在分析 3分析失败 4分析等待 ',
	gradeName varchar(5) default '' comment'考试的年级名称',
	entranceSchoolYear int default 0 comment'入学年份',
	semester int default 1 comment'学期 1上学期 2下学期',
	learningSegment int default 1 comment'学段1小学 2初中 3高中 4其他',
	examLevel int default 4 comment'考试级别 1省级 2地市级考试 3区县级考试 4学校级考试 5联考',
	eamType varchar(30) default '' comment'考试类型',
	PRIMARY KEY (id)
);