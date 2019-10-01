/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Images;

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

	public static HashMap<ThemeColor, ImageIcon> pawnIcons = new HashMap<>();
	public static HashMap<ThemeColor, ImageIcon> castleIcons = new HashMap<>();
	public static HashMap<ThemeColor, ImageIcon> horseIcons = new HashMap<>();
	public static HashMap<ThemeColor, ImageIcon> bishopIcons = new HashMap<>();
	public static HashMap<ThemeColor, ImageIcon> queenIcons = new HashMap<>();
	public static HashMap<ThemeColor, ImageIcon> kingIcons = new HashMap<>();
	public static HashMap<ThemeColor, ImageIcon> wazirIcons = new HashMap<>();
	public static HashMap<ThemeColor, ImageIcon> berolinaIcons = new HashMap<>();

	/**
	 * Initialize all the images image mappings for the
         * chess pieces
	 */
	public void loadImages() {
		
		//white pawn
		try {
			pawnIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_pawn.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white castle
		try {
			castleIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_castle.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white horse
		try {
			horseIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_horse.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white bishop
		try {
			bishopIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_bishop.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white queen
		try {
			queenIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_queen.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white king
		try {
			kingIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_king.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//white wazir
		try {
			wazirIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_wazir.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//white berolina
		try {
			berolinaIcons.put(ThemeColor.LightPiece, new ImageIcon(ImageIO.read(new File("src/Images/White_berolina.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black pawn
		try {
			pawnIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_pawn.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black castle
		try {
			castleIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_castle.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black horse
		try {
			horseIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_horse.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black bishop
		try {
			bishopIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_bishop.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black queen
		try {
			queenIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_queen.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black king
		try {
			kingIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_king.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//black wazir
		try {
			wazirIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_wazir.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//black berolina
		try {
			berolinaIcons.put(ThemeColor.DarkPiece, new ImageIcon(ImageIO.read(new File("src/Images/Black_berolina.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
