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
	beginTiemstamp bigint default 0 comment'考试开始时间',
	endTiemstamp bigint default 0 comment'考试开始时间',
	createTiemstamp bigint default 0 comment'考试开始时间',
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
	knowledge varchar(255) default '' commit '知识点',
	ability varchar(255) default '' commit '能力结构',
	titleType varchar(50) default '' commit '题型',
	bigTitleName varchar(50) default '' commit '大题',
	smallTitleName varchar(50) default '' commit '小题',
  itemType tinyint default '' commit '1单项选择题 2 多项选择题 0非选择题',
  answer varchar(15) default '' commit '正确选项',
  fullOptional varchar(15) default 'ABCD' commit '题目选项列表,默认为ABCD',
  otherSubject varchar(20) default '' commit '综合科目包含的科目，如理综包含物理化学生物',
  choice boolean default 0 commit '是否是选做题',
  choiceInfo varchar(255) default '' commit '选做题规则："从12,13中选1题每题10分"',
  fieldName varchar(20) default '' commit '非选择题对应的成绩数据表中的字段索引，需要根据它查找对应的项',
  displayOrder int default 0 commit '顺序号',
  titleBlock varchar(50) default '' commit '题块',
	PRIMARY KEY (id)
);