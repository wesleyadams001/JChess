/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Models.Board;
import Jchess.Models.Factory;
import Jchess.Enums.ThemeColor;
import Jchess.Models.Pair;
import Jchess.Models.Tile;
import Jchess.Core.Constants;
import java.util.Vector;

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
        Player enemy = board.getEnemyPlayer();
        Tile[][] matrix = board.getMatrix();
        for ( int i = 0; i < Constants.ROW_COUNT ; i ++ ){
            for ( int j = 0; j < Constants.COLUMN_COUNT ; j ++ ){
                if (  matrix[i][j].isOccupied() && matrix[i][j].getPiece().getPlayer()!=enemy ){
                    Vector<Pair> allyMoves = matrix[i][j].getPiece().getPossibleMoves(board);
                    for ( int k = 0 ; k < allyMoves.size() ; k ++ ){
                        /*
                        move ally piece to pair @ allyMoves.get(k)
                        pass king location to pairUnderAttack(), see if it returns false
                        if false, king can be saved, else if true, keep going until false
                        */
                        Board temp = Factory.cloneBoard(board);
                        temp.movePiece(matrix[i][j].getPosition(), allyMoves.get(k));
                        
                        //check to see after piece has been moved if king is still under attack
                        if(!temp.pairUnderAttack(temp.getCurrentPlayer().getLocationOfKing(), temp.getEnemyPlayer())){
                            return true;
                        }
                        //if we get here that means king is not saved for that possible move and we need to reset the temp board back             
                    }    
                }
            }
        }
        return false;
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

        return board.pairUnderAttack(this.getLocationOfKing(), enemy);
    }

    /**
     * Determines if the Player's Pieces' list of possible moves contain the given Tile.
     * @param tile The Tile to search for.
     * @param gameBoard The game board.
     * @return
     */
    public boolean canAttack(Tile tile, Board gameBoard) {
        return gameBoard.pairUnderAttack(tile.getPosition(), this);
    }
}