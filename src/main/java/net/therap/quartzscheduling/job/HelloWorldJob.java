package net.therap.quartzscheduling.job;

import net.therap.quartzscheduling.model.TimerInfo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author adyel.ullahil
 * @since 5/17/21
 */
public class HelloWorldJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldJob.class);

    @Override
    public void execute(JobExecutionContext context) {

        TimerInfo timerInfo = (TimerInfo) context.getJobDetail().getJobDataMap().get(HelloWorldJob.class.getSimpleName());
        LOG.info(timerInfo.getCallbackData());
    }
}