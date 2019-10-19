/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Pieces.*;
import Player.Player;
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
    
    public static Board makeBoard(final Player lightPlayer, final Player darkPlayer, final String FEN) {
        Board board = new Board(lightPlayer, darkPlayer);

        Tile[][] matrix = board.getMatrix();
        
        //         rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w default ren
        int i = 0, j = 0, k = 0;
        boolean boardComplete = false;
        for( ; !boardComplete ; k++){
            switch (FEN.charAt(k)){
                case ' ':
                    boardComplete=true; // a _ means we are done with the board data
                    break;
                case '/':
                    i++; // a / means move to next rank/line
                    j=0;
                    break;
                case 'r': // ROOKS
                    matrix[i][j].setPiece(new Rook(darkPlayer));
                    j++;
                    break;
                case 'R':
                    matrix[i][j].setPiece(new Rook(lightPlayer));
                    j++;
                    break;
                case 'n': // KNIGHTS
                    matrix[i][j].setPiece(new Knight(darkPlayer));
                    j++;
                    break;
                case 'N':
                    matrix[i][j].setPiece(new Knight(lightPlayer));
                    j++;
                    break;
                case 'b': // BISHOPS
                    matrix[i][j].setPiece(new Bishop(darkPlayer));
                    j++;
                    break;
                case 'B':
                    matrix[i][j].setPiece(new Bishop(lightPlayer));
                    j++;
                    break;
                case 'q': // QUEENS
                    matrix[i][j].setPiece(new Queen(darkPlayer));
                    j++;
                    break;
                case 'Q': 
                    matrix[i][j].setPiece(new Queen(lightPlayer));
                    j++;
                    break;
                case 'k': // KINGS
                    matrix[i][j].setPiece(new King(darkPlayer));
                    darkPlayer.setLocationOfKing(matrix[i][j].getPosition());
                    j++;
                    break;
                case 'K':
                    matrix[i][j].setPiece(new King(lightPlayer));
                    lightPlayer.setLocationOfKing(matrix[i][j].getPosition());
                    j++;
                    break;
                case 'p': // PAWNS
                    matrix[i][j].setPiece(new Pawn(darkPlayer));
                    j++;
                    break;
                case 'P':
                    matrix[i][j].setPiece(new Pawn(lightPlayer));
                    j++;
                    break;
                default:
                    j+=Character.getNumericValue(FEN.charAt(k));
                
            }
        }

        board.setCurrentPlayer(FEN.charAt(k) == 'w' ? board.getLightPlayer() : board.getDarkPlayer());
        board.setEnemyPlayer(FEN.charAt(k) == 'b' ? board.getDarkPlayer() : board.getLightPlayer());

        return board;
    }
    
    public static String serializeBoard(final Board gameBoard) { 
        return generatePiecePlacement(gameBoard.getMatrix()) + determineActiveColor(gameBoard);
    }

    public static Board cloneBoard(final Board gameBoard) {
        String FEN = serializeBoard(gameBoard);
        return makeBoard(gameBoard.getLightPlayer(), gameBoard.getDarkPlayer(), FEN);
    }

    private static String generatePiecePlacement(final Tile[][] matrix) {
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
    
    private static char determineActiveColor(final Board gameBoard) {
        return gameBoard.getCurrentPlayer().getColor().getAbbr().charAt(0);
    }
    
    public static String readFENFromFile(String fileName) {
        File input = new File(fileName);//.ren
        
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            return br.readLine();
        } catch (IOException e) {
            System.out.print(e.getMessage());
            return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w";
        }
    }
}