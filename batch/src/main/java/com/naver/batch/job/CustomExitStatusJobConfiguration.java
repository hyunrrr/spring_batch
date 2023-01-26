package com.naver.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CustomExitStatusJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public CustomExitStatusJobConfiguration(
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job stepCustomConfigJob() {
        return jobBuilderFactory.get("stepCustomConfigJob")
                .start(customExitStatusStep1())
                    .on("FAILED")
                    .end()
                .from(customExitStatusStep1())
                    .on("COMPLETED WITH SKIPS")
                    .to(errorPrint1())
                    .on("*")
                    .end()
                .from(customExitStatusStep1())
                    .on("*")
                    .to(customExitStatuStep2())
                    .end()
                .build();
    }

    @Bean
    public Step customExitStatusStep1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> THis is stepCustomConfig step1");

                    contribution.setExitStatus(new ExitStatus("COMPLETED WITH SKIPS"));

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step errorPrint1() {
        return stepBuilderFactory.get("errorPrint1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> THis is stepCustomConfig errorPrint1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step customExitStatuStep2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> THis is stepCustomConfig step2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    public static class SkipCheckingListener extends StepExecutionListenerSupport {

        @Override
        public ExitStatus afterStep(StepExecution stepExecution) {
            String exitCode = stepExecution.getExitStatus().getExitCode();
            if (!exitCode.equals(ExitStatus.FAILED.getExitCode()) &&
                    stepExecution.getSkipCount() > 0) {
                return new ExitStatus("COMPLETED WITH SKIPS");
            } else {
                return null;
            }
        }
    }
}
