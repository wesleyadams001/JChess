/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Pieces.Piece;
import Pieces.Pawn;
import Player.Player;
import java.util.Vector;

/**
 *
 * @author nehalpatel
 */
public final class Board {

    /**
     *
     */
    public Pair currentSelection;

    /**
     *
     */
    public int currentPlayer;

    /**
     * The number of rows for this Board.
     */
    public final int rowCount = 8;

    /**
     * The number of columns for this Board.
     */
    public final int columnCount = 8;

    private final Tile[][] matrix;

    /**
     *
     */
    public Board(Player one, Player two) {
        matrix = new Tile[rowCount][columnCount];
        
        // Populate matrix with empty Tiles.
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                Tile tile = new Tile(new Pair(rowIndex, columnIndex));
                matrix[rowIndex][columnIndex] = tile;
            }
        }
        
        // Add enemy Pawns.
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            matrix[1][columnIndex].setPiece(createPawn(one, matrix[1][columnIndex].getPosition()));
        }
        
        // Add home Pawns.
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            matrix[rowCount - 2][columnIndex].setPiece(createPawn(two, matrix[rowCount - 2][columnIndex].getPosition()));
        }

        tempTest();
    }

    /**
     * Destroy the current Board and create a new, ready-to-play Board.
     */
    public void reset() {
    }

    /**
     * Load an previously saved session of the game.
     * @param fileName
     */
    public void load(String fileName) {
    }

    /**
     * Save this session of the game.
     * @param fileName
     */
    public void serialize(String fileName) {
    }

    /**
     * Search for and retrieve a Tile.
     * @param position A pair of coordinates with which to search for a Tile.
     * @return The desired Tile if found, else null.
     */
    public Tile getTile(Pair position) {
        try {
            return matrix[position.getRow()][position.getColumn()];
        } catch (IndexOutOfBoundsException error) {
            System.out.println("Error while accessing Tile.");
            System.out.println(error);
            return null;
        }
    }
    
    public Tile[][] getMatrix(){
        return this.matrix;
    }
    
    /**
     * Swap the Pieces of two Tiles.
     * @param position1
     * @param position2
     */
    public void swapPieces(Pair position1, Pair position2) {
        Piece piece1 = getTile(position1).getPiece();
        
        if (piece1 != null) {
            piece1.setCurrentPosition(position2);
        }
        
        Piece piece2 = getTile(position2).getPiece(); 
        
        if (piece2 != null) {
            piece2.setCurrentPosition(position1);
        }
        
        matrix[position1.getRow()][position1.getColumn()].setPiece(piece2);
        matrix[position2.getRow()][position2.getColumn()].setPiece(piece1);
    }
    
    private Pawn createPawn(Player player, Pair position) {
        Pawn pawn = new Pawn(player);
        pawn.setCurrentPosition(position);
        
        return pawn;
    }
    
    public void tempTest() {
        // TEMPORARY setup. This will show the potential moves of the rightmost
        // enemy Pawn. A friendly Pawn has been moved to the enemy Pawn's diagonal
        // for demonstration. Check the console.
        // (T) = able to be taken
        // (x) = able to be moved to
        // ( ) = blank Tile
        // (P) = any Piece
        
        int rowIndex = 6; // Row index for Player 1's Pawns.
        int columnIndex = 5; // Random Pawn in the row.
        Tile player1PawnTile = getTile(new Pair(rowIndex, columnIndex));
        
        // Comment this line out to test.
        //player1PawnTile.getPiece().setHasTakenFirstMove(true);

        // Empty space diagonal to Player 1's Pawn.
        Tile emptySpace = getTile(new Pair(rowIndex - 1, columnIndex - 1)); 
        
        // Enemy Pawn.
        Tile player2PawnTile = getTile(new Pair(1, columnIndex - 1));
        
        print();
        System.out.println();

        // Move the Enemy Pawn into a kill position for Player 1's Pawn.
        swapPieces(player2PawnTile.getPosition(), emptySpace.getPosition());
        
        // Get possible moves for Player 1's Pawn.
        Vector<Pair> moves = player1PawnTile.getPiece().getPossibleMoves(this);
        
        print();
        System.out.println();
        
        player1PawnTile.getPiece().setSelected(true);
        
        // Highlight the moves on the Board.
        moves.forEach((position) -> {
            getTile(position).setHighlighted(true);
        });
        
        // Print the Board state to the console.
        print();
    }
    
    /**
     * Temporary method to debug and test the Board before the GUI is implemented.
     */
    public void print() {
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                Tile tile = matrix[rowIndex][columnIndex];
                if (tile.isHighlighted() && tile.isOccupied()) {
                    System.out.print("(T) "); // This Tile has a Piece that can be taken by the currently selected Piece.
                } else if (tile.isHighlighted()) {
                    System.out.print("(x) "); // This Tile can be moved to by the currently selected Piece.
                } else if (tile.isOccupied() && tile.getPiece().isSelected()) {
                    System.out.print("(S) ");
                } else if (!tile.isOccupied()) {
                    System.out.print("( ) "); // This Tile is empty.
                } else {
                    System.out.print("(" + tile.getPiece().getPlayer().getColor().getAbbr() + ") "); // This Tile has a Piece.
                }
            }
            
            System.out.println();
        }
    }

}
