package com.example.jeffrey.vakantieapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jeffrey on 13-6-2017.
 */

public class VakantieItem implements Serializable {

    public String name;
    public boolean compulsorydates;
    public ArrayList<Tijdvlak> tijdvlak = new ArrayList<>();

    public VakantieItem(String name) {
        this.name = name;
    }
}
