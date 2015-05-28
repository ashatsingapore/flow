package com.leanflow.app.framework;

/**
 * Step class to capture the activity in a workflow
 *
 * Created by ash on 28/5/15.
 */
public class StepDefinition {

    public String id;
    public Transfer transfer;

    public String getId() {
        return id;
    }

    public Transfer getTransfer() {
        return transfer;
    }
}
