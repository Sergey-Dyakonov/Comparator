package com.knubisoft;

public abstract class Mode {
    void onStrict(){
        if(this == Strict){

        }
    };
}

abstract class Strict extends Mode{}
abstract class Lenient extends Mode{}
