package com.docker.compose.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.docker.compose.controllers.TestController;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCases {
	private static final String API_PREFIX = "/elk";

	/**
	 * 用于模拟容器环境的测试对象
	 */
	private MockMvc mockMvc;

	@Autowired
	private TestController testController;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
	}

	@After
	public void teardown() {
		mockMvc = null;
	}

	@Test
	public void testCase1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(API_PREFIX + "/data")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				// HTTP协议的状态码
				.andExpect(status().isOk())
				// 报文内容
				.andExpect(content().string(containsString("\"code\":\"200\"")))
				.andDo(MockMvcResultHandlers.print());
	}

}
