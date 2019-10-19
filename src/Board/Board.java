/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Enums.PieceType;
import Pieces.Bishop;
import Pieces.Piece;
import Pieces.King;
import Pieces.Knight;
import Pieces.Rook;
import Pieces.Pawn;
import Pieces.Queen;
import Player.Player;

/**
 *
 * @author nehalpatel
 */
public final class Board {

    /**
     *
     */
    private Player currentPlayer;

    /**
     *
     */
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
    public Board(Player one, Player two, String fen) {
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
        
        //         rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w default ren
        int i = 0, j = 0, k = 0;
        boolean boardComplete = false;
        for( ; !boardComplete ; k++){
            switch (fen.charAt(k)){
                case ' ':
                    boardComplete=true; // a _ means we are done with the board data
                    break;
                case '/':
                    i++; // a / means move to next rank/line
                    j=0;
                    break;
                case 'r': // ROOKS
                    matrix[i][j].setPiece(new Rook(two));
                    j++;
                    break;
                case 'R':
                    matrix[i][j].setPiece(new Rook(one));
                    j++;
                    break;
                case 'n': // KNIGHTS
                    matrix[i][j].setPiece(new Knight(two));
                    j++;
                    break;
                case 'N':
                    matrix[i][j].setPiece(new Knight(one));
                    j++;
                    break;
                case 'b': // BISHOPS
                    matrix[i][j].setPiece(new Bishop(two));
                    j++;
                    break;
                case 'B':
                    matrix[i][j].setPiece(new Bishop(one));
                    j++;
                    break;
                case 'q': // QUEENS
                    matrix[i][j].setPiece(new Queen(two));
                    j++;
                    break;
                case 'Q': 
                    matrix[i][j].setPiece(new Queen(one));
                    j++;
                    break;
                case 'k': // KINGS
                    matrix[i][j].setPiece(new King(two));
                    j++;
                    break;
                case 'K':
                    matrix[i][j].setPiece(new King(one));
                    j++;
                    break;
                case 'p': // PAWNS
                    matrix[i][j].setPiece(new Pawn(two));
                    j++;
                    break;
                case 'P':
                    matrix[i][j].setPiece(new Pawn(one));
                    j++;
                    break;
                default:
                    j+=Character.getNumericValue(fen.charAt(k));
                
            }
        }
        
        this.setCurrentPlayer(fen.charAt(k) == 'w' ? this.getPlayerOne() : this.getPlayerTwo());
        this.setEnemyPlayer(fen.charAt(k) == 'b' ? this.getPlayerTwo() : this.getPlayerOne());
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
    
    public void reset(Tile[][] matrix, Player one, Player two) {
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
     * Load an previously saved session of the game.
     * @param fileName
     */
    
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
    public Tile getTransientTile() {
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
     */
    public void movePiece(Pair from, Pair to) {
        Tile toTile = getTile(to);
        if (toTile.isOccupied()) {
            // throw new Exception("The arrival Tile isn't empty.");
            toTile.removePiece();
        }

        Tile fromTile = getTile(from);
        if (fromTile.isOccupied()) {
            // Useful for tracking special moves for Pawn, etc.
            fromTile.getPiece().setHasTakenFirstMove(true);
        } else {
            // throw new Exception("The departing Piece doesn't exist.");
        }

        Piece fromPiece = fromTile.removePiece();
        toTile.setPiece(fromPiece);
        
        // =================
        // Move is finished.
        // =================
        
        if (toTile.getPiece().getPieceType() == PieceType.King){
            getCurrentPlayer().setLocationOfKing(toTile.getPosition());
        }
    }

}
