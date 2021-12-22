package com.bouwerbytes.fourhours.time.action.actions;

import com.bouwerbytes.fourhours.FourHours;
import com.bouwerbytes.fourhours.time.Whitelist;
import com.bouwerbytes.fourhours.time.action.Action;

public class ActionWhitelist extends Action {

    private Whitelist whitelist;
    
    public ActionWhitelist(FourHours plugin) {
        super("");
        this.whitelist = plugin.whitelist();
    }
    
    @Override
    public void execute() {
        whitelist.toggle();
    }
}
