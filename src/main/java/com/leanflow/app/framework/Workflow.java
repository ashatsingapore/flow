package com.leanflow.app.framework;

import java.util.LinkedList;
import java.util.List;

/**
 * Workflow class to capture the process flow
 * Created by ash on 28/5/15.
 */
public class Workflow {

    public String id;
    public List<Step> steps = new LinkedList<>();

    public String getId() {
        return id;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
