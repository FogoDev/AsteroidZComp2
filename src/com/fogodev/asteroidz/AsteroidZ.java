package com.fogodev.asteroidz;


import java.util.HashSet;
import java.util.Set;

/**
 * Criado por ericson em 26/05/16.
 *
 * https://github.com/fogodev
 */
public class AsteroidZ implements Jogo
{
    // Prepara os atributos principais do jogo, a nave, os asteroides e os tiros da nave
    private Spaceship spaceship;
    private Set<Asteroid> asteroids = new HashSet<Asteroid>();
    private Set<Shoot> shoots = new HashSet<Shoot>();

    // Atributos internos do jogo, a quantidade de vidas e a pontuação do jogador
    private int lifes = 3;
    private int points = 0;

    // Uma variável contador para mostrar uma mensagem de ajuda para o jogador e a mensagem do desenvolvedor
    private int showHelpMessage = 0;
    private int showDevMessage = 0;

    // Atributos da mecânica de criação e destruição de asteroides, e destruição de tiros
    private int asteroidsRemoved = 0;
    private Set<Asteroid> asteroidsToInclude = new HashSet<Asteroid>();
    private Set<Asteroid> asteroidsToRemove = new HashSet<Asteroid>();
    private Set<Shoot> shootsToRemove = new HashSet<Shoot>();

