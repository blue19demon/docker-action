package com.docker.compose.test;

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

import com.docker.compose.controllers.OrderController;

/**
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTestCases {
	private static final String API_PREFIX = "/test";

	/**
	 * 用于模拟容器环境的测试对象
	 */
	private MockMvc mockMvc;

	@Autowired
	private OrderController orderController;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	@After
	public void teardown() {
		mockMvc = null;
	}

	@Test
	public void tranAOPTestCase1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(API_PREFIX + "/insert01").accept(MediaType.APPLICATION_JSON_UTF8))
				// HTTP协议的状态码
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void tranAOPTestCase2() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(API_PREFIX + "/insert02").accept(MediaType.APPLICATION_JSON_UTF8))
				// HTTP协议的状态码
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void tranAOPTestCase3() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(API_PREFIX + "/insert03").accept(MediaType.APPLICATION_JSON_UTF8))
				// HTTP协议的状态码
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void tranAOPTestCase4() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(API_PREFIX + "/insert04").accept(MediaType.APPLICATION_JSON_UTF8))
				// HTTP协议的状态码
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void tranAOPTestCase5() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(API_PREFIX + "/insert05").accept(MediaType.APPLICATION_JSON_UTF8))
				// HTTP协议的状态码
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

}
