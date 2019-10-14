package com.docker.compose.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl01 implements TestService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional
	public void parent() {
		jdbcTemplate.execute("INSERT INTO `person` (`first`, `last`, `dateofbirth`, `placeofbirth`) VALUES ( 'a', 'aa', '2019-09-20', 'aaa')");
	}

	@Override
	@Transactional
	public void child() {
		jdbcTemplate.execute("INSERT INTO `person` (`first`, `last`, `dateofbirth`, `placeofbirth`) VALUES ( 'b', 'bb', '2019-09-20', 'bbb')");
	}
}
