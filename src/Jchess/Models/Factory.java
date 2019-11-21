/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Enums.ThemeColor;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;
import Jchess.Core.*;

/**
 * Factory to create new board instances
 * @author nehalpatel
 */
public class Factory {
    
    /**
     * Prevent instantiation.
     */
    private Factory() { }
    
    /**
     * Makes a new Board instance.
     * @param lightPlayer   Player in control of white pieces.
     * @param darkPlayer    Player in control of dark pieces.
     * @param FEN   FEN/board state to be loaded.
     * @return  Board state for the game.
     */
    public static Board makeBoard(final Player lightPlayer, final Player darkPlayer, final String FEN) {
        Board board = new Board(lightPlayer, darkPlayer);

        Tile[][] matrix = board.getMatrix();
        
        //         rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 default ren
        
        /*
        Part 1: read in board state string and generate the pieces based off of their location
        */
        
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
                    matrix[i][j].setPiece(new Rook(darkPlayer, true));
                    j++;
                    break;
                case 'R':
                    matrix[i][j].setPiece(new Rook(lightPlayer, true));
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
                    matrix[i][j].setPiece(new King(darkPlayer, true));
                    darkPlayer.setLocationOfKing(matrix[i][j].getPosition());
                    j++;
                    break;
                case 'K':
                    matrix[i][j].setPiece(new King(lightPlayer, true));
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
                default: // SPACES BETWEEN PIECES
                    j+=Character.getNumericValue(FEN.charAt(k));
                
            }
        }

        /*
        Part 2: load in character w or b to determine who's turn it is
        */
        
        board.setCurrentPlayer(FEN.charAt(k) == 'w' ? board.getLightPlayer() : board.getDarkPlayer());
        board.setEnemyPlayer(FEN.charAt(k) == 'w' ? board.getDarkPlayer() : board.getLightPlayer());
        
        k += 2; // skips over the turn char and the space after it putting FEN.charAt(k) at the next stage
        
        /*
        Part 3: read in castle availability string and set the boolean flag hasTakenFirstMove for each respective piece
        For rook and king hasTakenFirstMove is default to true.  It is only set to false if the correct char's below are present.
        */
        boolean notFinishedCastle = true;
        for ( ; notFinishedCastle; k++) {
            switch (FEN.charAt(k)) {
                case '-':
                    notFinishedCastle = false;
                    break;
                case ' ':
                    notFinishedCastle = false;
                    break;
                case 'K':
                    board.getMatrix()[board.getLightPlayer().getHomeRow()][Constants.ROOK_COLUMN_KINGSIDE].getPiece().setHasTakenFirstMove(false);
                    board.getMatrix()[board.getLightPlayer().getHomeRow()][Constants.KING_COLUMN].getPiece().setHasTakenFirstMove(false);
                    break;
                case 'Q':
                    board.getMatrix()[board.getLightPlayer().getHomeRow()][Constants.ROOK_COLUMN_QUEENSIDE].getPiece().setHasTakenFirstMove(false);
                    board.getMatrix()[board.getLightPlayer().getHomeRow()][Constants.KING_COLUMN].getPiece().setHasTakenFirstMove(false);
                    break;
                case 'k':
                    board.getMatrix()[board.getDarkPlayer().getHomeRow()][Constants.ROOK_COLUMN_KINGSIDE].getPiece().setHasTakenFirstMove(false);
                    board.getMatrix()[board.getDarkPlayer().getHomeRow()][Constants.KING_COLUMN].getPiece().setHasTakenFirstMove(false);
                    break;
                case 'q':
                    board.getMatrix()[board.getDarkPlayer().getHomeRow()][Constants.ROOK_COLUMN_QUEENSIDE].getPiece().setHasTakenFirstMove(false);
                    board.getMatrix()[board.getDarkPlayer().getHomeRow()][Constants.KING_COLUMN].getPiece().setHasTakenFirstMove(false);
                    break;
            }
            
            if (k == FEN.length() - 1) {
                notFinishedCastle = false;
            }
        }
        
        return board;
    }
    
    /**
     * Creates a FEN representation of the Board.
     * @param gameBoard Board state to be serialized.
     * @return  FEN string for boards current state.
     */
    public static String serializeBoard(final Board gameBoard) {
        StringJoiner joiner = new StringJoiner(" ");
        
        joiner
            .add(generatePiecePlacement(gameBoard.getMatrix()))
            .add(determineActiveColor(gameBoard))
            .add(generateCastlingAvailability(gameBoard));
        
        return joiner.toString();
    }

    /**
     * Creates a deep copy of the Board.
     * @param gameBoard Board to be cloned.
     * @return  Clone of passed in board.
     */
    public static Board cloneBoard(final Board gameBoard) {
        String FEN = serializeBoard(gameBoard);

        Player newLight = new Player(
            UserPreferences.getLightPlayerName(),
            ThemeColor.LightPiece
        );

        Player newDark = new Player(
            UserPreferences.getDarkPlayerName(),
            ThemeColor.DarkPiece
        );

        return makeBoard(newLight, newDark, FEN);
    }

    /**
     * Creates the piece placement part of the FEN.
     * @param matrix    Tile matrix for the board.
     * @return  String containing piece placement information.
     */
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
        
        return piecePlacement;
    }
    
    /**
     * Creates the active color part of the FEN.
     * @param gameBoard Board for the game state.
     * @return  String containing active color information.
     */
    private static String determineActiveColor(final Board gameBoard) {
        return gameBoard.getCurrentPlayer().getColor().getAbbr();
    }
    
    /**
     * Creates the castling availability part of the FEN.
     * @param gameBoard Board with the current game state.
     * @return  String housing castling availability information to be added to FEN.
     */
    private static String generateCastlingAvailability(final Board gameBoard) {
        String castlingAvailability = "";
        
        Player lightPlayer = gameBoard.getLightPlayer();
        Player darkPlayer = gameBoard.getDarkPlayer();
        
        Pair lightKingLocation = lightPlayer.getLocationOfKing();
        Pair darkKingLocation = darkPlayer.getLocationOfKing();
        
        if (lightKingLocation != null) {
            
            Tile lightKingTile = gameBoard.getTile(lightKingLocation);
            King lightKing = (King) lightKingTile.getPiece();
            // if the light king has not taken first move, fetch rooks and check to see if they have taken theirt first moves
            if (!lightKing.hasTakenFirstMove()) {
                Rook lightQueenSide = (Rook) gameBoard.getMatrix()[lightPlayer.getHomeRow()][Constants.ROOK_COLUMN_QUEENSIDE].getPiece();
                Rook lightKingSide = (Rook) gameBoard.getMatrix()[lightPlayer.getHomeRow()][Constants.ROOK_COLUMN_KINGSIDE].getPiece();
                castlingAvailability += lightKing.isRookEligibleForCastling(lightKingSide) ? "K" : "";
                castlingAvailability += lightKing.isRookEligibleForCastling(lightQueenSide) ? "Q" : "";
            }
        }
        
        if (darkKingLocation != null) {
            
            Tile darkKingTile = gameBoard.getTile(darkKingLocation);
            King darkKing = (King) darkKingTile.getPiece();
            // if the dark king has not taken first move, fetch rooks and check to see if they have taken theirt first moves
            if (!darkKing.hasTakenFirstMove()) {
                Rook darkQueenSide = (Rook) gameBoard.getMatrix()[darkPlayer.getHomeRow()][Constants.ROOK_COLUMN_QUEENSIDE].getPiece();
                Rook darkKingSide = (Rook) gameBoard.getMatrix()[darkPlayer.getHomeRow()][Constants.ROOK_COLUMN_KINGSIDE].getPiece();
                castlingAvailability += darkKing.isRookEligibleForCastling(darkKingSide) ? "k" : "";
                castlingAvailability += darkKing.isRookEligibleForCastling(darkQueenSide) ? "q" : "";
            }
        }
        
        return "".equals(castlingAvailability) ? "-" : castlingAvailability;
    }
    
    /**
     * Reads FEN notation from a file.
     * @param fileName  File name to be loaded.
     * @return  FEN string if found, else it will load the default game state.
     */
    public static String readFENFromFile(String fileName) {
        File input = new File(fileName);//.ren
        
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            return br.readLine();
        } catch (IOException e) {
            //if it cannot read from file, it will pass the default board state as a string-
            System.out.print(e.getMessage());
            return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w";
        }
    }
    
    /**
     * Saves a fen to a particular file location
     * @param fen   FEN string to be saved.
     * @param fileName  File Name/Directory to be saved to.
     */
    public static void saveFENToFile(String fen, String fileName){
        File output = new File(fileName);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(output))){
            bw.write(fen);
        } catch (IOException e){
            System.out.print(e.getMessage());
        }
    }
}