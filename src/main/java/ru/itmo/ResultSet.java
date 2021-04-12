package ru.itmo;

import java.util.HashSet;

public class ResultSet {
    HashSet<String> result = new HashSet<>();
    public void add(String s){
        result.add(s);
    }
    public void print(){
        for(String s : result){
            System.out.println(s);
        }
    }
}
