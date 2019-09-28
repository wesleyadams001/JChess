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
        setPlayer(player);
        
        Images imageSet = new Images();
        
        if (player.getColor() == Color.black) {
            image = imageSet.blackKingImage;
        } else {
            image = imageSet.whiteKingImage;
        }
    }
    
    @Override
    public Vector<Pair> specialMoves(Board board, Piece p) {
        //A special move with the king known as castling is allowed only once per player, per game
        //The king and rook involved in castling must not have previously moved;
        //There must be no pieces between the king and the rook;
        //The king may not currently be in check, nor may the king pass through or end up in a square that is under attack by an enemy piece 
        //(though the rook is permitted to be under attack and to pass over an attacked square);
        //The king and the rook must be on the same rank
        
       throw new UnsupportedOperationException("Not supported yet.");
        
        
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        //The king moves exactly one square horizontally, vertically, or diagonally. 
        Vector<Pair> moves = new Vector<>();
        
        Pair position = this.getCurrentPosition();
        final int row = position.getRow();
        final int column = position.getColumn();
        
        Tile oneAbove = board.getTile(new Pair(row - 1, column));
        Tile oneRight = board.getTile(new Pair(row, column + 1));
        Tile oneLeft = board.getTile(new Pair(row, column - 1));
        Tile oneDown = board.getTile(new Pair(row + 1, column));
            
        if(canMoveTo(oneAbove, MoveType.EnemyPieceOnly)) {
            moves.add(oneAbove.getPosition());
        }
        if(canMoveTo(oneRight, MoveType.EnemyPieceOnly)){
            moves.add(oneRight.getPosition());
        }
        if(canMoveTo(oneLeft, MoveType.EnemyPieceOnly)){
            moves.add(oneLeft.getPosition());
        }
        if(canMoveTo(oneDown, MoveType.EnemyPieceOnly)){
            moves.add(oneDown.getPosition());
        }
        
        return moves;
    }




}
