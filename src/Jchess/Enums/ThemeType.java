/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Enums;

/**
 * Represents the images that will be loaded
 * 
 * @author Jonathan Joiner
 */
public enum ThemeType {
    Normal("normal"),
    Doki("doki");

    private final String name;

    private ThemeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}