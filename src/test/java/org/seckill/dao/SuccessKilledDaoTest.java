package org.seckill.dao;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	@Autowired
	private SuccessKilledDao  successKilledDao;
	@Test
	public void testInsertSuccessKilled() {
		/**
		 * 17:09:51.748 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@cb4f2fe] will not be managed by Spring
17:09:51.757 [main] DEBUG o.s.d.S.insertSuccessKilled - ==>  Preparing: insert ignore into success_killed (seckill_id,user_phone) values (?,?) 
17:09:51.797 [main] DEBUG o.s.d.S.insertSuccessKilled - ==> Parameters: 1000(Long), 13713713712(Long)
17:09:51.799 [main] DEBUG o.s.d.S.insertSuccessKilled - <==    Updates: 1
第二次插入 插入0条
		 */
		
		int insertSuccessKilled = successKilledDao.insertSuccessKilled(1001l, 13713713712l);
		System.out.println("插入"+insertSuccessKilled+"条");
	}

	@Test
	public void testQueryByIdWithSeckill() {
		SuccessKilled queryByIdWithSeckill = successKilledDao.queryByIdWithSeckill(1000l, 13713713712l);
		System.out.println(queryByIdWithSeckill);
		System.out.println(queryByIdWithSeckill.getSeckill());
		
	}

}
