package com.fogodev.asteroidz;

/**
 * Criado por ericson em 26/05/16.
 *
 * https://github.com/fogodev
 */
public class Point
{
    private double positionX;
    private double positionY;

    public Point(double positionX, double positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void rotate(double direction)
    {
        // Aplica uma matriz de rotação nos pontos dados
        double newPositionX = this.positionX * Math.cos(direction) - this.positionY * Math.sin(direction);
        double newPositionY = this.positionY * Math.cos(direction) + this.positionX * Math.sin(direction);

        this.positionX = newPositionX;
        this.positionY = newPositionY;
    }
    // Getters das posições do ponto
    public double getPositionY()
    {
        return this.positionY;
    }

    public double getPositionX()
    {
        return this.positionX;
    }
}
