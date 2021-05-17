package net.therap.quartzscheduling.model;

/**
 * @author adyel.ullahil
 * @since 5/17/21
 */
public class TimerInfo {

    private int totalFire;
    private int remainingFireCount;
    private boolean runForever;
    private long repeatInterval;
    private long initialOffsetMilliSecond;
    private String callbackData;

    public int getTotalFire() {
        return totalFire;
    }

    public void setTotalFire(int totalFire) {
        this.totalFire = totalFire;
    }

    public int getRemainingFireCount() {
        return remainingFireCount;
    }

    public void setRemainingFireCount(int remainingFireCount) {
        this.remainingFireCount = remainingFireCount;
    }

    public boolean isRunForever() {
        return runForever;
    }

    public void setRunForever(boolean runForever) {
        this.runForever = runForever;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public long getInitialOffsetMilliSecond() {
        return initialOffsetMilliSecond;
    }

    public void setInitialOffsetMilliSecond(long initialOffsetMilliSecond) {
        this.initialOffsetMilliSecond = initialOffsetMilliSecond;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }
}
