/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Ui;

import Jchess.Enums.ThemeType;
import Jchess.Models.Tile;

/**
 * Represents a Viewer ➜ Controller event mapping.
 * @author nehalpatel
 */
public class EventMapping {
    
    /**
     * Overrides Tile click handlers.
     */
    public interface TileDelegate 
    {

        /**
         * Handles Tile click event.
         * @param tile
         */
        public void didClick(Tile tile);
    }
    
    /**
     * Overrides Start click handler.
     */
    public interface StartDelegate
    {

        /**
         * Handles Start button click event.
         * @param FEN
         * @param theme
         */
        public void didStart(String FEN, ThemeType theme);
    }
}
