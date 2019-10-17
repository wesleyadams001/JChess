/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Enums.ThemeColor;
import Pieces.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nehalpatel
 */
public class FEN {
    private final Map<Class, Character> pieceMapping;
    
    public FEN() {
        pieceMapping = new HashMap<>();
        pieceMapping.put(Rook.class, 'R');
        pieceMapping.put(Knight.class, 'N');
        pieceMapping.put(Bishop.class, 'B');
        pieceMapping.put(Queen.class, 'Q');
        pieceMapping.put(King.class, 'K');
        pieceMapping.put(Pawn.class, 'P');
    }
    
    public String serialize(final Board gameBoard) {
        String piecePlacement = generatePiecePlacement(gameBoard.getMatrix());
        char activeColor = determineActiveColor(gameBoard);
        
        return piecePlacement + " " + activeColor;
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
                    piecePlacement += determinePieceLetter(tile.getPiece());
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
        
        return piecePlacement;
    }
    
    private Character determinePieceLetter(final Piece piece) {
        if (pieceMapping.containsKey(piece.getClass())) {
            // Get the single character representation for the Piece. 
            Character letter = pieceMapping.get(piece.getClass());
            
            // Convert to lowercase if it's a black piece.
            if (piece.getPlayer().getColor() == ThemeColor.DarkPiece) {
                letter = Character.toLowerCase(letter);
            }
            
            return letter;
        }
        
        return null;
    }
    
    private char determineActiveColor(final Board gameBoard) {
        return gameBoard.getCurrentPlayer().getColor().getAbbr().charAt(0);
    }
}
