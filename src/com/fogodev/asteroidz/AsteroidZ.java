package com.fogodev.asteroidz;


import java.util.Set;

public class AsteroidZ implements Jogo {

    private Asteroid asteroids[];

    public AsteroidZ()
    {
        this.asteroids = new Asteroid[6];

        for(int i = 0; i < 6; i++){
            this.asteroids[i] = new Asteroid(Math.random() * this.getLargura(), Math.random() * this.getAltura());
        }
    }

    @Override
    public String getTitulo() {
        return "AsteroidZ";
    }

    @Override
    public int getLargura() {
        return 1024;
    }

    @Override
    public int getAltura() {
        return 768;
    }

    @Override
    public void tique(Set<String> teclas, double dt) {
        for(Asteroid asteroid : this.asteroids)
            asteroid.move(dt, this);
    }

    @Override
    public void desenhar(Tela tela) {
        for(Asteroid asteroid : this.asteroids)
            asteroid.draw(tela);
    }

    @Override
    public void tecla(String tecla) {

    }

    public static void main(String[] args)
    {
        new Motor(new AsteroidZ());
    }
}
