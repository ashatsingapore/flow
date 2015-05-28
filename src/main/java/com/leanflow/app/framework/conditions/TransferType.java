package com.leanflow.app.framework.conditions;

/**
 * Types of transition between steps SEQ or PARALLEL
 * Created by ash on 28/5/15.
 */
public enum TransferType {
    SEQ(1), PRL(2);

    private int type;

    private TransferType(int type) {
        this.type = type;
    }
}