    public AsteroidZ()
    {
        // Criando a nave no meio da tela
        this.spaceship = new Spaceship(this.getLargura() / 2, this.getAltura() / 2);

        // Criando os 6 asteroides iniciais do jogo
        for(int i = 0; i < 6; i++){
            this.asteroids.add(new Asteroid(Math.random() * this.getLargura(), Math.random() * this.getAltura()));
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
        // Faz a movimentação dos asteroides
        for(Asteroid asteroid : this.asteroids)
            asteroid.move(dt, this);

        // Caso as vidas tenham acabado, impede as ações do jogador
        if(this.lifes > 0){
            // Ações do jogador, para a movimentação da nave
            if(teclas.contains("left") || teclas.contains("a")) spaceship.turnLeft(dt);
            if(teclas.contains("right") || teclas.contains("d")) spaceship.turnRight(dt);
            if(teclas.contains("up") || teclas.contains("w")) spaceship.move(dt, this);
        }

        // Faz a movimentação dos tiros na tela, marcando os tiros que saíram da tela pra serem tirados do sistema
        for(Shoot shoot : this.shoots){
            shoot.move(dt, this);
            if(shoot.getMustBeDestroyed()){
                this.shootsToRemove.add(shoot);
            }
        }

        // Faz a iteração entre todos os asteroides do jogo
        for(Asteroid asteroid : this.asteroids){
            // Faz a iteração entre os tiros do jogo, pra destruição de asteroides
            for(Shoot shoot : this.shoots){
                if(Collision.checkShootAsteroidCollision(shoot, asteroid)){
                    this.shootsToRemove.add(shoot);
                    this.points += 10;
                    if(this.points % 1000 == 0)
                        this.lifes++;
                    this.asteroidsToRemove.add(asteroid);
                    this.asteroidsRemoved++;
                    switch (asteroid.getSize()){
                        case 3:
                            this.asteroidsToInclude.add(new Asteroid(asteroid.getPositionX(), asteroid.getPositionY(), asteroid.getVelocityY(), asteroid.getVelocityX(), 1, asteroid.getColor()));
                            this.asteroidsToInclude.add(new Asteroid(asteroid.getPositionX(), asteroid.getPositionY(), - asteroid.getVelocityX(), - asteroid.getVelocityY(), 1, asteroid.getColor()));
                            break;
                        case 4:
                            this.asteroidsToInclude.add(new Asteroid(asteroid.getPositionX(), asteroid.getPositionY(), asteroid.getVelocityY(), asteroid.getVelocityX(), 2, asteroid.getColor()));
                            this.asteroidsToInclude.add(new Asteroid(asteroid.getPositionX(), asteroid.getPositionY(), - asteroid.getVelocityX(), - asteroid.getVelocityY(), 1, asteroid.getColor()));
                            break;
                        default:
                            break;
                    }
                }
            }
            // Verifica as vidas do jogador para tirar uma vida em caso de colisão da nave com algum asteroide
            if(this.lifes > 0)
                if(Collision.checkSpaceshipAsteroidCollision(spaceship, asteroid)){
                    this.asteroidsToRemove.add(asteroid);
                    this.lifes--;
                    this.spaceship = new Spaceship(this.getLargura() / 2, this.getAltura() / 2);

                }

        }

        // Remove os tiros e asteroides marcados pra serem destruídos
        this.shoots.removeAll(this.shootsToRemove);
        this.asteroids.removeAll(this.asteroidsToRemove);

        // Faz a inclusão de novos asteroides de tamanho menor, a partir dos asteroides de tamanho maior que foram destruídos
        this.asteroids.addAll(this.asteroidsToInclude);
        this.asteroidsToInclude = new HashSet<Asteroid>();


        // A cada 4 asteroides destruídos, gera 2 novos asteroides, para aumentar a dificuldade do jogo gradativamente, e o jogador não ficar sozinho no mapa
        if(this.asteroidsRemoved >= 4) {
            this.asteroidsRemoved = 0;
            for (int i = 0; i < 2; i++) {
                if ((Math.random() * 100) % 2 == 1) {
                    this.asteroids.add(new Asteroid(Math.random() * this.getLargura() - this.getAltura(), Math.random() * this.getAltura() - this.getLargura()));
                } else {
                    this.asteroids.add(new Asteroid(Math.random() * this.getLargura() + this.getLargura(), Math.random() * this.getAltura() + this.getAltura()));
                }
            }
        }

        // Faz uma verificação nos tamanhos dos HashSets de tiros e de asteroides pra fazer uma limpeza de tempos em tempos
        if(this.shootsToRemove.size() > 50)
            this.shootsToRemove = new HashSet<Shoot>();
        if(this.asteroidsToRemove.size() > 50)
            this.asteroidsToRemove = new HashSet<Asteroid>();

    }

    @Override
    public void desenhar(Tela tela) {

        // Faz a renderização dos asteroides e dos tiros na tela
        for(Asteroid asteroid : this.asteroids)
            asteroid.draw(tela);
        for(Shoot shoot : this.shoots)
            shoot.draw(tela);

        // Verifica a quantidade de vidas do jogador, para renderizar a nave ou para exibir a mensagem de game over
        if(this.lifes > 0)
            this.spaceship.draw(tela);
        else
            tela.texto("GAME OVER", this.getLargura()/5, this.getAltura()/2, this.getLargura() / 10, Cor.BRANCO);

        // Renderiza os textos das vidas e da pontuação do jogador
        tela.texto("" + this.lifes, this.getLargura() - 60, 60, 60, Cor.BRANCO );
        tela.texto("" + this.points, 60, 60, 60, Cor.BRANCO );

        if(this.showHelpMessage < 10) {
            tela.texto("Pressione [W] ou [↑] para acelerar os motores", this.getLargura() / 3 - 40, this.getAltura() - this.getAltura() / 5, 20, Cor.BRANCO);
            tela.texto("Pressione [A] ou [S] ou [←] ou [→] para girar a nave", this.getLargura() / 3 - 70, this.getAltura() - this.getAltura() / 5 + 30, 20, Cor.BRANCO);
            tela.texto("Pressione [K] para disparar os canhões laser", this.getLargura() / 3 - 35, this.getAltura() - this.getAltura() / 5 + 60, 20, Cor.BRANCO);
        } else if(this.showDevMessage > 15 && this.showDevMessage < 30){
            tela.texto("Desenvolvido por Ericson \"Fogo\" Soares", this.getLargura() / 3 - 20, this.getAltura() - this.getAltura() / 5, 20, Cor.BRANCO);
            tela.texto("github.com/fogodev/AsteroidZComp2", this.getLargura() / 3, this.getAltura() - this.getAltura() / 5 + 50, 20, Cor.BRANCO);
        }

    }

    @Override
    public void tecla(String tecla)
    {
        // Captura os eventos de apertar a tecla para atirar
        if(this.lifes > 0)
            if(tecla.equals("space") || tecla.equals("k")) {
                this.shoots.add(spaceship.fireCannons());
                if(this.showHelpMessage < 10)
                    this.showHelpMessage++;
                if(this.showDevMessage < 30)
                    this.showDevMessage++;
            }

    }

    public static void main(String[] args)
    {
        new Motor(new AsteroidZ());
    }
}
