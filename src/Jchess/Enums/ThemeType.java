/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Enums;

import Jchess.Core.Constants;

/**
 * Represents the images that will be loaded.
 * @author Jonathan Joiner
 */
public enum ThemeType {

    /**
     * The default Chess theme.
     */
    Normal(Constants.NORMAL_THEME),

    /**
     * The special Anime theme.
     */
    Doki(Constants.DOKI_THEME);

    private final String name;

    /**
     * Constructor for the theme.
     * @param name The theme name.
     */
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