package io.springbatch.springbatchlecture.Job.preventRestart;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// @Configuration
@RequiredArgsConstructor
public class ValidatorConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    // @Bean
    public Job job() {
        return this.jobBuilderFactory.get("job1")
                .start(step1())
                .next(step2())
                .preventRestart() // job이 실패하더라도 재시작 하면 안되는 경우 사용
                .build();
    }

    // @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step1 was executed");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    // @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        throw new RuntimeException("step2 was filed");

                        // System.out.println("step2 was executed");
                        // return RepeatStatus.FINISHED;
                    }
                }).build();
    }

}
