package net.therap.quartzscheduling.service;

import net.therap.quartzscheduling.job.HelloWorldJob;
import net.therap.quartzscheduling.model.TimerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author adyel.ullahil
 * @since 5/17/21
 */
@Service
public class HelloWorldScheduleService {

    @Autowired
    private SchedulerService schedulerService;

    public void runHelloWorld(){
        TimerInfo timerInfo = new TimerInfo();
        timerInfo.setTotalFire(5);
        timerInfo.setRemainingFireCount(timerInfo.getTotalFire());
        timerInfo.setRepeatInterval(5000);
        timerInfo.setInitialOffsetMilliSecond(1000);
        timerInfo.setCallbackData("Some Juicy Date");

        schedulerService.schedule(HelloWorldJob.class, timerInfo);
    }

    public List<TimerInfo> getAllRunningTimers(){
        return schedulerService.getAllRunningTimerInfos();
    }

    public TimerInfo getRunningTimer(String timerId) {
        return schedulerService.getRunningTimer(timerId);
    }

    public boolean deleteTimer(final String timerId){
        return schedulerService.deleteTimer(timerId);
    }
}
