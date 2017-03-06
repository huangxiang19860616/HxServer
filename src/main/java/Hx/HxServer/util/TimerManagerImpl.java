package Hx.HxServer.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import Hx.HxServer.exception.XAssert;

public class TimerManagerImpl implements ITimerManager {
    private TreeMap<Long, LinkedList<AbstractTimerNode>> m_timerMap = new TreeMap<Long, LinkedList<AbstractTimerNode>>();

    private List<AbstractTimerNode> m_removeList = new ArrayList<AbstractTimerNode>();

    public TimerManagerImpl() {

    }

    private void insertTimerNode(AbstractTimerNode timerNode) {
        if (m_timerMap.containsKey(timerNode.getNextTrigger())) {
            m_timerMap.get(timerNode.getNextTrigger()).add(timerNode);
        } else {
            LinkedList<AbstractTimerNode> nodeSet = new LinkedList<AbstractTimerNode>();
            nodeSet.add(timerNode);

            m_timerMap.put(timerNode.getNextTrigger(), nodeSet);
        }
    }

    private void deleteTimerNode(AbstractTimerNode timerNode) {
        Long key = timerNode.getNextTrigger();

        if (m_timerMap.containsKey(key)) {
            m_timerMap.get(key).remove(timerNode);

            if (m_timerMap.get(key).size() == 0) {
                m_timerMap.remove(key);
            }
        } else {
            XAssert.checkContinue(false, timerNode);
        }
    }

    public void removeTimerNode(AbstractTimerNode timerNode) // this assume
    // timer node is
    // in list
    {
        timerNode.setIsRunning(false);
        m_removeList.add(timerNode);
    }

    public void tick() {
        // firstly, remove all invalid timer
        for (Iterator<AbstractTimerNode> iterator = m_removeList.iterator(); iterator.hasNext(); ) {
            AbstractTimerNode timerNode = (AbstractTimerNode) iterator.next();
            deleteTimerNode(timerNode);
        }
        m_removeList.clear();

        // get triggers
        long current = System.currentTimeMillis();
        List<LinkedList<AbstractTimerNode>> triggerList = new ArrayList<LinkedList<AbstractTimerNode>>();
        Set<Long> keySet = m_timerMap.keySet();
        for (Iterator<Long> iterator = keySet.iterator(); iterator.hasNext(); ) {
            Long triggerPoint = (Long) iterator.next();
            LinkedList<AbstractTimerNode> triggerSet = m_timerMap.get(triggerPoint);

            if (triggerPoint <= current) {
                triggerList.add(triggerSet);
                iterator.remove();
            } else {
                break;
            }
        }

        // on timer
        for (Iterator<LinkedList<AbstractTimerNode>> iterator = triggerList.iterator(); iterator.hasNext(); ) {
            LinkedList<AbstractTimerNode> triggerSet = iterator.next();
            for (Iterator<AbstractTimerNode> iterator2 = triggerSet.iterator(); iterator2.hasNext(); ) {
                AbstractTimerNode timerNode = (AbstractTimerNode) iterator2.next();

                try {
                    if (timerNode.IsRunning()) {
                        timerNode.onTimer();
                    }
                } catch (Exception e) {
                    XAssert.checkContinue(false, timerNode.getDelay(), timerNode.getInterval(), timerNode.getClass().getName(), e);
                } catch (Error e) {
                    XAssert.checkContinue(false, timerNode.getDelay(), timerNode.getInterval(), e);
                }

                if (timerNode.isPeriod()) {
                    timerNode.setNextTrigger(timerNode.getNextTrigger() + timerNode.getInterval());
                    insertTimerNode(timerNode);
                }
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();

        Set<Long> keySet = m_timerMap.keySet();
        for (Iterator<Long> iterator = keySet.iterator(); iterator.hasNext(); ) {
            Long triggerPoint = (Long) iterator.next();
            LinkedList<AbstractTimerNode> triggerSet = m_timerMap.get(triggerPoint);

            for (Iterator<AbstractTimerNode> iterator2 = triggerSet.iterator(); iterator2.hasNext(); ) {
                AbstractTimerNode timerNode = (AbstractTimerNode) iterator2.next();
                strBuilder.append("[" + timerNode.toString() + "]\n");
            }
        }

        return strBuilder.toString();
    }

    public void addTimerNode(AbstractTimerNode timerNode) {
        long current = System.currentTimeMillis();
        timerNode.setNextTrigger(current + timerNode.getDelay());
        insertTimerNode(timerNode);
    }
}

