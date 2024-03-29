/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Enums.MoveType;
import Jchess.Enums.PieceType;
import java.util.Vector;
import javax.swing.ImageIcon;
import static Jchess.Images.Images.Icons;

/**
 * The abstract piece type that all pieces use as a base
 * @author nehalpatel
 */
public abstract class Piece {

    private Pair position;
    private final Player player;
    
    private boolean selected;
    private boolean hasTakenFirstMove;
    
    private final char letter;

    /**
     * An image which represents the Piece on the board.
     */
    protected final ImageIcon image;

    /**
     * Represents the type of the Piece.
     */
    protected final PieceType shortHand;

    /**
     * A Piece.
     * @param owner The Piece's owner.
     * @param type The type of Piece.
     */
    public Piece(Player owner, PieceType type) {
        player = owner;
        shortHand = type;
        image = Icons.get(shortHand).get(owner.getColor());
        letter = type.determineLetter(owner);
    }
    
    /**
     * A Piece.
     * @param owner The Piece's owner.
     * @param type The type of Piece.
     * @param moved Whether to set the hasTakenFirstMove property as true.
     */
    public Piece(Player owner, PieceType type, boolean moved) {
        player = owner;
        shortHand = type;
        hasTakenFirstMove = moved;
        image = Icons.get(shortHand).get(owner.getColor());
        letter = type.determineLetter(owner);
    }

    /**
     * Returns the vector of pairs to which a piece can move to with its special move.
     * @param board The game board.
     * @return A collection of Pairs representing the possible special moves for this Piece.
     */
    public abstract Vector<Pair> getSpecialMoves(Board board);

    /**
     * Get a list of possible moves this Piece can make.
     * @param board The Board on which the Piece exists.
     * @return A collection of Pairs representing the possible moves for this Piece.
     */
    public abstract Vector<Pair> getPossibleMoves(Board board);

    /**
     * Returns the player to which the piece belongs.
     * @return The Piece's owner.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Determines if the Piece is owned by a given Player.
     * @param player The Player to check ownership with.
     * @return True, if player owns the Piece.
     */
    public Boolean isOwnedBy(Player player) {
        return getPlayer() == player;
    }

    /**
     * Returns the current position of the Piece.
     * @return The Piece's position on the Board.
     */
    public Pair getCurrentPosition() {
        return position;
    }

    /**
     * Sets the current position of a Piece.
     * @param position The Piece's position on the Board.
     */
    public void setCurrentPosition(Pair position) {
        this.position = position;
    }
    
    /**
     * Gets the image associated with the Piece.
     * @return The Piece's image asset.
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     * Boolean to indicate Piece is selected.
     * @return True, if Piece is selected.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected piece.
     * @param selected Whether the Piece is selected.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    /**
     * Indicates if the piece has taken its first move.
     * @return True, if the Piece has moved before.
     */
    public boolean hasTakenFirstMove() {
        return hasTakenFirstMove;
    }

    /**
     * Sets if the piece has taken its first move.
     * @param hasTakenFirstMove Whether the Piece has moved.
     */
    public void setHasTakenFirstMove(boolean hasTakenFirstMove) {
        this.hasTakenFirstMove = hasTakenFirstMove;
    }
    
    
    /**
     * Check whether a move to a Tile is technically legal.(Only ensures that the Tile is not occupied by a friendly Piece.)
     * @param tile The Tile which you'd like to move this Piece to.
     * @param type The type of Move.
     * @return True if Tile is empty or holds an enemy Piece, false otherwise.
     */
    public boolean canMoveTo(Tile tile, MoveType type) {
        if (tile == null) {
            return false;
        }

        if (MoveType.EmptyTileOnly == type) {
            return !tile.isOccupied();
        }
        
        if (MoveType.EnemyPieceOnly == type) {
            if (tile.isOccupied()) {
                return tile.getPiece().player != this.player;
            } else {
                return false;
            }
        }
        
        if (MoveType.EmptyOrEnemyPiece == type) {
            if (tile.isOccupied()) {
                return tile.getPiece().player != this.player;
            } else {
                return true;
            }
        }

        return true;
    }

    /**
     * Gets the letter associated with the piece
     * @return A character that represents the Piece.
     */
    public char getLetter() {
        return this.letter;
    }

    /**
     * Gets the piece type of the piece
     * @return The piece type.
     */
    public PieceType getPieceType() {
        return this.shortHand;
    }

