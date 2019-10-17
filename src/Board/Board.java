/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Pieces.Bishop;
import Pieces.Piece;
import Pieces.King;
import Pieces.Knight;
import Pieces.Rook;
import Pieces.Pawn;
import Pieces.Queen;
import Player.Player;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nehalpatel
 */
public final class Board {

    /**
     *
     */
    private Player currentPlayer;
    
    private Player enemyPlayer;

    /**
     *
     */
    private Player playerOne;

    /**
     *
     */
    private Player playerTwo;

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
     * @param one
     * @param two
     */
    public Board(Player one, Player two) {
        this.currentPlayer = one;
        this.enemyPlayer = two;
        this.playerOne = one;
        this.playerTwo = two;

        matrix = new Tile[rowCount][columnCount];

        // Populate matrix with empty Tiles.
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                Tile tile = new Tile(new Pair(rowIndex, columnIndex));
                matrix[rowIndex][columnIndex] = tile;
            }
        }

        // Add Pawns.
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            matrix[1][columnIndex].setPiece(new Pawn(two));
            matrix[rowCount - 2][columnIndex].setPiece(new Pawn(one));
        }

        // Add Kings. and set location of pair for each player
        matrix[0][4].setPiece(new King(two));
        matrix[rowCount - 1][4].setPiece(new King(one));
        two.setLocationOfKing(matrix[0][4].getPosition());
        one.setLocationOfKing(matrix[rowCount - 1][4].getPosition());
        
        // Add Rooks.
        matrix[0][0].setPiece(new Rook(two));
        matrix[0][7].setPiece(new Rook(two));
        matrix[rowCount - 1][0].setPiece(new Rook(one));
        matrix[rowCount - 1][7].setPiece(new Rook(one));
        
        //Add Knights 
        matrix[0][1].setPiece(new Knight(two));
        matrix[0][6].setPiece(new Knight(two));
        matrix[rowCount - 1][1].setPiece(new Knight(one));
        matrix[rowCount - 1][6].setPiece(new Knight(one));
        
        //Add Bishops
        matrix[0][2].setPiece(new Bishop(two));
        matrix[0][5].setPiece(new Bishop(two));
        matrix[rowCount - 1][2].setPiece(new Bishop(one));
        matrix[rowCount - 1][5].setPiece(new Bishop(one));
        
        //Add Queens
        matrix[0][3].setPiece(new Queen(two));
        matrix[rowCount - 1][3].setPiece(new Queen(one));
    }
    /**
     * constructor that duplicates the board passed to the constructor
     * @param orig the board that will be copied
     */
    public Board(Board orig){
        this.enemyPlayer = orig.enemyPlayer;
        this.currentPlayer = orig.currentPlayer;
        this.playerOne = orig.playerOne;
        this.playerTwo = orig.playerTwo;
        //duplicating the matrix
        this.matrix = new Tile[orig.rowCount][orig.columnCount];
        // Populate matrix with empty Tiles.
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                Tile tile = new Tile(new Pair(rowIndex, columnIndex));
                matrix[rowIndex][columnIndex] = tile;
            }
        }
        //copy the state of the board over
        for(int i=0;i<rowCount;i++){
            for(int j=0;j<columnCount;j++){
                if(orig.matrix[i][j].isOccupied()){
                    this.matrix[i][j].setPiece(orig.matrix[i][j].getPiece());
                }
            }
        }
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
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public Player getEnemyPlayer() {
        return this.enemyPlayer;
    }
    
    public void setEnemyPlayer(Player enemy) {
        this.enemyPlayer = enemy;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public void toggleCurrentPlayer() {
        setCurrentPlayer(getCurrentPlayer() == getPlayerOne() ? getPlayerTwo() : getPlayerOne());
        setEnemyPlayer(getEnemyPlayer() == getPlayerOne() ? getPlayerTwo() : getPlayerOne());
    }

    public boolean isValidPair(Pair pair) {
        int rowIndex = pair.getRow();
        int columnIndex = pair.getColumn();

        if ((rowIndex < 0) || (columnIndex < 0)) {
            return false;
        }

        if ((rowIndex > rowCount - 1) || (columnIndex > columnCount - 1)) {
            return false;
        }

        return true;
    }

    /**
     * Search for and retrieve a Tile.
     * @param position A pair of coordinates with which to search for a Tile.
     * @return The desired Tile if found, else null.
     */
    public Tile getTile(Pair position) {
        if (isValidPair(position)) {
            return matrix[position.getRow()][position.getColumn()];
        }

        return null;
    }

    /**
     * Get the Player's selected Piece.
     * @return The first Piece found with isSelected() being true, null otherwise.
     */
    public Tile getCurrentSelection() {
        for (Tile[] row : matrix) {
            for (Tile tile : row) {
                if (tile.isOccupied() && tile.getPiece().isSelected()) {
                    return tile;
                }
            }
        }

        return null;
    }

    public Tile[][] getMatrix(){
        return this.matrix;
    }

    /**
     * Move a Piece to another Tile.
     * 
     * @param from
     * @param to
     * @throws Exception
     */
    public void movePiece(Pair from, Pair to) throws Exception {
        Tile toTile = getTile(to);
        if (toTile.isOccupied()) {
            throw new Exception("The arrival Tile isn't empty.");
        }

        Tile fromTile = getTile(from);
        if (!fromTile.isOccupied()) {
            throw new Exception("The departing Piece doesn't exist.");
        }

        Piece fromPiece = fromTile.removePiece();
        toTile.setPiece(fromPiece);
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

        try {
            // Move the Enemy Pawn into a kill position for Player 1's Pawn.
            movePiece(player2PawnTile.getPosition(), emptySpace.getPosition());
        } catch (Exception ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
