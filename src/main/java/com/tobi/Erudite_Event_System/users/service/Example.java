package com.tobi.Erudite_Event_System.users.service;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class Example {
    @Email
    private final String name;
    public Example(Example ib){
        this.name = ib.name;
    }

    public static int area(){
        return 1*2;
    }

    public static String area(String name){
        return "Oluwatobi";
    }

    public static boolean area(String name, String location){
        return name.equals(location);
    }

    public void finalize(){

    }

    public void main(String[] args) {

    }
     public static  class Area{

     }
}
