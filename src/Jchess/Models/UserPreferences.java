/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import java.util.prefs.*;

import Jchess.Core.Constants;
import Jchess.Enums.ThemeType;

/**
 * Stores the user data
 * @author Wesley
 */
public class UserPreferences {
    
    /**
     * The default constructor of the user preferences to restrict instantiation
     */
    private UserPreferences() { }
    
    /**
     * Set a value in the java user preferences
     * @param key   Key name for preference.
     * @param value Value to be stored in key.
     */
    public static void setValue(String key, String value){
        Preferences.userNodeForPackage(UserPreferences.class).put(key, value);
    }
    
    /**
     * Get a value from the java user preferences
     * @param key   Key name for preference.
     * @param defVal    Default value to be grabbed.
     * @return  Preference at key location as a string.
     */
    public static String getValue(String key, String defVal){
        return Preferences.userNodeForPackage(UserPreferences.class).get(key,defVal);
    }

    /**
     * Get the saved light Player name from user preferences.
     * @return The saved name for the light Player.
     */
    public static String getLightPlayerName() {
        return UserPreferences.getValue(Constants.LIGHT_PLAYER_KEY, Constants.DEFAULT_LIGHT_PLAYER_NAME);
    }

    /**
     * Updates the light Player's name in user preferences.
     * @param name The light Player's name.
     */
    public static void setLightPlayerName(String name) {
        UserPreferences.setValue(Constants.LIGHT_PLAYER_KEY, name);
    }

    /**
     * Get the saved dark Player name from user preferences.
     * @return The saved name for the dark Player.
     */
    public static String getDarkPlayerName() {
        return UserPreferences.getValue(Constants.DARK_PLAYER_KEY, Constants.DEFAULT_DARK_PLAYER_NAME);
    }

    /**
     * Updates the dark Player's name in user preferences.
     * @param name The dark Player's name.
     */
    public static void setDarkPlayerName(String name) {
        UserPreferences.setValue(Constants.DARK_PLAYER_KEY, name);
    }

    /**
     * Get the preferred ThemeType from user preferences.
     * @return The preferred theme.
     */
    public static ThemeType getTheme() {
        String identifier = UserPreferences.getValue(Constants.THEME_KEY, Constants.DEFAULT_THEME);
        return Constants.NORMAL_THEME.equals(identifier) ? ThemeType.Normal : ThemeType.Doki;
    }

    /**
     * Updates the preferred theme in user preferences.
     * @param identifier A theme identifier.
     */
    public static void setTheme(String identifier) {
        UserPreferences.setValue(Constants.THEME_KEY, identifier);
    }
}

