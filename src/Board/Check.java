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
/**
 *
 * @author Chris
 */
public class Check {
    
    public boolean inCheck(Tile[][] matrix, Board board, Player player){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(matrix[i][j].getPiece()!=null){
                    Vector<Pair> moves = matrix[i][j].getPiece().getPossibleMoves(board);
                    Iterator current = moves.iterator();
                    while(current.hasNext()){
                        //matrix[current.next().getRow()][current.getColumn()];
                        
                    }
                    
                }
            }
        }
        return false;
    }
}
