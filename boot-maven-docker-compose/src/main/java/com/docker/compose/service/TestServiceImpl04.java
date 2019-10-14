package com.docker.compose.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl04 implements TestService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 这种场景下由于调用child没有抛出异常。两个都会成功
	 */
	@Override
	@Transactional
	public void parent() {
		jdbcTemplate.execute("INSERT INTO `person` (`first`, `last`, `dateofbirth`, `placeofbirth`) VALUES ( 'a', 'aa', '2019-09-20', 'aaa')");
		try {
			child();
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
