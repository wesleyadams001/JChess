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

    /**
     * The default Chess theme.
     */
    Normal("normal"),

    /**
     * The special Anime theme.
     */
    Doki("doki");

    private final String name;

    private ThemeType(String name) {
        this.name = name;
    }

    /**
     * Returns a String representation of this theme. 
     * @return The theme as a String.
     */
    public String getName() {
        return name;
    }
}