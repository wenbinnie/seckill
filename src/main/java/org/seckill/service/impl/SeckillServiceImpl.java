package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
// @Component @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	//注入service依赖   @Resource @Inject两个是j2ee的注解
	//autowied 会查找SeckillDao类型的一个实例注入到属性
	@Autowired 
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successkilleddao;
	//md5 盐值
	private final String salt = "1231qwqe2313q!!@2#";
	public List<Seckill> getSeckillList() {
		// TODO Auto-generated method stub
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);//可以把这个查询结果缓存到redis
		if (seckill == null){
			return new Exposer(false,seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime(); 
		Date now = new Date();
		if (now.getTime()<startTime.getTime()||
				now.getTime()>endTime.getTime()){
			return new Exposer(false,seckillId,now.getTime(),startTime.getTime(),endTime.getTime());
		}
		//转化特定字符串的过程，不可逆，
		String md5 = this.getMD5(seckillId);
		return new Exposer(true,md5,seckillId);
	}
   @Transactional
   /**
    * 使用注解控制事务方法的优点
    * 1:开发团队达成一致约定，明确标注事务方法的编程风格
    * 2:保证事务方法的执行时间尽可能短，不要穿插其他的网络操作，如rpc，缓存（redis,memeorychge)，http请求等，或者剥离事务方法外部
    *  ，正是因为有写入数据库操作，才锁定数据
    *  3:不是所有的方法都需要事务，比如只有一条修改操作，只读操作不需要事务控制
    */
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException {
		//判断用户输入的md5
		if(md5 == null || !md5.equals(this.getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite");//数据被改写
		}
		//执行秒杀逻辑:1.减少库存 2.纪录购买行为，也就是成功购买纪录
		Date now = new Date();
		try{
		int updateCount = seckillDao.reduceNumber(seckillId, now);
		//没有秒杀到纪录
		if (updateCount <= 0){
			throw new SeckillCloseException("seckill is close");
		} else {
			int insertCount = this.successkilleddao.insertSuccessKilled(seckillId, userPhone);
			if (insertCount <= 0){
				throw new RepeatKillException("seckill is repeated");
			} else {
				//秒杀成功
				SuccessKilled successKilled = successkilleddao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
			}
		}
		}catch(SeckillCloseException e){
			throw e;
		}catch(RepeatKillException e){
			throw e;
		}
		catch(Exception e){
			log.error(e.getMessage(),e);
			throw new SeckillException("seckill innner exception:"+e.getMessage());
		}
	}
	
	private String getMD5(long seckillId){
		String base = seckillId + "/" + salt;
		//spirng的工具类 生成md5
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

}
