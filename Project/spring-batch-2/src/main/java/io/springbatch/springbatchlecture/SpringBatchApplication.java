package io.springbatch.springbatchlecture;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing // 스프링 배치가 작동하기 위해 선언해야 하는 어노테이션
/*
    총 4개의 설정 클래스를 실행시키며 스프링 배치의 모든 초기화 및 실행 구성이 이루어짐
    스프링 부트 배치의 자동 설정 클래스가 실행됨으로 빈으로 등록된 모든 Job을 검색해서 초기화와 동시에 Job을 수행하도록 구성됨
 */

/*
    스프링 배치 초기화 설정 클래스
    1. SimpleBatchConfiguration
    2. BatchConfigurerConfiguration
        2-1 BasicBatchConfigurer
        2-2 JpaBatchConfigurer 
    3. BatchAutoConfiguration
        : Job을 수행하는 빈을 생성하기 때문에 가장 마지막

 */
public class SpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

}