    /**
     * Get all possible horizontal and vertical moves.
     * @param board The Board on which the Piece exists.
     * @return A vector of possible horizontal and vertical moves.
     */
    public Vector<Pair> getLaneMoves(Board board){
        Vector<Pair> moves = new Vector<>();        //Store all possible moves to return
        Vector<Tile> laneUpward = new Vector<>();   //Store all tiles above the castle in the same column
        Vector<Tile> laneDownward = new Vector<>(); //Store all tiles below the castle in the same column
        Vector<Tile> laneLeft = new Vector<>();     //Store all tiles to the left of the castle in the same row
        Vector<Tile> laneRight = new Vector<>();    //Store all tiles to the right of the castle in the same row
        
        final int row = position.getRow();          //Initialize variable to hold row position
        final int column = position.getColumn();    //Initialize variable to hold column position
        boolean canMove = true;                     //Boolean to test if an enemy piece is in a path
        
        //Fill laneUpward until outside matrix bounds
        for(int i = 1; (row - i) >= -1; i++){
            laneUpward.add(board.getTile(new Pair(row - i, column)));
        }
        
        //Fill laneDownward until outside matrix bounds
        for(int i = 1; (row + i) <=8 ; i++){
            laneDownward.add(board.getTile(new Pair(row + i, column)));
        }
        
        //Fill laneLeft until outside matrix bounds
        for(int i = 1; (column - i) >= -1; i++){
            laneLeft.add(board.getTile(new Pair(row, column - i)));
        }
        
        //Fill laneRight until outside matrix bounds
        for(int i = 1; (column + i) <= 8; i++){
            laneRight.add(board.getTile(new Pair(row, column + i)));
        }
                
        //Add possible moves above the Piece in the same column
        for(int i = 0; canMoveTo(laneUpward.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(laneUpward.get(i).isOccupied()){
                Player temp1 = laneUpward.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(laneUpward.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(laneUpward.get(i).getPosition());
            }
        }
        canMove = true;
        
        //Add possible moves below the Piece in the same column
        for(int i = 0; canMoveTo(laneDownward.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(laneDownward.get(i).isOccupied()){
                Player temp1 = laneDownward.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(laneDownward.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(laneDownward.get(i).getPosition());
            }
        }
        canMove = true;
        
        //Add possible moves to the left of the Piece in the same row
        for(int i = 0; canMoveTo(laneLeft.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
               //test if tile is occupied
            if(laneLeft.get(i).isOccupied()){
                Player temp1 = laneLeft.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(laneLeft.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(laneLeft.get(i).getPosition());
            }
        }
        canMove = true;
        
        //Add possible moves to the right of the Piece in the same row
        for(int i = 0; canMoveTo(laneRight.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(laneRight.get(i).isOccupied()){
                Player temp1 = laneRight.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(laneRight.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(laneRight.get(i).getPosition());
            }
        }

        //Return moves
        return moves;
    }

    /**
     * Get all possible diagonal moves.
     * @param board The Board on which the Piece exists.
     * @return A vector of possible diagonal moves.
     */
    public Vector<Pair> getDiagonalMoves(Board board){
        Vector<Pair> moves = new Vector<>();                //Store all possible moves to return
        Vector<Tile> upLeftDiagonal = new Vector<>();       //Store all tiles up and to the left in a diagonal line
        Vector<Tile> upRightDiagonal = new Vector<>();      //Store all tiles up and to the right in a diagonal line
        Vector<Tile> downLeftDiagonal = new Vector<>();     //Store all tiles down and to the left in a diagonal line
        Vector<Tile> downRightDiagonal = new Vector<>();    //Store all tiles down and to the right in a diagonal line
        
        final int row = position.getRow();          //Initialize variable to hold row position
        final int column = position.getColumn();    //Initialize variable to hold column position
        
        boolean canMove = true;
        
        //Fill all vector diagonals around quenn until outside matrix bounds
        for(int i = 1; i < 8; i++){
            if((row - i) >= -1 && (column - i) >= -1){
                
                upLeftDiagonal.add(board.getTile(new Pair(row - i, column - i)));
            }
            if((row - i) >= -1 && (column + i) <=8){
                upRightDiagonal.add(board.getTile(new Pair(row - i, column + i)));
            }
            if((row + i) <= 8 && (column - i) >= -1){
                downLeftDiagonal.add(board.getTile(new Pair(row + i, column - i)));
            }
            if((row + i) <= 8 && (column + i) <= 8){
                downRightDiagonal.add(board.getTile(new Pair(row + i, column + i)));
            }
        }
        
        //Add possible in upLeftDiagonal
        for(int i = 0; i < upLeftDiagonal.size() && canMoveTo(upLeftDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(upLeftDiagonal.get(i).isOccupied()){
                Player temp1 = upLeftDiagonal.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(upLeftDiagonal.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(upLeftDiagonal.get(i).getPosition());
            }
        }
        canMove = true;
        
        //Add possible moves in upRightDiagonal
        for(int i = 0; i < upRightDiagonal.size() && canMoveTo(upRightDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(upRightDiagonal.get(i).isOccupied()){
                Player temp1 = upRightDiagonal.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(upRightDiagonal.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(upRightDiagonal.get(i).getPosition());
            }
        }
        canMove = true;
        
        //Add possible moves in downLeftDiagonal
        for(int i = 0; i < downLeftDiagonal.size() && canMove && canMoveTo(downLeftDiagonal.get(i), MoveType.EmptyOrEnemyPiece); i++){
            //test if tile is occupied
            if(downLeftDiagonal.get(i).isOccupied()){
                Player temp1 = downLeftDiagonal.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(downLeftDiagonal.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(downLeftDiagonal.get(i).getPosition());
            }
        }
        canMove = true;
        
        //Add possible moves in downRightDiagonal
        for(int i = 0; i < downRightDiagonal.size() && canMoveTo(downRightDiagonal.get(i), MoveType.EmptyOrEnemyPiece) && canMove; i++){
            //test if tile is occupied
            if(downRightDiagonal.get(i).isOccupied()){
                Player temp1 = downRightDiagonal.get(i).getPiece().getPlayer();
                Player temp2 = this.getPlayer();
                
                //Test if pieces color is the enemy color
                if(!(temp1.getColor() == temp2.getColor())){
                    //Add
                    moves.add(downRightDiagonal.get(i).getPosition());
                    canMove = false; //update boolean so they can't move past it
                }
                
            }
            //Otherwise, add empty tiles to moves
            else{
                moves.add(downRightDiagonal.get(i).getPosition());
            }
        }

        //Return moves
        return moves;
    }

}
