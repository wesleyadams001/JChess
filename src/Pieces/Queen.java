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
import Player.Player;
import java.util.Vector;

/**
 * The class that contains the logic that 
 * @author jonathanjoiner
 */
public class Queen  extends Piece{
    
    public Queen(Player owner){
        super(owner, PieceType.Queen);
    }

    private Vector<Pair> getLanes(Board board){
        Vector<Pair> moves = new Vector<>();        //Store all possible moves to return
        Vector<Tile> laneUpward = new Vector<>();   //Store all tiles above the castle in the same column
        Vector<Tile> laneDownward = new Vector<>(); //Store all tiles below the castle in the same column
        Vector<Tile> laneLeft = new Vector<>();     //Store all tiles to the left of the castle in the same row
        Vector<Tile> laneRight = new Vector<>();    //Store all tiles to the right of the castle in the same row
        
        Pair position = this.getCurrentPosition();  //Initialize Pair object with current position
        final int row = position.getRow();          //Initialize variable to hold row position
        final int column = position.getColumn();    //Initialize variable to hold column position
        boolean canMove = true;                     //Boolean to test if an enemy piece is in a path
        
        //Fill laneUpward until outside matrix bounds
        for(int i = 1; (row - i) >= -1; i++){
            laneUpward.add(board.getTile(new Pair(row - i, column)));
        }
        
        //Fill laneDownward until outside matrix bounds
        for(int i = 1; (row + i) <=8 ; i++){
            laneDownward.add(board.getTile(new Pair(row + i, column)));
        }
        
        //Fill laneLeft until outside matrix bounds
        for(int i = 1; (column - i) >= -1; i++){
            laneLeft.add(board.getTile(new Pair(row, column - i)));
        }
        
        //Fill laneRight until outside matrix bounds
        for(int i = 1; (column + i) <= 8; i++){
            laneRight.add(board.getTile(new Pair(row, column + i)));
        }
                
        //Add possible moves above the Queen in the same column
        for(int i = 0; canMoveTo(laneUpward.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(laneUpward.get(i).isOccupied()){
                Player temp1 = laneUpward.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(laneUpward.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(laneUpward.get(i).getPosition());
            } 
        }
        canMove = true;
        
        //Add possible moves below the Queen in the same column
        for(int i = 0; canMoveTo(laneDownward.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(laneDownward.get(i).isOccupied()){
                Player temp1 = laneDownward.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(laneDownward.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(laneDownward.get(i).getPosition());
            }
        }
        canMove = true;
        
        //Add possible moves to the left of the Queen in the same row
        for(int i = 0; canMoveTo(laneLeft.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
               //test if tile is occupied
            if(laneLeft.get(i).isOccupied()){
                Player temp1 = laneLeft.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(laneLeft.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(laneLeft.get(i).getPosition());
            }
        }
        canMove = true;
        
        //Add possible moves to the right of the Queen in the same row
        for(int i = 0; canMoveTo(laneRight.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(laneRight.get(i).isOccupied()){
                Player temp1 = laneRight.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(laneRight.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(laneRight.get(i).getPosition());
            }
        }
        canMove = true;

        //Return moves
        return moves;
    }
    
    private Vector<Pair> getDiagonals(Board board){
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
        for(int i = 0; canMoveTo(upLeftDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
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
        for(int i = 0; canMoveTo(upRightDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
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
        for(int i = 0; canMoveTo(downLeftDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
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
        for(int i = 0; canMoveTo(downRightDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
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
    @Override
    public Vector<Pair> specialMoves(Board board) {
        Vector<Pair> moves = new Vector<>();
        
        return moves;
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        Vector<Pair> moves = new Vector<>();
        Vector<Pair> lanes = this.getLanes(board);
        Vector<Pair> diagonals = this.getDiagonals(board);
        
        //Add lanes to moves
        for(int i = 0; i < lanes.size(); i++){
            moves.add(lanes.get(i));
        }
        
        //Add diagonals to moves
        for(int i = 0; i < diagonals.size(); i++){
            moves.add(diagonals.get(i));
        }
        
        //Return move set
        return moves;
    }
}
