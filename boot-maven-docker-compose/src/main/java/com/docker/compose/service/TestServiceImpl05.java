package com.docker.compose.service;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl05 implements TestService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 *child方法由于异常已经回滚了，而parent可以正确的提交，这才是我们想要的结果！注意的是在parent调用child的时候是通过try/catch捕获了异常的！
	 */
	@Override
	@Transactional
	public void parent() {
		jdbcTemplate.execute("INSERT INTO `person` (`first`, `last`, `dateofbirth`, `placeofbirth`) VALUES ( 'a', 'aa', '2019-09-20', 'aaa')");
		try {
			((TestService)AopContext.currentProxy()).child();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void child() {
		jdbcTemplate.execute("INSERT INTO `person` (`first`, `last`, `dateofbirth`, `placeofbirth`) VALUES ( 'b', 'bb', '2019-09-20', 'bbb')");
		throw new RuntimeException("child Exception....................");
	}
}
