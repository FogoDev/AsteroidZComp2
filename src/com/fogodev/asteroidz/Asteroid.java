package com.fogodev.asteroidz;


public class Asteroid
{
    private double positionX;
    private double positionY;
    private double velocityX = Math.random() * 200;
    private double velocityY = Math.random() * 200;
    private int size = 1 + (int)(Math.random() * 4);
    private Cor color = new Cor(Math.random(), Math.random(), Math.random());

    public Asteroid(double positionX, double positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;

        if(((int)(Math.random() * 100)) % 2 == 1){
            this.velocityX *= -1;
        }

        if(((int)(Math.random() * 100)) % 2 == 1){
            this.velocityY *= -1;
        }
    }

    public void draw(Tela canvas)
    {
        canvas.circulo(this.positionX, this.positionY, this.size * 10, this.color);
    }

    public void move(double dTime, Jogo game)
    {
        this.positionX += this.velocityX * dTime;
        if(this.positionX >= game.getLargura() + this.size * 10){
            this.positionX = - this.size * 10;
        }

        if(this.positionX < - this.size * 10){
            this.positionX = game.getLargura() + this.size * 10;
        }

        this.positionY += this.velocityY * dTime;
        if(this.positionY >= game.getAltura() + this.size * 10){
            this.positionY = -this.size * 10;
        }

        if(this.positionY < - this.size * 10){
            this.positionY = game.getAltura() + this.size * 10;
        }
    }
}
