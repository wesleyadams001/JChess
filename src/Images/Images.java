/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Images;

import Enums.PieceType;
import Enums.ThemeColor;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 * @author Wesley
 * Contains the mappings for chess pieces
 */
public class Images {

    public static HashMap<PieceType, HashMap<ThemeColor, ImageIcon>> Icons = new HashMap<>();

    /**
     * Initialize all the image mappings for the chess Pieces.
     */
    public void loadImages() {
        try {
            HashMap<ThemeColor, ImageIcon> pawnIcons = new HashMap<>();
            pawnIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_pawn.png"))));
            pawnIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_pawn.png"))));
            Icons.put(PieceType.Pawn, pawnIcons);

            HashMap<ThemeColor, ImageIcon> rookIcons = new HashMap<>();
            rookIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_castle.png"))));
            rookIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_castle.png"))));
            Icons.put(PieceType.Rook, rookIcons);

            HashMap<ThemeColor, ImageIcon> knightIcons = new HashMap<>();
            knightIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_horse.png"))));
            knightIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_horse.png"))));
            Icons.put(PieceType.Knight, knightIcons);

            HashMap<ThemeColor, ImageIcon> bishopIcons = new HashMap<>();
            bishopIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_bishop.png"))));
            bishopIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_bishop.png"))));
            Icons.put(PieceType.Bishop, bishopIcons);

            HashMap<ThemeColor, ImageIcon> queenIcons = new HashMap<>();
            queenIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_queen.png"))));
            queenIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_queen.png"))));
            Icons.put(PieceType.Queen, queenIcons);

            HashMap<ThemeColor, ImageIcon> kingIcons = new HashMap<>();
            kingIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_king.png"))));
            kingIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_king.png"))));
            Icons.put(PieceType.King, kingIcons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
