/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import Board.Board;
import Board.Check;
import Enums.ThemeColor;
import Board.Pair;
import Board.Tile;
import Controller.Constants;

/**
 * Class that contains the player logic
 * @author nehalpatel
 */
public final class Player {
    private final String name;
    private final ThemeColor color;
    private final int homeRow;
    private final int pawnRow;
    private Pair kingPair;
    
    public Player(String name, ThemeColor color) {
        this.name = name;
        this.color = color;
        this.homeRow = this.getColor() == ThemeColor.DarkPiece ? Constants.HOME_ROW_DARK : Constants.HOME_ROW_LIGHT;
        this.pawnRow = this.getColor() == ThemeColor.DarkPiece ? Constants.PAWN_ROW_DARK : Constants.PAWN_ROW_LIGHT;
    }

    /**
     * Returns the Player's name.
     * @return 
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the Player's color.
     * @return 
     */
    public ThemeColor getColor() {
        return this.color;
    }
    
    /**
     * Returns the location of Player's King.
     * @return 
     */
    public Pair getLocationOfKing() {
        return this.kingPair;
    }
    
    /**
     * Updates the location of Player's King.
     * @param king 
     */
    public void setLocationOfKing(Pair king){
        this.kingPair = king;
    }

    /**
     * Returns an array of Tiles found in the Player's home row.
     * @param board The game board.
     * @return The array of home row Tiles.
     */
    public Tile[] getHomeRow(Board board) {
        return board.getMatrix()[homeRow];
    }
    
    /**
     * Get the home row rank
     * @return 
     */
    public int getHomeRow() {
        return this.homeRow;
    }
    
    /**
     * Get the Pawn row rank.
     * @return Player's Pawn row index.
     */
    public int getPawnRow() {
        return this.pawnRow;
    }

    /**
     * Determines if the Player's King can be saved by an ally's move.
     * @param board
     * @return 
     */
    public boolean canKingBeSaved(Board board) {
        return Check.kingCanBeSaved(board);
    }

    /**
     * Determines if the Player's King is in the line of attack of any enemy Piece.
     * @param board
     * @return
     */
    public boolean isKingUnderAttack(Board board) {
        Player enemy;

        if (this.getColor() == ThemeColor.LightPiece) {
            enemy = board.getDarkPlayer();
        } else {
            enemy = board.getLightPlayer();
        }

        return Check.pairUnderAttack(this.getLocationOfKing(), board, enemy);
    }

    /**
     * Determines if the Player's Pieces' list of possible moves contain the given Tile.
     * @param tile The Tile to search for.
     * @param gameBoard The game board.
     * @return
     */
    public boolean canAttack(Tile tile, Board gameBoard) {
        return Check.pairUnderAttack(tile.getPosition(), gameBoard, this);
    }
}
