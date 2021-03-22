package com.github.brunomarq.searchcli;

import com.github.brunomarq.searchcli.controller.SearchCLI;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class EntrypointTests {

	@Autowired
	private SearchCLI controller;

	@Test
	void contextLoads() throws Exception {
		Assertions.assertThat(controller).isNotNull();
	}

}
