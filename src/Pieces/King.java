/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.Board;
import Board.Tile;
import Board.Pair;
import Enums.MoveType;
import Enums.PieceType;
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
    public Vector<Pair> specialMoves(Board board, Piece p) {
        //A special move with the king known as castling is allowed only once per player, per game
        //The king and rook involved in castling must not have previously moved;
        //There must be no pieces between the king and the rook;
        //The king may not currently be in check, nor may the king pass through or end up in a square that is under attack by an enemy piece 
        //(though the rook is permitted to be under attack and to pass over an attacked square);
        //The king and the rook must be on the same rank
        
        Vector<Pair> specialMoves = new Vector<>(); //The king special castling moves
        
        Pair position = this.getCurrentPosition();  //Initialize Pair object with current position
        final int row = position.getRow();          //Initialize variable to hold row position
        final int column = position.getColumn();    //Initialize variable to hold column position
        boolean pos1 = false;                       //Boolean for testing castlePos1
        boolean pos2 = false;                       //Boolean for testing castlePos2
        
        
        //Tiles for castle positions to the left and right of initial King square
        Tile castlePos1 = board.getTile(new Pair(row, column - 4));
        Tile castlePos2 = board.getTile(new Pair(row, column + 3));
        
        //The king special castling moves
        if(this.hasTakenFirstMove()){
            throw new UnsupportedOperationException("King has taken first move. can't castle");
        }

        //Tests if LongSide castle is still in castling position, and hasn't taken first move
        if(castlePos1.isOccupied()){
            if(castlePos1.getPiece().getClass() == Rook.class){
                if(castlePos1.getPiece().hasTakenFirstMove() == false){
                    pos1 = true;
                }
            } 
        }

        //Tests if shortside castle is still in castling position, and hasn't taken first move
        if(castlePos2.isOccupied()){
            if(castlePos2.getPiece().getClass() == Rook.class){
                if(castlePos2.getPiece().hasTakenFirstMove() == false){
                    pos2 = true;
                }
            } 
        }
       
       //if Rook's available to castle longside, test if spaces are empty between rook and king
       //Note: ***This is where if king would pass through check needs to be checked for***
       if (pos1){
           for(int i = 1; (canMoveTo(board.getTile(new Pair(row, column - i)), MoveType.EmptyTileOnly)) && (i < 4); i++){
               //If all squares are empty add last square to possible move set
               if (i == 3){
                   specialMoves.add(board.getTile(new Pair(row, column - i)).getPosition());
               }
           }
       }
       
       //if Rook's available to castle shortside, test if spaces are empty between rook and king
       //Note: ***This is where if king would pass through check needs to be checked for***
       if (pos1){
           for(int i = 1; (canMoveTo(board.getTile(new Pair(row, column + i)), MoveType.EmptyTileOnly)) && (i < 3); i++){
               //If all squares are empty add last square to possible move set
               if (i == 2){
                   specialMoves.add(board.getTile(new Pair(row, column + i)).getPosition());
               }
           }
       }
    
       
        
       //return special moves vector
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
