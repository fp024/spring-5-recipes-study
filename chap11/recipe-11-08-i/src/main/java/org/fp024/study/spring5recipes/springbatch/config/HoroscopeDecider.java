package org.fp024.study.spring5recipes.springbatch.config;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

@Slf4j
public class HoroscopeDecider implements JobExecutionDecider {
  private final Random random = new Random();

  private boolean isMercuryIsInRetrograde() {
    return random.nextBoolean();
  }

  @Override
  public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
    LOGGER.info("### 🎈 decide ###");
    if (isMercuryIsInRetrograde()) {
      LOGGER.info("### 🎈 MERCURY_IN_RETROGRADE ###");
      return new FlowExecutionStatus("MERCURY_IN_RETROGRADE");
    }

    return FlowExecutionStatus.COMPLETED;
  }
}
