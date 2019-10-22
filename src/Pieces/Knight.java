/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.Board;
import Board.Pair;
import Board.Tile;
import Enums.MoveType;
import Enums.PieceType;
import java.util.Vector;
import Player.Player;

/**
 *
 * @author jonathanjoiner
 */
public class Knight extends Piece{
    
    public Knight(Player owner){
        super(owner, PieceType.Knight);
    }
    
     @Override
    public Vector<Pair> specialMoves(Board board) {
        Vector<Pair> moves = new Vector<>();
        
        return moves;
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        Vector<Pair> moves = new Vector<>();                //Store all possible moves to return
       
        Pair position = this.getCurrentPosition();  //Initialize Pair object with current position
        final int row = position.getRow();          //Initialize variable to hold row position
        final int column = position.getColumn();    //Initialize variable to hold column position
        
        //Tiles for 8 squares around the knight to test for possible moves
        // **naming convention <1st direction in respect to orientation of the piece><2nd Direction>
        Tile upLeft = board.getTile(new Pair(row - 2, column - 1));
        Tile upRight = board.getTile(new Pair(row - 2, column + 1));
        Tile downLeft = board.getTile(new Pair(row + 2, column - 1));
        Tile downRight = board.getTile(new Pair(row + 2, column + 1));
        Tile leftUp = board.getTile(new Pair(row - 1, column - 2));
        Tile leftDown = board.getTile(new Pair(row + 1, column - 2));
        Tile rightUp = board.getTile(new Pair(row - 1, column + 2));
        Tile rightDown = board.getTile(new Pair(row + 1, column + 2));
        
        //Test which squares the knight can move to
        if(canMoveTo(upLeft, MoveType.EmptyOrEnemyPiece)) { 
            moves.add(upLeft.getPosition());      //Add upLeft to possible move set
        }
        if(canMoveTo(upRight, MoveType.EmptyOrEnemyPiece)){
            moves.add(upRight.getPosition());      //Add upRight to possible move set
        }
        if(canMoveTo(downLeft, MoveType.EmptyOrEnemyPiece)){
            moves.add(downLeft.getPosition());       //Add downLeft to possible move set
        }
        if(canMoveTo(downRight, MoveType.EmptyOrEnemyPiece)){
            moves.add(downRight.getPosition());       //Add downRight to possible move set
        }
        if(canMoveTo(leftUp, MoveType.EmptyOrEnemyPiece)){
            moves.add(leftUp.getPosition());    //Add leftUp to possible move set
        }
        if(canMoveTo(leftDown, MoveType.EmptyOrEnemyPiece)){
            moves.add(leftDown.getPosition());     //Add leftDown to possible move set
        }
        if(canMoveTo(rightUp, MoveType.EmptyOrEnemyPiece)){
            moves.add(rightUp.getPosition());     //Add rightUp to possible move set
        }
        if(canMoveTo(rightDown, MoveType.EmptyOrEnemyPiece)){
            moves.add(rightDown.getPosition());      //Add rightDown to possible move set
        }
        
       //Return possible move set
       return moves;
    }
}
