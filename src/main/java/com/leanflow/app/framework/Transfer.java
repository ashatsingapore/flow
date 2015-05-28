package com.leanflow.app.framework;

import com.leanflow.app.framework.conditions.Conditional;
import com.leanflow.app.framework.conditions.TransferType;

import java.util.List;
import java.util.ArrayList;

/**
 * Transfer class to define the transition between the steps
 * Created by ash on 28/5/15.
 */
public class Transfer {

    public String id;
    public TransferType type;
    public List<Conditional> conditional = new ArrayList<Conditional>();

    public String getId() {
        return id;
    }

    public TransferType getType() {
        return type;
    }

    public List<Conditional> getConditional() {
        return conditional;
    }
}
