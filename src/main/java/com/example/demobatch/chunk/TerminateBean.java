package com.example.demobatch.chunk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class TerminateBean {

	@Autowired
	ThreadPoolTaskExecutor taskExecutor;
	
    @PreDestroy
    public void onDestroy() throws Exception {
    	taskExecutor.shutdown();
    }
}