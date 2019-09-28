/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.Board;
import Board.Tile;
import Board.Pair;
import Enums.Color;
import Enums.MoveType;
import Images.Images;
import Player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * The King piece
 * @author Wesley
 */
public class King extends Piece{
     
    //Enumerated type to store all possible moves for King piece
    private enum Move {
        UpLeftDiagonal,
        Up,
        UpRightDiagonal,
        Right,
        RightDownDiagonal,
        down,
        LeftDownDiagonal,
        Left,
        TwoRight,
        TwoLeft
        
    };
    
    public King(Player player){
        //Call function to initialize player object
        setPlayer(player);
        
        //Create imageset object
        Images imageSet = new Images();
        
        //Test if King is black
        if (player.getColor() == Color.black) {
            image = imageSet.blackKingImage;    //Set to black
        } else {
            image = imageSet.whiteKingImage;    //Set to white
        }
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
        
        //The king special castling moves
        Vector<Pair> specialMoves = new Vector<>();
        
        Pair position = this.getCurrentPosition();  //Initialize Pair object with current position
        final int row = position.getRow();          //Initialize variable to hold row position
        final int column = position.getColumn();    //Initialize variable to hold column position
        boolean pos1 = false;
        boolean pos2 = false;
        
        
        Tile castlePos1 = board.getTile(new Pair(row, column - 4));
        Tile castlePos2 = board.getTile(new Pair(row, column + 3));
        
        if(this.hasTakenFirstMove){
            throw new UnsupportedOperationException("King has taken first move. can't castle");
        }
        
        if(castlePos1.getPiece() != null){
            if(castlePos1.getPiece().rook){
                if(castlePos1.getPiece().hasTakenFirstMove == false){
                    pos1 = true;
                }
            } 
        }
       if(castlePos2.getPiece() != null){
            if(castlePos2.getPiece().rook){
                if(castlePos2.getPiece().hasTakenFirstMove == false){
                    pos2 = true;
                }
            } 
        }
       
       
        
        
       throw new UnsupportedOperationException("Not supported yet.");
        
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
        Tile downRight = board.getTile(new Pair(row - 1, column + 1));
        Tile downLeft = board.getTile(new Pair(row - 1, column - 1));
        
        //Test which squares the king can move to
        if(canMoveTo(oneAbove, MoveType.EnemyPieceOnly)) { 
            moves.add(oneAbove.getPosition());      //Add oneAbove to possible move set
        }
        if(canMoveTo(oneRight, MoveType.EnemyPieceOnly)){
            moves.add(oneRight.getPosition());      //Add oneRight to possible move set
        }
        if(canMoveTo(oneLeft, MoveType.EnemyPieceOnly)){
            moves.add(oneLeft.getPosition());       //Add oneLeft to possible move set
        }
        if(canMoveTo(oneDown, MoveType.EnemyPieceOnly)){
            moves.add(oneDown.getPosition());       //Add oneDown to possible move set
        }
        if(canMoveTo(aboveRight, MoveType.EnemyPieceOnly)){
            moves.add(aboveRight.getPosition());    //Add aboveRight to possible move set
        }
        if(canMoveTo(aboveLeft, MoveType.EnemyPieceOnly)){
            moves.add(aboveLeft.getPosition());     //Add aboveLeft to possible move set
        }
        if(canMoveTo(downRight, MoveType.EnemyPieceOnly)){
            moves.add(downRight.getPosition());     //Add downRight to possible move set
        }
        if(canMoveTo(downLeft, MoveType.EnemyPieceOnly)){
            moves.add(downLeft.getPosition());      //Add downLeft to possible move set
        }
        
        //Return possible move set
        return moves;
    }




}
