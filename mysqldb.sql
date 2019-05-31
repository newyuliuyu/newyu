DROP TABLE IF EXISTS data_exam;
CREATE TABLE data_exam(
	id                  bigint        not null    comment'考试ID 由系统业务系统生成',
	name                varchar(255)  not null    comment'考试名字',
	examState           int           default 0   comment'考试状态 0新建考试 1分析成功 2正在分析 3分析失败 4分析等待 ',
	gradeName           varchar(5)    default ''  comment'考试的年级名称',
	entranceSchoolYear  int           default 0   comment'入学年份',
	semester            int           default 1   comment'学期 1上学期 2下学期',
	learningSegment     int           default 1   comment'学段1小学 2初中 3高中 4其他',
	examLevel           int           default 4   comment'考试级别 1省级 2地市级考试 3区县级考试 4学校级考试 5联考',
	examType             varchar(30)   default ''  comment'考试类型',
	beginTimestamp      bigint        default 0   comment'考试开始时间',
	endTimestamp        bigint        default 0   comment'考试开始时间',
	createTimestamp     bigint        default 0   comment'考试开始时间',
	sourceId            varchar(32)   default ''  comment'考试数据来源唯一ID',
	wl                  tinyint       default 0   comment'考试文理数据 0不分文理，1 理科 2 文科 3 文理分科',
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS data_exam_subject;
CREATE TABLE data_exam_subject(
	id bigint not null comment'由系统业务系统生成',
	examId bigint not null comment'考试id',
	name varchar(20) not null comment'名字',
	wl int not null comment'考试科目文理分类 0不科 1理科 2文科',
	displayOrder int default 0 comment'科目显示顺序',
  fullScore double default 0 comment'科目满分',
  kgFullScore double default 0 comment'客观提满分',
  zgFullScore double default 0 comment'主观题满分',
  childSubjectNames varchar(50) default '' comment'多科目下的子科目',
  examSubject boolean default 1 comment'是否为考试科目',
  multiSubject boolean default 0 comment'是否为多科目',
  fullSubject boolean default 0 comment'是否为全科',
  childSubject boolean default 0 comment'是否子科目，子科目定义为从综合科目拆分出来的科目',
  parentSubject varchar(20) default '' comment'如果为子科目需要设置该科目的父级科目，这总情况只有综合科目拆分子科目才会出现一般为空',
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS data_exam_subject_item;
CREATE TABLE data_exam_subject_item(
	id bigint not null comment'由系统业务系统生成',
	examId bigint not null comment'考试id',
	subjectId bigint not null comment'科目ID',
	name varchar(50) not null comment'名字同一场考试同一个科目名字必须唯一',
	score double default 0 comment'题目满分',
	knowledge varchar(255) default '' comment '知识点',
	ability varchar(255) default '' comment '能力结构',
	titleType varchar(50) default '' comment '题型',
	bigTitleName varchar(50) default '' comment '大题',
	smallTitleName varchar(50) default '' comment '小题',
  itemType tinyint default 0 comment '1单项选择题 2 多项选择题 0非选择题',
  answer varchar(15) default '' comment '正确选项',
  fullOptional varchar(15) default 'ABCD' comment '题目选项列表,默认为ABCD',
  otherSubject varchar(20) default '' comment '综合科目包含的科目，如理综包含物理化学生物',
  choice boolean default 0 comment '是否是选做题',
  choiceInfo varchar(255) default '' comment '选做题规则："从12,13中选1题每题10分"',
  fieldName varchar(20) default '' comment '非选择题对应的成绩数据表中的字段索引，需要根据它查找对应的项',
  displayOrder int default 0 comment '顺序号',
  titleBlock varchar(50) default '' comment '题块',
	PRIMARY KEY (id)
);
DROP TABLE IF EXISTS data_sys_config;
CREATE TABLE data_sys_config(
	id int not null AUTO_INCREMENT,
	code varchar(50) not null comment'系统属性代码',
	name varchar(50) not null comment'系统属性名字',
	value varchar(150) not null comment'系统属性值',
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS data_sys_student_extend_filed;
CREATE TABLE data_sys_student_extend_filed(
	id int not null AUTO_INCREMENT,
	code varchar(50) not null comment'编码',
	name varchar(50) not null comment'名称',
	fileName varchar(150) not null comment'对应外部文件的字段都，多个相同以上的字段用英文逗号隔开',
	PRIMARY KEY (id),
	key code(code)
);


DROP TABLE IF EXISTS data_exam_org_province;
CREATE TABLE data_exam_org_province(
	id int not null AUTO_INCREMENT,
	examId bigint not null comment'考试ID',
	code varchar(50) not null comment'编码',
	name varchar(50) not null comment'名称',
	PRIMARY KEY (id)
);
DROP TABLE IF EXISTS data_exam_org_city;
CREATE TABLE data_exam_org_city(
	id int not null AUTO_INCREMENT,
	examId bigint not null comment'考试ID',
	code varchar(50) not null comment'编码',
	name varchar(50) not null comment'名称',
	PRIMARY KEY (id)
);
DROP TABLE IF EXISTS data_exam_org_county;
CREATE TABLE data_exam_org_county(
	id int not null AUTO_INCREMENT,
	examId bigint not null comment'考试ID',
	code varchar(50) not null comment'编码',
	name varchar(50) not null comment'名称',
	PRIMARY KEY (id)
);
DROP TABLE IF EXISTS data_exam_org_school;
CREATE TABLE data_exam_org_school(
	id int not null AUTO_INCREMENT,
	examId bigint not null comment'考试ID',
	code varchar(50) not null comment'编码',
	name varchar(50) not null comment'名称',
	PRIMARY KEY (id)
);
DROP TABLE IF EXISTS data_exam_org_clazz;
CREATE TABLE data_exam_org_clazz(
	id int not null AUTO_INCREMENT,
	examId bigint not null comment'考试ID',
	schoolCode varchar(50) not null comment'名称',
	schoolName varchar(50) not null comment'名称',
	code varchar(50) not null comment'编码',
	name varchar(50) not null comment'名称',
	wl int default 0 comment'班级文理标志',
	groupValue varchar(50) default '' comment'名称',
	PRIMARY KEY (id)
);
DROP TABLE IF EXISTS data_exam_org_teachClazz;
CREATE TABLE data_exam_org_teachClazz(
	id int not null AUTO_INCREMENT,
	examId bigint not null comment'考试ID',
	schoolCode varchar(50) not null comment'名称',
	schoolName varchar(50) not null comment'名称',
	code varchar(50) not null comment'编码',
	name varchar(50) not null comment'名称',
	subjectName varchar(20) not null comment'科目名字',
	groupValue varchar(50) default '' comment'名称',
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS data_exam_org_x_school;
CREATE TABLE data_exam_org_x_school(
	id int not null AUTO_INCREMENT,
	examId bigint not null comment'考试ID',
	provinceCode varchar(50) default '' comment'名称',
	provinceName varchar(50) default '' comment'名称',
	cityCode varchar(50) default '' comment'名称',
	cityName varchar(50) default '' comment'名称',
	countyCode varchar(50) default '' comment'名称',
	countyName varchar(50) default '' comment'名称',
	schoolCode varchar(50) not null comment'名称',
	schoolName varchar(50) not null comment'名称',
	PRIMARY KEY (id)
);
DROP TABLE IF EXISTS data_exam_import_filed;
CREATE TABLE data_exam_import_filed(
	id int not null AUTO_INCREMENT,
	examId bigint not null comment'考试ID',
	subjectId bigint default 0 comment'科目ID,当科目id为0的时候代表报名库里面的字段',
	code varchar(50) default '' comment'代码名称',
	name varchar(50) default '' comment'名称',
	hasValue boolean default 1 comment'是否右值',
	PRIMARY KEY (id)
);
DROP TABLE IF EXISTS data_exam_subject_version;
CREATE TABLE data_exam_subject_version(
	id int not null AUTO_INCREMENT,
	examId bigint not null comment'考试ID',
	subjectId bigint default 0 comment'科目ID,当科目id为0的时候代表报名库里面的字段',
	previousVesrion int default 0 comment'上一个版本号',
	curVesrion int default 0 comment'当前版本号',
	PRIMARY KEY (id)
);




