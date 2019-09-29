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
import java.util.Vector;
import Player.Player;
import static Images.Images.bishopIcons;

/**
 *
 * @author jonathanjoiner
 */
public class Bishop extends Piece{
    
    public Bishop(Player owner){
        super(owner);
        image = bishopIcons.get(owner.getColor());
    }
    
    @Override
    public Vector<Pair> specialMoves(Board board, Piece p) {
        throw new UnsupportedOperationException("Castling handled in King class"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        Vector<Pair> moves = new Vector<>();                //Store all possible moves to return
        Vector<Tile> upLeftDiagonal = new Vector<>();       //Store all tiles up and to the left in a diagonal line
        Vector<Tile> upRightDiagonal = new Vector<>();      //Store all tiles up and to the right in a diagonal line
        Vector<Tile> downLeftDiagonal = new Vector<>();     //Store all tiles down and to the left in a diagonal line
        Vector<Tile> downRightDiagonal = new Vector<>();    //Store all tiles down and to the right in a diagonal line
        
        Pair position = this.getCurrentPosition();  //Initialize Pair object with current position
        final int row = position.getRow();          //Initialize variable to hold row position
        final int column = position.getColumn();    //Initialize variable to hold column position
        
        //Fill upLeftDiagonal until outside matrix bounds
        for(int i = 1; (board.getTile(new Pair(row - i, column - i)) != null); i++){
            upLeftDiagonal.add(board.getTile(new Pair(row - i, column - i)));
        }
        
        //Fill upRightDiagonal until outside matrix bounds
        for(int i = 1; (board.getTile(new Pair(row - i, column + i)) != null); i++){
            upRightDiagonal.add(board.getTile(new Pair(row - i, column + i)));
        }
        
        //Fill downLeftDiagonal until outside matrix bounds
        for(int i = 1; (board.getTile(new Pair(row + i, column - i)) != null); i++){
            downLeftDiagonal.add(board.getTile(new Pair(row + i, column - i)));
        }
        
        //Fill downRightDiagonal until outside matrix bounds
        for(int i = 1; (board.getTile(new Pair(row + i, column + i)) != null); i++){
            downRightDiagonal.add(board.getTile(new Pair(row + i, column + i)));
        }
        
        
        
        //Add possible in upLeftDiagonal
        for(int i = 0; canMoveTo(upLeftDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && i < upLeftDiagonal.size(); i++){
            moves.add(upLeftDiagonal.get(i).getPosition());
        }
        
        //Add possible moves in upRightDiagonal
        for(int i = 0; canMoveTo(upRightDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && i < upRightDiagonal.size(); i++){
            moves.add(upRightDiagonal.get(i).getPosition());
        }
        
        //Add possible moves in downLeftDiagonal
        for(int i = 0; canMoveTo(downLeftDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && i < downLeftDiagonal.size(); i++){
            moves.add(downLeftDiagonal.get(i).getPosition());
        }
        
        //Add possible moves in downRightDiagonal
        for(int i = 0; canMoveTo(downRightDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && i < downRightDiagonal.size(); i++){
            moves.add(downRightDiagonal.get(i).getPosition());
        }

        //Return moves
        return moves;
    }

}
