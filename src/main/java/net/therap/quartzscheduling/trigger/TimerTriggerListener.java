package net.therap.quartzscheduling.trigger;

import net.therap.quartzscheduling.model.TimerInfo;
import net.therap.quartzscheduling.service.SchedulerService;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * @author adyel.ullahil
 * @since 5/17/21
 */
public class TimerTriggerListener implements TriggerListener {

    private final SchedulerService schedulerService;

    public TimerTriggerListener(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return TimerTriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        final String timerId = trigger.getKey().getName();
        final TimerInfo timerInfo = (TimerInfo) context.getJobDetail().getJobDataMap().get(timerId);

        if (!timerInfo.isRunForever()){
            int remainingCount = timerInfo.getRemainingFireCount();
            if (remainingCount == 0){
                return;
            }
            timerInfo.setRemainingFireCount(--remainingCount);
        }

        schedulerService.updateTimerInfo(timerId, timerInfo);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }
}
