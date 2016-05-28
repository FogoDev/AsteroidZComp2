package com.fogodev.asteroidz;

/**
 * Criado por ericson em 26/05/16.
 *
 * https://github.com/fogodev
 */
public class Shoot implements GameObject
{
    private double positionX;
    private double positionY;
    private double velocity = 100;
    private double directionAngle = 0;
    private boolean mustBeDestroyed = false;

    private Cor color = Cor.BRANCO;

    public Shoot(double positionX, double positionY, double directionAngle, double velocity)
    {
        // Pega os atributos de acordo com os atributos da nave
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocity += velocity;
        this.directionAngle = directionAngle;
    }

    @Override
    public void draw(Tela canvas)
    {
        // Desenha o tiro na tela
        canvas.circulo(this.positionX, this.positionY, 1, this.color);
    }

    @Override
    public void move(double dTime, Jogo game)
    {
        // Faz a movimentação de tiros pelo mapa, marcando os tiros para serem tirados do sistema quando saírem do mapa
        this.positionX += Math.cos(this.directionAngle) * this.velocity * dTime;
        if (this.positionX >= game.getLargura() + 5) {
            this.mustBeDestroyed = true;
        }

        if (this.positionX < -5) {
            this.mustBeDestroyed = true;
        }

        this.positionY += Math.sin(this.directionAngle) * this.velocity * dTime;
        if (this.positionY >= game.getLargura() + 5) {
            this.mustBeDestroyed = true;
        }

        if (this.positionY < -5) {
            this.mustBeDestroyed = true;
        }
    }

    // Getters das posições do tiro e da condição do tiro ser eliminado do sistema
    public double getPositionX()
    {
        return this.positionX;
    }

    public double getPositionY()
    {
        return this.positionY;
    }

    public boolean getMustBeDestroyed()
    {
        return this.mustBeDestroyed;
    }
}
