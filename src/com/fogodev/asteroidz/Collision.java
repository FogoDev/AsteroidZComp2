package com.fogodev.asteroidz;

import java.util.Collections;

/**
 * Criado por ericson em 27/05/16.
 *
 * https://github.com/fogodev
 */
public abstract class Collision
{
    private Collision(){ /* Construtor vazio e privado, pra evitar herança da classe */ }

    // Verifica colisão de um tiro e um asteroide, usando módulo de vetores
    public static boolean checkShootAsteroidCollision(Shoot shoot, Asteroid asteroid)
    {
        return Math.sqrt(Math.pow(asteroid.getPositionX() - shoot.getPositionX(), 2) + Math.pow(asteroid.getPositionY() - shoot.getPositionY(), 2)) < asteroid.getSize() * 10;
    }

    // Verifica colisão da nave e um asteroide, usando módulo de vetores
    public static boolean checkSpaceshipAsteroidCollision(Spaceship spaceship, Asteroid asteroid)
    {
        return Math.sqrt(Math.pow(asteroid.getPositionX() - spaceship.getPositionX(), 2) + Math.pow(asteroid.getPositionY() - spaceship.getPositionY(), 2)) < asteroid.getSize() * 10 + 5;
    }
}
