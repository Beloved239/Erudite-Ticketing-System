package com.tobi.Erudite_Event_System.users.service;

import jakarta.validation.constraints.Email;

public class Example2 extends Example{
    public Example2(@Email String name) {
        super(name);
    }

    public Example2(Example ib) {
        super(ib);

    }


    private Example example;

}
