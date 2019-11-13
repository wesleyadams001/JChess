/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;
import java.util.prefs.*;

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
}

