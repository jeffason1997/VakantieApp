package com.example.jeffrey.vakantieapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jeffrey on 13-6-2017.
 */

class VakantieItem implements Serializable {

    public String name;
    ArrayList<Tijdvlak> tijdvlak = new ArrayList<>();
}
