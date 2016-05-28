package com.fogodev.asteroidz;

/**
 * Criado por ericson em 26/05/16.
 *
 * https://github.com/fogodev
 */
public class Asteroid implements GameObject
{
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private int size;
    private Cor color;

    public Asteroid(double positionX, double positionY, double velocityX, double velocityY, int size, Cor color)
    {
        // Esse construtor é usado pra gerar asteroides menores a partir de asteroides grandes destruídos
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.size = size;
        this.color = color;
    }

    public Asteroid(double positionX, double positionY)
    {
        // Construtor pra gerar asteroides aleatórios
        this.positionX = positionX;
        this.positionY = positionY;

        if (((int) (Math.random() * 100)) % 2 == 1) {
            this.velocityX *= -1;
        }

        if (((int) (Math.random() * 100)) % 2 == 1) {
            this.velocityY *= -1;
        }

        this.velocityX = Math.random() * 200;
        this.velocityY = Math.random() * 200;
        this.size = 1 + (int) (Math.random() * 4);
        this.color = new Cor(Math.random(), Math.random(), Math.random());

    }
    @Override
    public void draw(Tela canvas)
    {
        // Desenha o asteroide na tela
        canvas.circulo(this.positionX, this.positionY, this.size * 10, this.color);
    }

    @Override
    public void move(double dTime, Jogo game)
    {
        // Faz a movimentação dos asteoires, aparecendo do lado oposto do mapa se sair do mesmo
        this.positionX += this.velocityX * dTime;
        if (this.positionX >= game.getLargura() + this.size * 10) {
            this.positionX = -this.size * 10;
        }

        if (this.positionX < -this.size * 10) {
            this.positionX = game.getLargura() + this.size * 10;
        }

        this.positionY += this.velocityY * dTime;
        if (this.positionY >= game.getAltura() + this.size * 10) {
            this.positionY = -this.size * 10;
        }

        if (this.positionY < -this.size * 10) {
            this.positionY = game.getAltura() + this.size * 10;
        }
    }

    // Getters dos atributos dos asteroides
    public double getPositionX()
    {
        return positionX;
    }

    public double getPositionY()
    {
        return positionY;
    }

    public int getSize()
    {
        return size;
    }

    public double getVelocityX()
    {
        return velocityX;
    }

    public double getVelocityY()
    {
        return velocityY;
    }

    public Cor getColor()
    {
        return color;
    }


}
