package com.leanflow.app.framework.conditions;

import com.leanflow.app.framework.Step;

import java.util.HashMap;
import java.util.Map;

/**
 * Conditions to route context based on decisions from configuration
 * Created by ash on 28/5/15.
 */
public class Conditional {
    public enum Options {
        YES, NO, DEFAULT;
    }
    public String id;
    public Map<String, Step> conditionalClass = new HashMap<String, Step>();
    public boolean end;

    public String getId() {
        return id;
    }

    public Map<String, Step> getConditionalClass() {
        return conditionalClass;
    }

    public boolean isEnd() {
        return end;
    }
}
