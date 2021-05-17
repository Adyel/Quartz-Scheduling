package net.therap.quartzscheduling.service;

import net.therap.quartzscheduling.model.TimerInfo;
import net.therap.quartzscheduling.trigger.TimerTriggerListener;
import net.therap.quartzscheduling.util.TimerUtils;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author adyel.ullahil
 * @since 5/17/21
 */
@Service
public class SchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    protected Scheduler scheduler;

    @PostConstruct
    public void initialize() {
        try {
            scheduler.start();
            scheduler.getListenerManager().addTriggerListener(new TimerTriggerListener(this));
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    private void preDestroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void schedule(final Class jobClazz, final TimerInfo timerInfo) {
        final JobDetail jobDetail = TimerUtils.buildJobDetails(jobClazz, timerInfo);
        final Trigger trigger = TimerUtils.buildTrigger(jobClazz, timerInfo);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public List<TimerInfo> getAllRunningTimerInfos() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup()).stream()
                    .map(jobKey -> {
                        try {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return (TimerInfo) jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (SchedulerException e) {
                            LOG.error(e.getMessage(), e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public TimerInfo getRunningTimer(String timerId) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            return Objects.nonNull(jobDetail) ? (TimerInfo) jobDetail.getJobDataMap().get(timerId) : null;
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    public void updateTimerInfo(final String timerId, final TimerInfo timerInfo){
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (Objects.nonNull(jobDetail)) {
                jobDetail.getJobDataMap().put(timerId, timerInfo);
            } else {
                LOG.error("Timer with id '{}' does not exist!", timerId);
            }
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public boolean deleteTimer(final String timerID){
        try {
            return scheduler.deleteJob(new JobKey(timerID));
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
}