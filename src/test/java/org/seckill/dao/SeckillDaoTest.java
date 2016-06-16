package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和junit整合，为了junit启动时加载springIOC容器
 * spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	//注入DAo实现类依赖
	@Autowired
	private SeckillDao  seckilldao;
	
	@Test
	public void testQueryById() {
		long id = 1000l;
		Seckill seckill = seckilldao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
		/**
		 * 
		 * 	1000元秒杀iphone6
		Seckill [seckillId=1000, name=1000元秒杀iphone6, number=100, 
		startTime=Sun Jun 05 00:00:00 CST 2016, 
		endTime=Sun Jun 05 00:00:00 CST 2016, 
		createTime=Sun Jun 05 00:36:53 CST 2016]
		 */
	
	}
	
	@Test
	public void testQueryAll() {
		List<Seckill> queryAll = seckilldao.queryAll(0, 100);
		for (Seckill s :queryAll){
			System.out.println(s);
		}
	}
	
	@Test
	public void testReduceNumber() {
		/**
		 * SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1604dd0d]
		 *  was not registered for synchronization because synchronization is not active
16:36:38.408 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection 
[com.mchange.v2.c3p0.impl.NewProxyConnection@706c8123] will not be managed by Spring
16:36:38.408 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==>  
Preparing: update seckill set number = number -1 where seckill_id = ? and start_time <= ? and end_time >= ? and number > 0 
16:36:38.410 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==>
 Parameters: 1000(Long), 2016-06-05 16:36:38.407(Timestamp), 2016-06-05 16:36:38.407(Timestamp)
16:36:38.410 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - <==    Updates: 0
		 */
		Date killTime = new Date();
		int reduceNumber = seckilldao.reduceNumber(1000l, killTime);
		System.out.println(reduceNumber);
	}



}
