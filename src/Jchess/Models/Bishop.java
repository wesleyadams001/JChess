/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Models.*;
import Jchess.Models.Board;
import Jchess.Models.Pair;
import Jchess.Models.Tile;
import Jchess.Enums.MoveType;
import Jchess.Enums.PieceType;
import java.util.Vector;

/**
 * Class that holds the available moves for a bishop
 * @author jonathanjoiner
 */
public class Bishop extends Piece{
    
    public Bishop(Player owner){
        super(owner, PieceType.Bishop);
    }
    
    @Override
    public Vector<Pair> specialMoves(Board board) {
        Vector<Pair> moves = new Vector<>();
        
        return moves;
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
        
        boolean canMove = true;
        
        //Fill all vector diagonals around quenn until outside matrix bounds
        for(int i = 1; i < 8; i++){
            if((row - i) >= -1 && (column - i) >= -1){
                
                upLeftDiagonal.add(board.getTile(new Pair(row - i, column - i)));
            }
            if((row - i) >= -1 && (column + i) <=8){
                upRightDiagonal.add(board.getTile(new Pair(row - i, column + i)));
            }
            if((row + i) <= 8 && (column - i) >= -1){
                downLeftDiagonal.add(board.getTile(new Pair(row + i, column - i)));
            }
            if((row + i) <= 8 && (column + i) <= 8){
                downRightDiagonal.add(board.getTile(new Pair(row + i, column + i)));
            }
        }
        
        //Add possible in upLeftDiagonal
        for(int i = 0; i < upLeftDiagonal.size() && canMoveTo(upLeftDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(upLeftDiagonal.get(i).isOccupied()){
                Player temp1 = upLeftDiagonal.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(upLeftDiagonal.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(upLeftDiagonal.get(i).getPosition());
            } 
        }
        canMove = true;
        
        //Add possible moves in upRightDiagonal
        for(int i = 0; i < upRightDiagonal.size() && canMoveTo(upRightDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(upRightDiagonal.get(i).isOccupied()){
                Player temp1 = upRightDiagonal.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(upRightDiagonal.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(upRightDiagonal.get(i).getPosition());
            } 
        }
        canMove = true;
        
        //Add possible moves in downLeftDiagonal
        for(int i = 0; i < downLeftDiagonal.size() && canMove && canMoveTo(downLeftDiagonal.get(i), MoveType.EmptyOrEnemyPiece); i++){
            //test if tile is occupied
            if(downLeftDiagonal.get(i).isOccupied()){
                Player temp1 = downLeftDiagonal.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(downLeftDiagonal.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(downLeftDiagonal.get(i).getPosition());
            } 
        }
        canMove = true;
        
        //Add possible moves in downRightDiagonal
        for(int i = 0; i < downRightDiagonal.size() && canMoveTo(downRightDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(downRightDiagonal.get(i).isOccupied()){
                Player temp1 = downRightDiagonal.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(downRightDiagonal.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(downRightDiagonal.get(i).getPosition());
            } 
        }
        canMove = true;

        //Return moves
        return moves;
    }

}
