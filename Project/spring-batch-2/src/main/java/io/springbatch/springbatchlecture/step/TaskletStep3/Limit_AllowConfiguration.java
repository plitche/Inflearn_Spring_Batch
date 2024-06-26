package io.springbatch.springbatchlecture.step.TaskletStep3;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// @Configuration
@RequiredArgsConstructor
public class Limit_AllowConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    // @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("batchJob")
                .start(step1())
                .next(step2())
                .build();
    }

    // @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("stepContribution = " + stepContribution + ", chunkContext = " + chunkContext);
                    return RepeatStatus.FINISHED;
                }))
                .allowStartIfComplete(true)
                .build();
    }

    // @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("stepContribution = " + stepContribution + ", chunkContext = " + chunkContext);
                    throw new RuntimeException("step2 was failed");
//                    return RepeatStatus.FINISHED;
                }))
                .startLimit(3)
                .build();
    }
}
