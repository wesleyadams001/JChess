/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import java.util.Vector;

/**
 *
 * @author nehalpatel
 */
public final class Board {

    /**
     *
     */
    public Grid grid;

    /**
     *
     */
    public Pair currentSelection;

    /**
     *
     */
    public int currentPlayer;

    /**
     *
     */
    public Board() {
        grid = new Grid();
        
        // TEMPORARY setup. This will show the potential moves of the rightmost
        // enemy Pawn. A friendly Pawn has been moved to the enemy Pawn's diagonal
        // for demonstration. Check the console.
        // (T) = able to be taken
        // (x) = able to be moved to
        // ( ) = blank Tile
        // (P) = any Piece
        
        int rowIndex = 6; // Row index for Player 1's Pawns.
        int columnIndex = 5; // Random Pawn in the row.
        Tile player1PawnTile = grid.getTile(new Pair(rowIndex, columnIndex));
        
        // Comment this line out to test.
        //player1PawnTile.getPiece().setHasTakenFirstMove(true);

        // Empty space diagonal to Player 1's Pawn.
        Tile emptySpace = grid.getTile(new Pair(rowIndex - 1, columnIndex - 1)); 
        
        // Enemy Pawn.
        Tile player2PawnTile = grid.getTile(new Pair(1, columnIndex - 1));
        
        grid.print();
        System.out.println();

        // Move the Enemy Pawn into a kill position for Player 1's Pawn.
        grid.swapPieces(player2PawnTile.getPosition(), emptySpace.getPosition());
        
        // Get possible moves for Player 1's Pawn.
        Vector<Pair> moves = player1PawnTile.getPiece().getPossibleMoves(grid);
        
        grid.print();
        System.out.println();
        
        player1PawnTile.getPiece().setSelected(true);
        
        // Highlight the moves on the Board.
        moves.forEach((position) -> {
            grid.getTile(position).setHighlighted(true);
        });
        
        // Print the Board state to the console.
        grid.print();
    }

    /**
     * Destroy the current Grid and create a new, ready-to-play Grid.
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

}
