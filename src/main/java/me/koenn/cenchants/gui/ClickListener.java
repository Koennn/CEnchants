package me.koenn.cenchants.gui;

/**
 * Interface allowing you to listen for when an <code>Option</code> in a <code>Gui</code> is clicked.
 */
public interface ClickListener {

    /**
     * Will be called when the <code>Option</code> has been clicked.
     *
     * @param clickedSlot Slot in which the option was clicked
     */
    void click(int clickedSlot);
}