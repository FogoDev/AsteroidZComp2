package com.fogodev.asteroidz;

/**
 * Criado por ericson em 26/05/16.
 * <p>
 * https://github.com/fogodev
 */
public interface GameObject
{
    // Interface pra definir os objetos do jogo, que são a nave, os asteroides e os tiros
    void draw(Tela canvas);
    
    void move(double dTime, Jogo game);
}
