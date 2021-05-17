package net.therap.quartzscheduling.util;

import net.therap.quartzscheduling.model.TimerInfo;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.Date;

/**
 * @author adyel.ullahil
 * @since 5/17/21
 */
public class TimerUtils {

    private TimerUtils(){}

    public static JobDetail buildJobDetails(final Class jobClazz, final TimerInfo timerInfo){
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClazz.getSimpleName(), timerInfo);
        return JobBuilder
                .newJob(jobClazz)
                .withIdentity(jobClazz.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }

    public static Trigger buildTrigger(final Class jobClazz, final TimerInfo timerInfo){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(timerInfo.getRepeatInterval());

        if (timerInfo.isRunForever()) {
            scheduleBuilder.repeatForever();
        } else {
            scheduleBuilder.withRepeatCount(timerInfo.getTotalFire() - 1); // fires extra time
        }

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClazz.getSimpleName())
                .withSchedule(scheduleBuilder)
                .startAt(new Date(System.currentTimeMillis() + timerInfo.getInitialOffsetMilliSecond()))
                .build();
    }
}