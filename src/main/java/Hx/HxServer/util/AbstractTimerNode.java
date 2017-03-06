package Hx.HxServer.util;

import Hx.HxServer.exception.XAssert;

public abstract class AbstractTimerNode {
    private final boolean m_isPeriod;
    private final long m_interval;
    private final long m_delay;

    private long m_nextTrigger = 0L;

    private boolean m_isRunning = true;// 增加一个是否运行的标志

    protected AbstractTimerNode(long delay, boolean isPeriod, long interval) {
        m_isPeriod = isPeriod;
        m_interval = interval;
        m_delay = delay;

        XAssert.check(delay >= 0, this);
    }

    long getDelay() {
        return m_delay;
    }

    long getNextTrigger() {
        return m_nextTrigger;
    }

    void setNextTrigger(long nextTrigger) {
        m_nextTrigger = nextTrigger;
    }

    boolean isPeriod() {
        return m_isPeriod;
    }

    long getInterval() {
        return m_interval;
    }

    public boolean IsRunning() {
        return m_isRunning;
    }

    public void setIsRunning(boolean m_isRunning) {
        this.m_isRunning = m_isRunning;
    }

    public abstract void onTimer();

    @Override
    public String toString() {
        return m_isPeriod + "," + m_interval + "," + m_nextTrigger;
    }
}


