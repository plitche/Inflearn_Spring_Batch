package io.springbatch.springbatchlecture;

/*
    1. @Configuration 선언 : 하나의 배치 Job 을 정의하고 빈 설정
    (Job 정의 : Job 을 실행할 수 있는 각각의 내용들을 구성한다.)
    2. JobBuilderFactory
    3. StepBuilderFactory
    4. Job
    5. Step
    6. Tasklet : Step 안에서 단일 태스크로 수행되는 로직 구현
    7. Job 구동 > Step 을 실행 - Tasklet 을 실행

    Job : 일, 일감(여러 단계의 항목을 구성, Job의 모음)
    Step : 일의 항목, 단계(하나의 단위)
    Tasklet : 작업 내용
 */

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                .start(helloStep1())
                .next(helloStep2())
                .build();
    }

    @Bean
    public Step helloStep1() {
        return stepBuilderFactory.get("helloStep1")
               .tasklet(new Tasklet() {
                   @Override
                   public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                       System.out.println(" =======================");
                       System.out.println(" >> Hello Spring Batch!!");
                       System.out.println(" =======================");

                       return RepeatStatus.FINISHED; // 한번 실행하고 종료
                   }
               })
            .build();
    }

    @Bean
    public Step helloStep2() {
        return stepBuilderFactory.get("helloStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(" =======================");
                        System.out.println(" >> step2 was executed!!");
                        System.out.println(" =======================");

                        return RepeatStatus.FINISHED; // 한번 실행하고 종료
                    }
                })
                .build();
    }

}
