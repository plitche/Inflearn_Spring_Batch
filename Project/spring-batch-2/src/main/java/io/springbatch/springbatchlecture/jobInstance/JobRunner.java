package io.springbatch.springbatchlecture.jobInstance;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

// @Component
public class JobRunner implements ApplicationRunner {

    @Autowired // 이미 Bean으로 생성된 객체이기 때문에 의존성 주입을 받아 사용
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception { // Spring boot가 초기화되고 완료가되면 가장 먼저 호출
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                // .addString("name", "user2")
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
