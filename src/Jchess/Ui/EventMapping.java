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
    
    public interface TileDelegate 
    {
        public void didClick(Tile tile);
    }
    
    public interface StartDelegate
    {
        public void didStart(String FEN, ThemeType theme);
    }
}
