package org.seckill.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class SeckillServiceTest {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillService  seckillService;
	@Test
	public void testGetSeckillList() {
		List<Seckill> seckillList = seckillService.getSeckillList();
		log.info("list={}",seckillList);
	}

	@Test
	public void testGetById() {
		long id=1001l;
		Seckill seckill = seckillService.getById(id);
		log.info("seckill={}",seckill);
	}

	@Test
	public void testSeckillLogic() {
		long id=1000l;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if (exposer.isExposed()){
			log.info("exposer={}",exposer);
			long phone = 12312312318l;
			String md5 = exposer.getMd5();
			try{
			SeckillExecution executeSeckill = seckillService.executeSeckill(id, phone, md5);
			log.info("result={}",executeSeckill);
			} catch(RepeatKillException | SeckillCloseException e ){
				log.error(e.getMessage());
			}
		} else {
			//秒杀未开启
			log.warn("exposer={}",exposer);
		}
		
		/**
		 * exposer=Exposer [isExposed=true, md5=346064bbd9f4a0141290cd97f2ea8431, seckillid=1000, 
		 * now=0, start=0, end=0]		
		 */
	}

	@Ignore
	public void testExecuteSeckill() {
		long id=1000l;
		long phone = 12312312318l;
		String md5 ="346064bbd9f4a0141290cd97f2ea8431";
		try{
		SeckillExecution executeSeckill = seckillService.executeSeckill(id, phone, md5);
		log.info("result={}",executeSeckill);
		} catch(RepeatKillException | SeckillCloseException e ){
			log.error(e.getMessage());
		}
			
/**
 * 21:58:41.292 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Creating a new SqlSession
21:58:41.292 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1d8268bd]
21:58:41.295 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@ac38614] will be managed by Spring
21:58:41.295 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==>  Preparing: update seckill set number = number -1 where seckill_id = ? and start_time <= ? and end_time >= ? and number > 0 
21:58:41.296 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==> Parameters: 1000(Long), 2016-06-05 21:58:41.29(Timestamp), 2016-06-05 21:58:41.29(Timestamp)
21:58:41.297 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - <==    Updates: 1
21:58:41.297 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1d8268bd]
21:58:41.297 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1d8268bd] from current transaction
21:58:41.297 [main] DEBUG o.s.d.S.insertSuccessKilled - ==>  Preparing: insert ignore into success_killed (seckill_id,user_phone,state) values (?,?,0) 
21:58:41.298 [main] DEBUG o.s.d.S.insertSuccessKilled - ==> Parameters: 1000(Long), 12312312312(Long)
21:58:41.299 [main] DEBUG o.s.d.S.insertSuccessKilled - <==    Updates: 1
21:58:41.299 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1d8268bd]
21:58:41.300 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1d8268bd] from current transaction
21:58:41.300 [main] DEBUG o.s.d.S.queryByIdWithSeckill - ==>  Preparing: select sk.seckill_id,sk.user_phone,sk.create_time,sk.state, s.seckill_id "seckill.seckill_id", s.name "seckill.name", s.number "seckill.number", s.start_time "seckill.start_time", s.end_time "seckill.end_time", s.create_time "seckill.create_time" from success_killed sk inner join seckill s on sk.seckill_id = s.seckill_id where sk.seckill_id =? and sk.user_phone = ? 
21:58:41.301 [main] DEBUG o.s.d.S.queryByIdWithSeckill - ==> Parameters: 1000(Long), 12312312312(Long)
21:58:41.305 [main] DEBUG o.s.d.S.queryByIdWithSeckill - <==      Total: 1
21:58:41.306 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1d8268bd]
21:58:41.306 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1d8268bd]
21:58:41.306 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1d8268bd]
21:58:41.306 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1d8268bd]
21:58:41.307 [main] INFO  o.seckill.service.SeckillServiceTest - result=org.seckill.dto.SeckillExecution@65496746
result=SeckillExecution [seckillId=1000, state=1, stateInfo=null, 
successKilled=SuccessKilled [seckillId=1000, userPhone=12312312318, state=0, createTime=Sun Jun 05 22:12:43 CST 2016]]
 */
	}

}
