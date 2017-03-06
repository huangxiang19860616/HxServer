package Hx.HxServer.core;

import Hx.HxServer.core.extension.impl.IRequest;

import java.util.Comparator;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */

public class RequestComparator implements Comparator<IRequest> {
    public RequestComparator() {
    }

    public int compare(IRequest r1, IRequest r2) {
        boolean res = false;
        byte res1;
        if(r1.getPriority().getValue() < r2.getPriority().getValue()) {
            res1 = -1;
        } else if(r1.getPriority() == r2.getPriority()) {
            if(r1.getTimeStamp() < r2.getTimeStamp()) {
                res1 = -1;
            } else if(r1.getTimeStamp() > r2.getTimeStamp()) {
                res1 = 1;
            } else {
                res1 = 0;
            }
        } else {
            res1 = 1;
        }

        return res1;
    }
}