package com.projetofog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Classe responsável por animas as explosões
public class Explosao {
    //Contador de tempo
    private int frame;
    //Sprites de cada frame da animação
    private Texture animacao1, animacao2, animacao3, animacao4;
    //Posição e duração da animação
    private float x, y, duracao;
    //Efeito sonoro de explosão
    private Music explosao;

    //Ao criar um objeto da classe devem ser passados: posição em x, em y e a duração da animação
    public Explosao(float x, float y, float duracao)
    {
        //Pega as texturas
        animacao1 = new Texture(Gdx.files.internal("explosao1.png"));
        animacao2 = new Texture(Gdx.files.internal("explosao2.png"));
        animacao3 = new Texture(Gdx.files.internal("explosao3.png"));
        animacao4 = new Texture(Gdx.files.internal("explosao4.png"));
        //Define que o contador começa em 0
        frame = 0;
        //Salva a posição
        this.x = x;
        this.y = y;
        //Pega o áudio
        explosao = Gdx.audio.newMusic(Gdx.files.internal("explosao.mp3"));
        //Define o volume, define que ele não deve entrar em loop e toca o áudio
        explosao.setVolume(0.075F);
        explosao.setLooping(false);
        explosao.play();
        //Salva a duração da animação
        this.duracao = duracao;
    }

    //Método que controla a animação
    public boolean animar(SpriteBatch batch)
    {
        //Se não tiver terminado
        if(frame < 4 * duracao / 3) {
            //Se estiver no começo
            if (frame < duracao / 3) {
                //Desenha o primeiro frame
                batch.draw(animacao1, x, y);
            }
            //Caso esteja na segunda parte
            else if (frame < 2 * duracao / 3) {
                //Desenha o segundo frame
                batch.draw(animacao2, x, y);
            }
            //Caso esteja na terceira parte
            else if (frame < duracao) {
                //Desenha o terceiro frame
                batch.draw(animacao3, x, y);
            }
            //Caso esteja na quarta parte
            else {
                //Desenha o quarto frame
                batch.draw(animacao4, x, y);

                //Este último frame é uma imagem vazia, seu objetivo é adicionar um intervalo entre
                //a explosão acabar e a classe reconhecer seu fim
            }
            //Aumenta o tempo passao
            frame++;

            //Retorna que ainda está animando
            return true;
        }
        //Caso tenha terminado
        else
        {
            //Retorna que não está mais animando
            return false;
        }
    }
}