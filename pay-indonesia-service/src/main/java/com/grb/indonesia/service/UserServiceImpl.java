package com.grb.indonesia.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.TransactionalRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import redis.clients.jedis.Jedis;

import com.grb.indonesia.entity.Tables;
import com.grb.indonesia.entity.tables.TestDate;
import com.grb.indonesia.entity.tables.records.TestDateRecord;

@Service
public class UserServiceImpl implements UserService {

	@Autowired DSLContext dsl;
	@Autowired Jedis redis;
	@Autowired DataSourceTransactionManager txManager;
	
	@Override
	public List<?> queryAll() throws Exception {
		
		try {
			queryByLimit();
			updateById1();
			updateById2();
			transaction();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 测试注解式事务
	 */
	@Override
	public void testAnnotationTransaction() throws Exception{
		try {
			//从redis中取数据，若无则存redis，若超时则从数据库中取
			String values = "";
			if(redis.get("key1") != null){
				values = redis.get("key1");
			}else{
				redis.set("key1", "value1");
			}
			Timestamp time = new Timestamp(new Date().getTime());
			dsl.insertInto(Tables.TEST_DATE,TestDate.TEST_DATE.ID,TestDate.TEST_DATE.DT).values(11L,time).execute();
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception("除0异常");
		}
	}
	
	/**
	 * 测试编程式事务
	 */
	@Override
	public void testDeclareTransaction() throws Exception{
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus transactionStatus = null;
		transactionStatus = txManager.getTransaction(def);
		try {
			Timestamp time = new Timestamp(new Date().getTime());
			dsl.insertInto(Tables.TEST_DATE,TestDate.TEST_DATE.ID,TestDate.TEST_DATE.DT).values(11L,time).execute();
			int i = 1/0;
			txManager.commit(transactionStatus);
		} catch (Exception e) {
			txManager.rollback(transactionStatus);
			System.out.println(e);
			throw new Exception("除0异常");
		}
	}
	
	/**
	 * 只更新部分字段
	 
	private void updateById3() {
		
		TradeOrderRecord record = new TradeOrderRecord();
		record.setMercordrno("AUTO2016101411522400671X");
		ULong id = ULong.valueOf(1);
		dsl.update(Tables.TRADE_ORDER).set(record)
			.where(Tables.TRADE_ORDER.ID.eq(id),Tables.TRADE_ORDER.CONSNO.eq("MN786776607566180353"))
			.execute();
		
	}
	*/
	/**
	 * 更新部分字段
	 */
	private void updateById2() {
		
		dsl.update(Tables.TEST_DATE)
			.set(Tables.TEST_DATE.DT,new Timestamp(new Date().getTime()))
			.where(Tables.TEST_DATE.ID.eq(1L))
			.execute();
	}
	
	/**
	 * 更新所有字段
	 */
	private void updateById1() {
		
		TestDateRecord record = new TestDateRecord(2L,new Timestamp(new Date().getTime()));
		dsl.update(Tables.TEST_DATE).set(record)
			.where(Tables.TEST_DATE.ID.eq(2L))
			.execute();
		
	}

	/**
	 * 分页查询
	 */
	public void queryByLimit(){
		List<Record2<Long, Timestamp>> result = dsl.select(Tables.TEST_DATE.ID,Tables.TEST_DATE.DT).from(Tables.TEST_DATE).limit(1,2).fetch();
		Record2<Long, Timestamp> record = result.get(0);
		System.out.println("总共记录数："+result.size()+"   第一条时间是："+record.getValue(Tables.TEST_DATE.field(1)));
	}
	/**
	 * 测试jooq编程式事务
	 * @throws Exception 
	 */
	private void transaction() throws Exception {
		
		final Timestamp time = new Timestamp(new Date().getTime());
		TransactionalRunnable run = new TransactionalRunnable() {
			@Override
			public void run(Configuration configuration) throws Exception {
				dsl.insertInto(Tables.TEST_DATE,TestDate.TEST_DATE.ID,TestDate.TEST_DATE.DT).values(11L,time).execute();
				try {
					int i = 1/0;
				} catch (Exception e) {
					throw new Exception("除0异常");
				}
			}
		};
		dsl.transaction(run);
	}

}
