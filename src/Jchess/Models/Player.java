/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Enums.ThemeColor;
import Jchess.Core.Constants;

import java.util.Collection;
import java.util.stream.Stream;
/**
 * Class that contains the player logic
 * @author nehalpatel
 */
public final class Player {
    private final String name;
    private final ThemeColor color;
    private final int homeRow;
    private final int pawnRow;
    private Pair kingPair;
    
    /**
     * A Player.
     * @param name The Player's name.
     * @param color The Player's color, which is either Light or Dark.
     */
    public Player(String name, ThemeColor color) {
        this.name = name;
        this.color = color;
        this.homeRow = this.getColor() == ThemeColor.DarkPiece ? Constants.HOME_ROW_DARK : Constants.HOME_ROW_LIGHT;
        this.pawnRow = this.getColor() == ThemeColor.DarkPiece ? Constants.PAWN_ROW_DARK : Constants.PAWN_ROW_LIGHT;
    }

    /**
     * Returns the Player's opponent, aka the other colored Player.
     * @param board The game board.
     * @return The Player's opponent.
     */
    private Player getOpponent(Board board) {
        return getColor() == ThemeColor.LightPiece ? board.getDarkPlayer() : board.getLightPlayer();
    }

    /**
     * Returns the Player's name.
     * @return  The player's name as a string.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the Player's color.
     * @return  The player's color.
     */
    public ThemeColor getColor() {
        return this.color;
    }
    
    /**
     * Returns the location of Player's King.
     * @return  The pair coordinates where the king is located.
     */
    public Pair getLocationOfKing() {
        return this.kingPair;
    }
    
    /**
     * Updates the location of Player's King.
     * @param king The pair location of the king.
     */
    public void setLocationOfKing(Pair king){
        this.kingPair = king;
    }

    /**
     * Returns an array of Tiles found in the Player's home row.
     * @param board The game board.
     * @return The array of home row Tiles.
     */
    public Tile[] getHomeRow(Board board) {
        return board.getMatrix()[homeRow];
    }
    
    /**
     * Get the home row rank
     * @return  The home row location.
     */
    public int getHomeRow() {
        return this.homeRow;
    }
    
    /**
     * Get the Pawn row rank.
     * @return Player's Pawn row index.
     */
    public int getPawnRow() {
        return this.pawnRow;
    }

    /**
     * Get all the Player's Pieces.
     * @param board The current Board State.
     * @return Pieces on the board in an array.
     */
    public Stream<Piece> getPieces(Board board) {
        return board
            .getTiles()
            // Only keep Tiles that 1) have a Piece 2) that's owned by the Player.
            .filter(tile -> tile.isOccupied() && tile.getPiece().isOwnedBy(this))
            // For every Tile, replace with inner Piece.
            .map(tile -> tile.getPiece());
    }

    /**
     * Get a list of all possible moves the Player's Pieces can make.
     * @param board The current board state.
     * @return  Array of all possible moves.
     */
    public Stream<Pair> getPossibleMoves(Board board) {
        return getPieces(board)
            // For every Piece, replace with vector of its possible moves.
            .map(piece -> piece.getPossibleMoves(board))
            // Combine possible moves for all enemy Pieces in this row into one "array".
            .flatMap(Collection::stream);
    }

    /**
     * Determines if the Player's King can be saved by an ally's move.
     * @param board The current board state.
     * @return  Boolean showing whether king can be saved.
     */
    public boolean canKingBeSaved(Board board) {
        // Create a collection of all the Player's Pieces.
        Stream<Piece> alliedPieces = getPieces(board);
        // Resolves to true if any of the Player's Pieces can save the King.
        return alliedPieces.anyMatch(piece -> canPieceSaveKing(board, piece));
    }

    /**
     * Determines if a given allied Piece can make a move that would take the Player's King out of Check.
     * @param board The current board state.
     * @param alliedPiece   Piece to test moves for.
     * @return Boolean showing whether allied piece can save the king.
     */
    private boolean canPieceSaveKing(Board board, Piece alliedPiece) {
        // Create a collection of possible moves the Piece can make.
        Stream<Pair> possibleMoves = alliedPiece.getPossibleMoves(board).stream();
        // Resolves to true if any of the Piece's possible moves can save the King.
        return possibleMoves.anyMatch(desination -> canMoveSaveKing(board, alliedPiece, desination));
    }

    /**
     * Determines if a given move by an allied Piece can take the Player's King out of Check.
     * @param board The current board state.
     * @param alliedPiece   Piece to test move for.
     * @param newDestination    Pair for destination to move to.
     * @return  Boolean showing whether specified move can save the king.
     */
    private boolean canMoveSaveKing(Board board, Piece alliedPiece, Pair newDestination) {
        // Move allied Piece to destination Pair.
        Board simulatedBoard = board.simulatedWithMove(alliedPiece, board.getTile(newDestination));
        // If false, King can be saved.
        return !simulatedBoard.getCurrentPlayer().isKingUnderAttack(simulatedBoard);
    }

    /**
     * Determines if the Player's King is in the line of attack of any enemy Piece.
     * @param board The current board state.
     * @return  Boolean showing whether king is under attack (i.e. in check).
     */
    public boolean isKingUnderAttack(Board board) {
        Player enemy = this.getOpponent(board);
        return board.isPairUnderAttack(this.getLocationOfKing(), enemy);
    }

    /**
     * Determines if the Player's Pieces' list of possible moves contain the given Tile.
     * @param tile The Tile to search for.
     * @param gameBoard The game board.
     * @return  Boolean showing whether the tile can be attacked.
     */
    public boolean canAttack(Tile tile, Board gameBoard) {
        return gameBoard.isPairUnderAttack(tile.getPosition(), this);
    }
}