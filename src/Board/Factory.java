/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author nehalpatel
 */
public class Factory {
    
    public Factory() { }
    
    public String serialize(final Board gameBoard) { 
        return generatePiecePlacement(gameBoard.getMatrix()) + determineActiveColor(gameBoard);
    }
    
    private String generatePiecePlacement(final Tile[][] matrix) {
        String piecePlacement = "";
        for (int rowIndex = 0; rowIndex < matrix.length; rowIndex++) {
            // Initiate tally of empty squares.
            int emptySquares = 0;
            
            for (int columnIndex = 0; columnIndex < matrix[rowIndex].length; columnIndex++) {
                Tile tile = matrix[rowIndex][columnIndex];
                
                // We've reached a Piece! 
                if (tile.isOccupied()) {
                    // Append a tally of the empty squares up to this Piece.
                    if (emptySquares > 0) {
                        piecePlacement += emptySquares;
                        emptySquares = 0;
                    }
                    
                    // Append the current Piece.
                    piecePlacement += tile.getPiece().getLetter();
                } else {
                    // Track another empty square.
                    emptySquares += 1;
                    
                    // Append a tally of the empty squares up to the end of the row.
                    if (columnIndex == matrix[rowIndex].length - 1) {
                        piecePlacement += emptySquares;
                    }
                }
            }
            
            // Mark the row as finished.
            if (rowIndex != matrix.length - 1) {
                piecePlacement += "/";
            }
        }
        
        return piecePlacement+" ";
    }
    
    private char determineActiveColor(final Board gameBoard) {
        return gameBoard.getCurrentPlayer().getColor().getAbbr().charAt(0);
    }
    
    public String loadFromFile(String fileName) {
        File input = new File(fileName);//.ren
        
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            return br.readLine();
        } catch (IOException e) {
            System.out.print(e.getMessage());
            return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w";
        }
    }
}