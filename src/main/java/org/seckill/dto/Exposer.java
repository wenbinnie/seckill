package org.seckill.dto;
/**
 * 暴露秒杀接口dto
 * @author wenbinnie
 *
 */
public class Exposer {
	/**
	 * 是否开启秒杀活动
	 */
	private  boolean isExposed;
	
	//一种加密措施
	private String md5;
	
	private long seckillid;
	//系统当前时间（毫秒）
	private long now;
	//开启时间
	private long start;
	//结束时间
	private long end;
	public Exposer(boolean isExposed, String md5, long seckillid) {
		super();
		this.isExposed = isExposed;
		this.md5 = md5;
		this.seckillid = seckillid;
	}
	public Exposer(boolean isExposed,long seckillid, long now, long start, long end) {
		super();
		this.isExposed = isExposed;
		this.now = now;
		this.start = start;
		this.end = end;
	}
	public Exposer(boolean isExposed, long seckillid) {
		super();
		this.isExposed = isExposed;
		this.seckillid = seckillid;
	}
	public boolean isExposed() {
		return isExposed;
	}
	public void setExposed(boolean isExposed) {
		this.isExposed = isExposed;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public long getSeckillid() {
		return seckillid;
	}
	public void setSeckillid(long seckillid) {
		this.seckillid = seckillid;
	}
	public long getNow() {
		return now;
	}
	public void setNow(long now) {
		this.now = now;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	@Override
	public String toString() {
		return "Exposer [isExposed=" + isExposed + ", md5=" + md5
				+ ", seckillid=" + seckillid + ", now=" + now + ", start="
				+ start + ", end=" + end + "]";
	}
	
	
}
