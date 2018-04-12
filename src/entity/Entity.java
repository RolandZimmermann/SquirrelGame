package entity;

import general.Direction;
import general.XY;

public abstract class Entity {

    private int id;
    private XY position;
    private int energy;
    
    test;
    
    public Entity(int id, int X, int Y, int energy) {

        this.id = id;
        XY position = new XY (X, Y);
        this.energy = energy;
    }
    
    public Entity(int id, XY position, int energy) {

        this.id = id;
        this.position = position;
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    public void updateEnergy(int energy) {
        this.energy +=energy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPositionXY(int deltaX, int deltaY) {
        position.move(deltaX, deltaY);
    }

    @Override
    public String toString() {
        return "Entity [id=" + id + ", position=" + position + ", energy=" + energy + "]";
    }

    public XY getPositionXY() {
        return position;
    }

    public abstract void nextStep();

}
