package Hx.HxServer.util;

public interface ITimerManager {
    public void addTimerNode(AbstractTimerNode timerNode);

    public void removeTimerNode(AbstractTimerNode timerNode);

    public void tick();
}
