/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;

import Jchess.Core.Constants;

/**
 *
 * @author nehalpatel
 */
public class Toaster extends JFrame {

    private static final long serialVersionUID = 1815212842342855720L;

    /**
     * Prevent instantiation.
     */
    private Toaster() { }

    private static JWindow push(String tart) {
        JWindow toast = new JWindow();

        JPanel bread = new JPanel() {
            private static final long serialVersionUID = -2993518605225003967L;

            @Override
            public void paintComponent(Graphics g) {
                g.setFont(g.getFont().deriveFont(50));
                int width = g.getFontMetrics().stringWidth(tart);
                int height = g.getFontMetrics().getHeight();
                toast.setSize(width + 50, height + 30);

                // Draw the boundary of the toast and fill it.
                g.setColor(Color.black);
                g.fillRect(10, 10, width + 30, height + 10);
                g.setColor(Color.black);
                g.drawRect(10, 10, width + 30, height + 10);

                // Set the color of text.
                g.setColor(new Color(255, 255, 255, 240));
                g.drawString(tart, 25, 27);

                // Draw the shadow of the toast.
                int t = 250;
                for (int i = 0; i < 4; i++) {
                    t -= 60;
                    g.setColor(new Color(0, 0, 0, t));
                    g.drawRect(10 - i, 10 - i, width + 30 + i * 2, height + 10 + i * 2);
                }
            }
        };

        toast.add(bread);

        // Make the background transparent.
        toast.setBackground(new Color(0, 0, 0, 0));

        // Spawn where the user clicked.
        toast.setLocation(
            MouseInfo.getPointerInfo().getLocation().x,
            MouseInfo.getPointerInfo().getLocation().y
        );

        return toast;
    }

    /**
     * Displays a toast message near the mouse pointer.
     * @param tart The message to display.
     */
    public static void pop(String tart) {
        JWindow toast = Toaster.push(tart);

        toast.setOpacity(1);
        toast.setVisible(true);

        long startTime = System.currentTimeMillis();
        long endTime = startTime + Constants.TOAST_LIFESPAN;

        Timer timer = new Timer(40, (ActionEvent e) -> {
            long currentTime = e.getWhen();

            if (currentTime >= endTime) {
                ((Timer) e.getSource()).stop();
                toast.setVisible(false);
                toast.dispose();
            } else {
                long elapsedTime = currentTime - startTime;
                float newOpacity = 1 - ((float) elapsedTime / Constants.TOAST_LIFESPAN);
                toast.setOpacity(newOpacity);
            }
        });

        timer.start();
    }
}
