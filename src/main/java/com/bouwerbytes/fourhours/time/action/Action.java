package com.bouwerbytes.fourhours.time.action;

public abstract class Action {
    protected boolean valid;
    protected String data;
    
    public Action(String data) {
        this.valid = true;
        this.data = data;
    }
    
    /**
    * To be called when the Action needs to be executed.
    */
    public abstract void execute();

}
