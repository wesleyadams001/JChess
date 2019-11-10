/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Enums;

/**
 * Indicates an associated move type.
 * @author Wesley
 */
public enum MoveType {

    /**
     * Piece can only move to an empty Tile.
     */
    EmptyTileOnly,

    /**
     * Piece can only move to a Tile occupied by enemy.
     */
    EnemyPieceOnly,

    /**
     * Piece can move to either an empty Tile or a Tile occupied by enemy.
     */
    EmptyOrEnemyPiece,

}
