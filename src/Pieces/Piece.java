/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.Board;
import Board.Pair;
import Board.Tile;
import Enums.MoveType;
import Enums.PieceType;
import Enums.ThemeColor;
import UserInterface.Player;
import java.util.Vector;
import javax.swing.ImageIcon;
import static Images.Images.Icons;

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
    protected final ImageIcon image;
    protected final PieceType shortHand;

    public Piece(Player owner, PieceType type) {
        player = owner;
        shortHand = type;
        image = Icons.get(shortHand).get(owner.getColor());
        
        if (owner.getColor() == ThemeColor.LightPiece) {
            letter = shortHand.getLightLetter();
        } else {
            letter = shortHand.getDarkLetter();
        }
    }

    /**
     * Returns the vector of pairs to which a piece can move to with its special move.
     * @param board The game board.
     * @return A collection of Pairs representing the possible special moves for this Piece.
     */
    public abstract Vector<Pair> specialMoves(Board board);

    /**
     * Get a list of possible moves this Piece can make.
     * @param board
     * @return A collection of Pairs representing the possible moves for this Piece.
     */
    public abstract Vector<Pair> getPossibleMoves(Board board);

    /**
     * Returns the player to which the piece belongs.
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * returns the current position of the piece
     * @return
     */
    public Pair getCurrentPosition() {
        return position;
    }

    /**
     * sets the current position of a piece
     * @param position
     */
    public void setCurrentPosition(Pair position) {
        this.position = position;
    }
    
    /**
     * Gets the image associated with the piece
     * @return
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     * Boolean to indicate piece is selected
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected piece
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    /**
     * indicates if the piece has taken its first move
     * @return
     */
    public boolean hasTakenFirstMove() {
        return hasTakenFirstMove;
    }

    /**
     * sets if the piece has taken its first move
     * @param hasTakenFirstMove
     */
    public void setHasTakenFirstMove(boolean hasTakenFirstMove) {
        this.hasTakenFirstMove = hasTakenFirstMove;
    }
    
    
    /**
     * Check whether a move to a Tile is technically legal.(Only ensures that the Tile is not occupied by a friendly Piece.)
     * @param tile The Tile which you'd like to move this Piece to.
     * @param type
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
     * @return 
     */
    public char getLetter() {
        return this.letter;
    }

    /**
     * gets the piece type of the piece
     * @return you guessed it 
     */
    public PieceType getPieceType() {
        return this.shortHand;
    }

}
