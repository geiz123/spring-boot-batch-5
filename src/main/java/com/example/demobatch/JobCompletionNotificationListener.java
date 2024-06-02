package com.example.demobatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * A job listener to initiate thread pool shutdown
 *
 */
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Autowired
    ThreadPoolTaskExecutor pool;
   
    @Override
    public void afterJob(@NonNull JobExecution jobExecution) {
    	
    	LOGGER.info("JobCompletionNotificationListener#afterJob - {}", jobExecution.toString());
    	
    	LOGGER.info("Shutting down pool...");
    	
    	pool.shutdown();
    	
    	LOGGER.info("Shutting down pool...DONE");
    }
}