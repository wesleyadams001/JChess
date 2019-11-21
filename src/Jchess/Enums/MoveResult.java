/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Enums;

/**
 * An enumeration that represents
 * @author nehalpatel
 */
public enum MoveResult {

    /**
     * Move was prevented because Player needs a Check-escaping move.
     */
    InvalidEscape,

    /**
     * Player is in check.
     */
    Check,

    /**
     * A Checkmate happened.
     */
    Checkmate,

    /**
     * Nothing fundamentally changed.
     */
    BetaMove,

}
