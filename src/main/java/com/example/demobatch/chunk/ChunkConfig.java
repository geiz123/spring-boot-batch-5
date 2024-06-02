package com.example.demobatch.chunk;

import java.util.Collections;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demobatch.ABean;
import com.example.demobatch.ABeanRowMapper;
import com.example.demobatch.Coffee;
import com.example.demobatch.CoffeeItemProcessor;
import com.example.demobatch.JobCompletionNotificationListener;

@Configuration
public class ChunkConfig {
	// READER
	@Bean
    public ItemReader<ABean> asyncReader(@Qualifier("dataSource2") DataSource dataSource) {

        return new JdbcPagingItemReaderBuilder<ABean>()
                .name("asyncReader")
                .dataSource(dataSource)
                .selectClause("SELECT * ")
                .fromClause("FROM COFFEE ")
                .maxItemCount(5)
                .sortKeys(Collections.singletonMap("COFFEE_ID", Order.ASCENDING))
                .rowMapper(new ABeanRowMapper())
                .build();
    }

	// PROCESSOR
	@Bean
    public AsyncItemProcessor<ABean, Coffee> asyncProcessor() {
        AsyncItemProcessor<ABean, Coffee> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(new CoffeeItemProcessor());
        asyncItemProcessor.setTaskExecutor(taskExecutor());

        return asyncItemProcessor;
    }
	
	// WRITTER
	@Bean
    public FlatFileItemWriter<Coffee> itemWriter() {

        return new FlatFileItemWriterBuilder<Coffee>()
                .name("Writer")
                .append(false)
                .resource(new FileSystemResource("transactions.txt"))
                .lineAggregator(new DelimitedLineAggregator<Coffee>() {
                    {
                        setDelimiter(";");
                        setFieldExtractor(new BeanWrapperFieldExtractor<Coffee>() {
                            {
                                setNames(new String[]{"coffee_id", "brand", "origin", "characteristics"});
                            }
                        });
                    }
                })
                .build();
    }
	@Bean
    public AsyncItemWriter<Coffee> asyncWriter() {
        AsyncItemWriter<Coffee> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(itemWriter());
        return asyncItemWriter;
    }
	
	// Executor
	@Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(5);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-");
        return executor;
    }
	
	// JOB Setup
	@Bean
	public Job asyncJob(JobRepository jobRepo, Step step1, JobCompletionNotificationListener listener) {
		return new JobBuilder("asyncJob", jobRepo)
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}
	
	@Bean
	public Step asyncManagerStep(JobRepository jobRepo,
			PlatformTransactionManager platfromTransactionManager,
			ItemReader<ABean> asyncItemReader,
			AsyncItemProcessor<ABean, Coffee> asyncProcessor,
			AsyncItemWriter<Coffee> asyncWriter,
			ThreadPoolTaskExecutor taskExecutor) {
		return new StepBuilder("asyncManagerStep", jobRepo)
				.<ABean, Future<Coffee>> chunk(3, platfromTransactionManager)
				.reader(asyncItemReader)
				.processor(asyncProcessor)
				.writer(asyncWriter)
				.taskExecutor(taskExecutor)
				.build();
	}
	
	
}
