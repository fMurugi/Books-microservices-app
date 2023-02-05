package com.fiona.Booksservice;

import com.fiona.Booksservice.dto.BookRequest;
import com.fiona.Booksservice.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration
class BooksServiceApplicationTests {
	@Container
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:5.7");

	private BookRepository bookRepository;
	@Autowired
	private MockMvc mockMvc;
//	@Autowired
//	private ObjectMapper objectMapper;

	@DynamicPropertySource // db url dynamically
	static  void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
//		dynamicPropertyRegistry.add("spring.datasource.url",mySQLContainer::);
		dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
		dynamicPropertyRegistry.add("spring.datasource.driver-class-name",mySQLContainer::getDriverClassName);
//		spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

	}

	@Test
	void createBook() throws Exception{
		BookRequest bookRequest =  getBookRequest();
//		String bookRequestString=objectMapper.writeValueAsString(bookRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(String.valueOf(bookRequest)))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1,bookRepository.findAll().size());

	}

	private BookRequest getBookRequest(){
		return BookRequest.builder()
				.name("Introduction to programming")
				.description("intro to coding")
				.price(20.0)
				.build();

	}


}

