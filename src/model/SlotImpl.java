package model;

import java.util.Objects;

import com.sun.tools.javac.util.StringUtils;

import model.enumeration.Color;
import model.interfaces.Slot;

public class SlotImpl implements Slot {
    
    private Color color;
    private int position;
    private int number;
    
    /** Constructor for SlotImp class **/
    public SlotImpl(int position, Color color, int number) {
        this.color = color;
        this.position = position;
        this.number = number;
    }

    @Override
    public int getPosition() {
        return this.position; // Return the position of slot
    }

    @Override
    public int getNumber() {
        return this.number; // Return the number of slot
    }

    @Override
    public Color getColor() {
        return this.color; // Return the color of slot
    }

    @Override
    public boolean equals(Slot slot) {

        if (this == slot) {
            return true;
        }

        if (!(slot instanceof Slot) || slot == null) {
            return false;
        }

        if (this.color == null) {
            if (slot.getColor() != null) {
                return false;
            }
        } else if (!this.color.equals(slot.getColor())) {
            return false;
        }

        if (this.number != slot.getNumber()) {
            return false;
        }

        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.color, this.number); // Generate a hash code base on the color and number
    }
    
    @Override
    public String toString() {
        return String.format("Position: %s, Color: %s, Number: %d", this.position, this.color.name().charAt(0) + this.color.name().substring(1).toLowerCase(), this.number);
    }
}
