package com.knubisoft;

public abstract class ErrorHelper {
    public void raise(String msg){
        raise(true, msg);
    }

    public void raise(boolean flag, String msg){
        if(flag){
            throw new MatchException(msg);
        }
    }
}
