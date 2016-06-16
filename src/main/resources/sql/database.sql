--数据库初始化脚本

--创建数据库
CREATE DATABASE seckill;
--使用数据库
use seckill;
--创建秒杀库存表
CREATE TABLE seckill(
seckill_id bigint not null AUTO_INCREMENT COMMENT '商品库存id',
name varchar(120) not null COMMENT '商品名称',
number int not null COMMENT '库存数量',
start_time datetime  not null comment '秒杀开启时间',
end_time datetime  not null comment '秒杀结束时间',
create_time timestamp default current_timestamp not null COMMENT '创建时间',
PRIMARY KEY(seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';
--为什么创建索引要使用key，不用index
--创建初始化数据
insert into seckill (name,number,start_time,end_time)
values 
('1000元秒杀iphone6',100,'2016-06-05 00:00:00','2016-06-05 00:00:00'),
('500元秒杀ipad',200,'2016-06-05 00:00:00','2016-06-05 00:00:00'),
('300元秒杀sansang',300,'2016-06-05 00:00:00','2016-06-05 00:00:00'),
('50元秒杀kindle',400,'2016-06-05 00:00:00','2016-06-05 00:00:00');

--秒杀成功明细表
--用户登录认证相关的信息
CREATE TABLE success_killed(
seckill_id  bigint not null COMMENT '秒杀商品id',
user_phone  bigint not null COMMENT '用户手机号',
state  tinyint not null  DEFAULT -1 COMMENT '状态标志 －1：无效 0:成功 1:已付款',
create_time  timestamp not null COMMENT '创建时间',
PRIMARY KEY (seckill_id,user_phone),  
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
--联合主键 一个人对一个商品只能秒杀一次


--连接数据库控制台
mysql -uroot -p





















