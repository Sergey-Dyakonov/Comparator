package com.knubisoft;

public class ErrorHelper {
    public void raise(String msg){
        raise(true, msg);
    }

    public void raise(boolean flag, String msg){
        if(flag){
            throw new MatchException(msg);
        }
    }
}
