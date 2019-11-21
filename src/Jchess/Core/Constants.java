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

    /**
     * Prevent instantiation.
     */
    private Constants() { }
    
    /**
     * The preference key used to refer to the light Player's name.
     */
    public static final String LIGHT_PLAYER_KEY = "1";

    /**
     * The preference key used to refer to the dark Player's name.
     */
    public static final String DARK_PLAYER_KEY = "2";

    /**
     * The preference key used to refer to user's theme preference.
     */
    public static final String THEME_KEY = "3";

    /**
     * The internal identifier for the Normal theme.
     */
    public static final String NORMAL_THEME = "normal";

    /**
     * The internal identifier for the Doki Doki theme.
     */
    public static final String DOKI_THEME = "doki";

    /**
     * The internal identifier for the default theme.
     */
    public static final String DEFAULT_THEME = NORMAL_THEME;

    /**
     * The index for the dark player's King row.
     */
    public static final int HOME_ROW_DARK = 0;

    /**
     * The index for the dark player's Pawn row.
     */
    public static final int PAWN_ROW_DARK = 1;

    /**
     * The index for the light player's King row.
     */
    public static final int HOME_ROW_LIGHT = 7;

    /**
     * The index for the light player's Pawn row.
     */
    public static final int PAWN_ROW_LIGHT = 6;

    /**
     * The index for the Queen-side Rook's column.
     */
    public static final int ROOK_COLUMN_QUEENSIDE = 0;

    /**
     * The index for the King-side Rook's column.
     */
    public static final int ROOK_COLUMN_KINGSIDE = 7;
    
    /**
     *  The index for the default position for the King.
     */

    public static final int KING_COLUMN = 4;
    
    /**
     * The default name used for the dark Player.
     */
    public static final String DEFAULT_DARK_PLAYER_NAME = "Father Coleman";

    /**
     * The default name used for the light Player.
     */
    public static final String DEFAULT_LIGHT_PLAYER_NAME = "Sith Rochowiak";

    /**
     * The count of rows on the Board.
     */
    public static final int ROW_COUNT = 8;

    /**
     * The count of columns on the Board.
     */
    public static final int COLUMN_COUNT = 8;

    /**
     * The initial width for BoardFrame.
     */
    public static final int SMALL_DIMENSION = 100;

    /**
     * The initial height for BoardFrame.
     */
    public static final int LARGE_DIMENSION = 400;

    /**
     * The initial position horizontal position for the divider.
     */
    public static final int DIVIDER_LOCATION = 900;

    /**
     * The initial height and width of each Tile.
     */
    public static final int TILE_DIMENSION = 90;

    /**
     * The amount of milliseconds toast messages should be displayed.
     */
    public static final int TOAST_LIFESPAN = 2000;

    /**
     * The DOKI Chess club URL
     */
    public static final String DOKI_URL = "https://doki-doki-literature-club.fandom.com/wiki/Doki_Doki_Literature_Club!";

    /**
     * A small textual description to appear over the DOKI help button
     */
    public static final String DOKI_BLURB = "Navigate to the Doki Doki Literature site to understand our doki theme";

    /**
     * The How to Play Chess URL
     */
    public static final String CHESS_URL = "https://www.instructables.com/id/Playing-Chess/";

    /**
     * A small textual description to appear over the CHESS help button
     */
    public static final String CHESS_BLURB = "Navigate to a helpful Chess informational page";

    /*
     * URL to reference material explaining how to interpret FENs
     */
    public static final String FEN_URL = "https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation";

    /**
     * A small textual description to appear over the FEN help button
     */
    public static final String FEN_BLURB = "Navigate to a documentation page to read about FEN notation";

}
