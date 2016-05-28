package com.fogodev.asteroidz;

import java.util.HashSet;
import java.util.Set;

/**
 * Criado por ericson em 26/05/16.
 *
 * https://github.com/fogodev
 */
public class Spaceship implements GameObject
{
    private double positionX;
    private double positionY;
    private double velocity = 100;
    private double directionAngle = 0;
    private Cor color = new Cor(232, 44, 12);
    

    public Spaceship(double positionX, double positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    public void draw(Tela canvas)
    {
        // Mecânica de rotação do desenho da nave
        Point[] triangleVertices = new Point[3];
        triangleVertices[0] = new Point(5, 0);
        triangleVertices[1] = new Point(-3, -4);
        triangleVertices[2] = new Point(-3, 4);

        for(Point point : triangleVertices){
            point.rotate(this.directionAngle);
        }

        canvas.triangulo(
                triangleVertices[0].getPositionX() + this.positionX,
                triangleVertices[0].getPositionY() + this.positionY,
                triangleVertices[1].getPositionX() + this.positionX,
                triangleVertices[1].getPositionY() + this.positionY,
                triangleVertices[2].getPositionX() + this.positionX,
                triangleVertices[2].getPositionY() + this.positionY,
                this.color
        );
    }

    @Override
    public void move(double dTime, Jogo game)
    {
        // Faz a movimentação da nave, aparecendo do lado oposto do mapa se sair do mesmo
        this.positionX += Math.cos(this.directionAngle) * this.velocity * dTime;
        if (this.positionX >= game.getLargura() + 5) {
            this.positionX = - 5;
        }
        if (this.positionX < -5) {
            this.positionX = game.getLargura() + 5;
        }


        this.positionY += Math.sin(this.directionAngle) * this.velocity * dTime;
        if (this.positionY >= game.getAltura() + 10) {
            this.positionY = -5;
        }

        if (this.positionY < -5) {
            this.positionY = game.getAltura() + 5;
        }
    }

    // Faz os giros pra esquerda e pra direita
    public void turnRight(double dTime)
    {
        this.directionAngle += Math.PI * dTime;
    }

    public void turnLeft(double dTime)
    {
        this.directionAngle -= Math.PI * dTime;
    }

    // Dispara um novo tiro
    public Shoot fireCannons()
    {
        return new Shoot(this.positionX, this.positionY, this.directionAngle, this.velocity);
    }

    // Getters da posição da nave
    public double getPositionX()
    {
        return this.positionX;
    }

    public double getPositionY()
    {
        return this.positionY;
    }
}
