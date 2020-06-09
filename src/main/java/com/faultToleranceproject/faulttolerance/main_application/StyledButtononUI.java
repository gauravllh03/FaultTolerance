package com.faultToleranceproject.faulttolerance.main_application;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;




/* 
 * A class to style individual buttons
 * used in the swing application
 * 
 */

public class StyledButtononUI extends BasicButtonUI {

	
    @Override
    public void installUI (JComponent jComponent) {
        super.installUI(jComponent);
        AbstractButton button = (AbstractButton) jComponent;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint (Graphics graphic, JComponent jComponent) {
        AbstractButton button = (AbstractButton) jComponent;
        paintBackground(graphic, button, button.getModel().isPressed() ? 2 : 0);
        super.paint(graphic, jComponent);
    }

    private void paintBackground (Graphics graphic, JComponent jComponent, int yOffset) {
        Dimension size = jComponent.getSize();
        Graphics2D graphics2D = (Graphics2D) graphic;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphic.setColor(jComponent.getBackground().darker());
        graphic.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        graphic.setColor(jComponent.getBackground());
        graphic.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }
}