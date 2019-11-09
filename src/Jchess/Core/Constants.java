/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Core;

/**
 * File to maintain constants used throughout the JChess program
 * @author Wesley
 */
public final class Constants {

    //prevent instantiation
    private Constants() {
            
    }
    
    public static final String PLAYER_ONE_KEY = "1";
    public static final String PLAYER_TWO_KEY = "2";
    public static final String THEME_KEY = "3";
    public static final int HOME_ROW_DARK = 0;
    public static final int PAWN_ROW_DARK = 1;
    public static final int HOME_ROW_LIGHT = 7;
    public static final int PAWN_ROW_LIGHT = 6;
    public static final int ROOK_COLUMN_QUEENSIDE = 0;
    public static final int ROOK_COLUMN_KINGSIDE = 7;
    public static final String DARK_PLAYER = "Dark";
    public static final String LIGHT_PLAYER = "Light";
    public static final int ROW_COUNT = 8;
    public static final int COLUMN_COUNT = 8;
    public static final int SMALL_DIMENSION = 100;
    public static final int LARGE_DIMENSION = 400;
    public static final int DIVIDER_LOCATION = 900;
    public static final int TILE_DIMENSION = 90;
    
}