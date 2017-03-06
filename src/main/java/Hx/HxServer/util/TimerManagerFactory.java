package Hx.HxServer.util;

import java.util.HashMap;

public class TimerManagerFactory {
    private final static HashMap<String, ITimerManager> s_timerMgrMap = new HashMap<String, ITimerManager>();

    private TimerManagerFactory() {

    }

    public static ITimerManager getTimerManager(String name) {
        if (s_timerMgrMap.containsKey(name)) {
            return s_timerMgrMap.get(name);
        }

        ITimerManager tickMgr = new TimerManagerImpl();
        s_timerMgrMap.put(name, tickMgr);

        return tickMgr;
    }

}
