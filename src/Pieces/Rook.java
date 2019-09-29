/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.Board;
import Board.Pair;
import Board.Tile;
import Enums.Color;
import Enums.MoveType;
import Images.Images;
import Player.Player;
import static Images.Images.castleIcons;
import java.util.Vector;

/**
 *
 * @author jonathanjoiner
 */
public class Rook extends Piece {

    public Rook(Player owner) {
        super(owner);
        image = castleIcons.get(owner.getColor()); 
    }

    @Override
    public Vector<Pair> specialMoves(Board board, Piece p) {
        Vector<Pair> moves = new Vector<>();
        
        return moves;
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
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
        
        
        
        //Add possible moves above the rook in the same column
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
        
        //Add possible moves below the rook in the same column
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
        
        //Add possible moves to the left of the rook in the same row
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
        
        //Add possible moves to the right of the rook in the same row
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

}
