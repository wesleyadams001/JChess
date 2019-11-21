/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Ui;

import Jchess.Models.Tile;

/**
 * Represents a Viewer ➜ Controller event mapping.
 * @author nehalpatel
 */
public class EventMapping {
    
    /**
     * Overrides Tile click handlers.
     */
    public interface ViewerDelegate 
    {

        /**
         * Handles Tile click event.
         * @param tile The Tile that was clicked.
         */
        public void didClickTile(Tile tile);
    }
    
    /**
     * Overrides Start click handler.
     */
    public interface StartMenuDelegate
    {

        /**
         * Handles Start button click event.
         * @param FEN The FEN that was loaded.
         */
        public void didClickStartButton(String FEN);
    }
}
