/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.*;
import Enums.*;
import Player.Player;
import java.util.Vector;

/**
 * The King piece
 * @author Wesley
 */
public class King extends Piece{

    public King(Player owner) {
        super(owner, PieceType.King);
    }
    
    /**
     * Gets a vector pair containing 
     * @return vector of Pair objects 
     */
    @Override
    public Vector<Pair> specialMoves(Board board) {
        
        // CASTLE.EXE 
        
        //set the rooks
        int homeRow = board.getCurrentPlayer().getColor() == ThemeColor.DarkPiece ? 0 : 7;
        Piece rookLeft = board.getMatrix()[homeRow][0].getPiece();
        Piece rookRight = board.getMatrix()[homeRow][7].getPiece();
        
        boolean canCastleLeft = true;
        boolean canCastleRight = true;
        
        Vector<Pair> specialMoves = new Vector<>();
        
        Player enemy = board.getEnemyPlayer() == this.getPlayer() ? board.getCurrentPlayer() : board.getEnemyPlayer(); // used for pair under attack
        
        if(!Check.pairUnderAttack(this.getCurrentPosition(), board, enemy)){ // king can not be in check
            if(!this.hasTakenFirstMove()){ // king can not have previously moved
                if(rookLeft != null && !rookLeft.hasTakenFirstMove()){ // testing rook left move
                    for(int i = rookLeft.getCurrentPosition().getColumn()+1; i < this.getCurrentPosition().getColumn() ; i++){ //looping throw the rank starting at the rook and until the king
                        // making sure each pair until the king is not under attack
                        if (board.getMatrix()[homeRow][i].getPiece()!=null || Check.pairUnderAttack(board.getMatrix()[homeRow][i].getPosition(), board, enemy)) {
                            canCastleLeft = false;
                        }
                    }
                }else{
                    canCastleLeft = false;
                }
                if(rookRight != null && !rookRight.hasTakenFirstMove()){ //testing rook right move
                    for(int i = rookRight.getCurrentPosition().getColumn()-1; i > this.getCurrentPosition().getColumn() ; i--){
                        // making sure each pair until the king is not under attack
                        if (board.getMatrix()[homeRow][i].getPiece()!=null || Check.pairUnderAttack(board.getMatrix()[homeRow][i].getPosition(), board, enemy)) {
                            canCastleRight = false;
                        }
                    }
                }else{
                    canCastleRight = false;
                }
            }else{
                canCastleLeft = false; canCastleRight = false;
            }
        }else{
            canCastleLeft = false; canCastleRight = false;
        }
        // based of test values, add castle move
        if(canCastleLeft){
            specialMoves.add(new Pair(homeRow, rookLeft.getCurrentPosition().getColumn()+1));
        }
        if(canCastleRight){
            specialMoves.add(new Pair(homeRow, rookRight.getCurrentPosition().getColumn()-1));
        }
        
    
        
        return specialMoves;
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        //The king moves exactly one square horizontally, vertically, or diagonally. 
        Vector<Pair> moves = new Vector<>();
        
        Pair position = this.getCurrentPosition();  //Initialize Pair object with current position
        final int row = position.getRow();          //Initialize variable to hold row position
        final int column = position.getColumn();    //Initialize variable to hold column position
        
        //Tiles for 8 squares around the king to test for possible moves
        Tile oneAbove = board.getTile(new Pair(row - 1, column));
        Tile oneRight = board.getTile(new Pair(row, column + 1));
        Tile oneLeft = board.getTile(new Pair(row, column - 1));
        Tile oneDown = board.getTile(new Pair(row + 1, column));
        Tile aboveRight = board.getTile(new Pair(row - 1, column + 1));
        Tile aboveLeft = board.getTile(new Pair(row - 1, column - 1));
        Tile downRight = board.getTile(new Pair(row + 1, column + 1));
        Tile downLeft = board.getTile(new Pair(row + 1, column - 1));
        
        //Test which squares the king can move to
        if(canMoveTo(oneAbove, MoveType.EmptyOrEnemyPiece)) { 
            moves.add(oneAbove.getPosition());      //Add oneAbove to possible move set
        }
        if(canMoveTo(oneRight, MoveType.EmptyOrEnemyPiece)){
            moves.add(oneRight.getPosition());      //Add oneRight to possible move set
        }
        if(canMoveTo(oneLeft, MoveType.EmptyOrEnemyPiece)){
            moves.add(oneLeft.getPosition());       //Add oneLeft to possible move set
        }
        if(canMoveTo(oneDown, MoveType.EmptyOrEnemyPiece)){
            moves.add(oneDown.getPosition());       //Add oneDown to possible move set
        }
        if(canMoveTo(aboveRight, MoveType.EmptyOrEnemyPiece)){
            moves.add(aboveRight.getPosition());    //Add aboveRight to possible move set
        }
        if(canMoveTo(aboveLeft, MoveType.EmptyOrEnemyPiece)){
            moves.add(aboveLeft.getPosition());     //Add aboveLeft to possible move set
        }
        if(canMoveTo(downRight, MoveType.EmptyOrEnemyPiece)){
            moves.add(downRight.getPosition());     //Add downRight to possible move set
        }
        if(canMoveTo(downLeft, MoveType.EmptyOrEnemyPiece)){
            moves.add(downLeft.getPosition());      //Add downLeft to possible move set
        }
        
        //Return possible move set
        return moves;
    }




}
