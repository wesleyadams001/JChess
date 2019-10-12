/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import java.util.Vector;
import java.util.*;
import Player.Player;
import Pieces.Piece;
import Pieces.King;

/**
 *
 * @author Chris
 */
public class Check {
    
    /**
     *
     * @param board the game board itself we are testing
     * @param enemy is the player we want to see if able to move to pair
     * @return boolean true if the pair is a possible move of another enemy piece
     */
    public boolean pairUnderAttack(Pair test, Board board, Player enemy){
        
        Tile[][] matrix = board.getMatrix();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                
                //testing to make sure 1) tile has a piece and 2) its an enemy piece
                if(matrix[i][j].getPiece()!=null && matrix[i][j].getPiece().getPlayer()==enemy){
                    
                    //create a vector of possible moves for enemy piece called moves
                    Vector<Pair> moves = matrix[i][j].getPiece().getPossibleMoves(board);
                    
                    //loop through the possibles moves until we find the pair
                    for(int k = 0; k < moves.size(); k++){
                        
                        // if ( the possible move pair is == to pair passed ) THEN true, that pair is under attack
                        if(moves.get(k).getRow() == test.getRow() && moves.get(k).getColumn() == test.getColumn()){
                            return true;
                        }
                    }   
                }
            }
        }
        //fix this later
        return false;
    }
    
    public boolean kingCanMove(Pair locKing, Board board, Player alpha){
        // check to first see if the king is in check
        if ( pairUnderAttack(locKing, board, alpha) ){
            /*get the possible moves of the king, 
            create a temp tile matrix of board, 
            pass the row and col of pair king to the tile matrix to get the tile, 
            call getPiece to get the king, then call getPossibleMoves
            */
            Tile[][] matrix = board.getMatrix();
            Vector<Pair> kingMoves = matrix[locKing.getRow()][locKing.getColumn()].getPiece().getPossibleMoves(board);
            Player enemy;
            if ( board.getPlayerOne()==alpha){
                enemy = board.getPlayerTwo();
            }else{
                enemy = board.getPlayerOne();
            }
            for (int k = 0; k < kingMoves.size(); k++){
                if (!pairUnderAttack(kingMoves.get(k), board, enemy)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean kingCanBeSaved(Pair locKing, Board board, Player enemy){
        Board temp = board;
        Tile[][] matrix = board.getMatrix();
        for ( int i = 0; i < 8 ; i ++ ){
            for ( int j = 0; j < 8 ; j ++ ){
                if (  matrix[i][j].isOccupied() && matrix[i][j].getPiece().getPlayer()!=enemy ){
                    Vector<Pair> allyMoves = matrix[i][j].getPiece().getPossibleMoves(board);
                    for ( int k = 0 ; k < allyMoves.size() ; k ++ ){
                        /*
                        move ally piece to pair @ allyMoves.get(k)
                        pass king location to pairUnderAttack(), see if it returns false
                        if false, king can be saved, else if true, keep going until false
                        */
                        try {
                            temp.movePiece(matrix[i][j].getPosition(), allyMoves.get(k));
                        } catch (Exception e){
                        
                        }
                        if(!pairUnderAttack(locKing, temp, enemy)){
                            return true;
                        }
                        temp = board;
                        
                        
                    }    
                }
            }
        }
        return false;
    }
    
}
