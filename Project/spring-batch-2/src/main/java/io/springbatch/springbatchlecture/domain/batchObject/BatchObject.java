package io.springbatch.springbatchlecture.domain.batchObject;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Collection;

// @Configuration
@RequiredArgsConstructor
public class BatchObject {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    // @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("test-job")
                .start(step1())
                .next(step2())
                .build();
    }

    // @Bean
    public Step step1() {
        return stepBuilderFactory.get("test-step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        StepExecution stepExecution = stepContribution.getStepExecution(); // Step 에 대한 한번의 시도를 의미하는 객체

                        JobParameters jobParameters = stepExecution.getJobParameters(); // Job을 실행할 떄 함께 포함되어 사용되는 파라미터를 가진 도메인 객체
                        JobExecution jobExecution = stepExecution.getJobExecution(); // JobInstance 에 대한 한번의 시도를 의미하는 객체

                        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions(); // Job에 포함되어 있는 stepExecution Collection
                        for (StepExecution execution : stepExecutions) {
                            execution.getJobExecution();
                            execution.getExitStatus();
                            execution.getStatus();
                            execution.getJobParameters();
                            // ...
                        }

                        JobInstance jobInstance = jobExecution.getJobInstance(); // Job이 실행될 때 생성되는 Job의 논리적 실행 단위 객체
                        String jobName = jobInstance.getJobName(); // test-job

                        ExecutionContext executionContext = jobExecution.getExecutionContext(); // 프레임워크에서 유지 및 관리하는 키/값으로 된 컬렉션으로 StepExecution 또는 JobExecution 객체의 상태를 저장하는 공유 객체
                        executionContext.get("jobName"); // ??

                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    // @Bean
    public Step step2() {
        return stepBuilderFactory.get("test-step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        StepContext stepContext = chunkContext.getStepContext();
                        StepExecution stepExecution = stepContext.getStepExecution();
                        JobExecution jobExecution = stepExecution.getJobExecution();
                        JobInstance jobInstance = jobExecution.getJobInstance();

                        String jobName = jobInstance.getJobName();
                        String stepName = stepExecution.getStepName();

                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}
