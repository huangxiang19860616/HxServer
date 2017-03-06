package Hx.HxServer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

public class TimeCheck {
    private static final Logger s_logger = LoggerFactory.getLogger(TimeCheck.class);
    private Calendar m_begCal = null;
    private Calendar m_endCal = null;

    static {

    }

    public TimeCheck() {
        m_begCal = Calendar.getInstance();
        m_endCal = Calendar.getInstance();
    }

    public void begCheck() {
        m_begCal = Calendar.getInstance();
        s_logger.info("m_begCal .. {}", m_begCal.getTime());
    }

    public void endCheck() {
        m_endCal = Calendar.getInstance();
        s_logger.info("m_endCal .. {}", m_endCal.getTime());
    }

    public void getTicks(String str) {
        s_logger.info("{} consume time: .. {}", str, m_endCal.getTimeInMillis() - m_begCal.getTimeInMillis());
    }
}
