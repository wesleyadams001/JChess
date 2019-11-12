/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Enums.ThemeColor;
import Jchess.Core.Constants;

import java.util.Arrays;
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
     * Returns the Player's name.
     * @return 
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the Player's color.
     * @return 
     */
    public ThemeColor getColor() {
        return this.color;
    }
    
    /**
     * Returns the location of Player's King.
     * @return 
     */
    public Pair getLocationOfKing() {
        return this.kingPair;
    }
    
    /**
     * Updates the location of Player's King.
     * @param king 
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
     * @return 
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
     * @param board
     * @return 
     */
    public Stream<Piece> getPieces(Board board) {
        return Arrays
            .stream(board.getMatrix())
            // Get all the Tiles into one "array".
            .flatMap(Arrays::stream)
            // Only keep Tiles that 1) have a Piece 2) that's owned by the Player.
            .filter(tile -> tile.isOccupied() && tile.getPiece().isOwnedBy(this))
            // For every Tile, replace with inner Piece.
            .map(tile -> tile.getPiece());
    }

    /**
     * Get a list of all possible moves the Player's Pieces can make.
     * @param board
     * @return 
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
     * @param board
     * @return 
     */
    public boolean canKingBeSaved(Board board) {
        // Create a collection of all the Player's Pieces.
        Stream<Piece> allPieces = getPieces(board);
        // Resolves to true if any of the Player's Pieces can save the King.
        return allPieces.anyMatch(piece -> canPieceSaveKing(board, piece));
    }

    /**
     * Determines if a given allied Piece can make a move that would take the Player's King out of Check.
     * @param board
     * @param alliedPiece
     * @return 
     */
    private boolean canPieceSaveKing(Board board, Piece alliedPiece) {
        // Create a collection of possible moves the Piece can make.
        Stream<Pair> allMoves = alliedPiece.getPossibleMoves(board).stream();
        // Resolves to true if any of the Piece's possible moves can save the King.
        return allMoves.anyMatch(desination -> canMoveSaveKing(board, alliedPiece, desination));
    }

    /**
     * Determines if a given move by an allied Piece can take the Player's King out of Check.
     * @param board
     * @param alliedPiece
     * @param destination
     * @return 
     */
    private boolean canMoveSaveKing(Board board, Piece alliedPiece, Pair destination) {
        // Move allied Piece to destination Pair.
        Board simulatedBoard = board.simulatedWithMove(alliedPiece, board.getTile(destination));
        // If false, King can be saved.
        return !simulatedBoard.getCurrentPlayer().isKingUnderAttack(simulatedBoard);
    }

    /**
     * Determines if the Player's King is in the line of attack of any enemy Piece.
     * @param board
     * @return
     */
    public boolean isKingUnderAttack(Board board) {
        Player enemy;

        if (this.getColor() == ThemeColor.LightPiece) {
            enemy = board.getDarkPlayer();
        } else {
            enemy = board.getLightPlayer();
        }

        return board.isPairUnderAttack(this.getLocationOfKing(), enemy);
    }

    /**
     * Determines if the Player's Pieces' list of possible moves contain the given Tile.
     * @param tile The Tile to search for.
     * @param gameBoard The game board.
     * @return
     */
    public boolean canAttack(Tile tile, Board gameBoard) {
        return gameBoard.isPairUnderAttack(tile.getPosition(), this);
    }
}