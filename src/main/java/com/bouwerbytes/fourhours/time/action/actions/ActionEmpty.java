package com.bouwerbytes.fourhours.time.action.actions;

import com.bouwerbytes.fourhours.time.action.Action;

public class ActionEmpty extends Action {
    public ActionEmpty(String data) {
        super(data);
    }
    
    @Override
    public void execute() {
        // nothing to execute
    }
}

