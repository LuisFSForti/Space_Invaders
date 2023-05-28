package com.projetofog.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.LinkedList;

//Classe que controla os tiros
public class Tiro {
    //Tamanho, posição e velocidade do tiro
    private float tamanhoX, tamanhoY, posicaoX, posicaoY, variacao;
    //Tipo de tiro
    private char tipo;
    //Sprite
    private Texture foto;

    //Ao criar um objeto da classe devem ser passados: posição, tipo e altura da tela
    public Tiro(float pX, float pY, char tipo, float altura)
    {
        //Define o tamanho, a posição inicial, o tipo de tiro e a velocidade
        this.tamanhoX = altura / 50;
        this.tamanhoY = altura / 20;
        this.posicaoY = pY;
        this.posicaoX = pX;
        this.tipo = tipo;
        this.variacao = altura / 50;
        //Caso o tiro seja do jogador
        if(tipo == 'j')
            //Pega o sprite do laser do jogador
            foto = new Texture("laserJogador.png");
        //Caso seja do inimigo
        else
            //Pega o sprite do laser do inimigo
            foto = new Texture("laserInimigo.png");
    }

    //Método responsável por mover os tiros e analisar se colidiram com algo
    public int tocouEmAlgo(Jogador naveJ, LinkedList<Inimigo> inimigos)
    {
        //Se tiver saído da tela
        if(posicaoY > variacao*100 || posicaoY < 0)
            //Retorna que precisa excluir o tiro
            return -1;

        //Caso seja o tiro de um jogador
        if(tipo == 'j')
        {
            //Se movimenta para cima
            posicaoY += variacao;

            //Para todos os inimigos
            for (Inimigo atual: inimigos) {
                //Caso o tiro esteja dentro da área horizontal do inimigo
                if(atual.getX() < posicaoX && posicaoX < (atual.getX() + atual.getTamanhoX()))
                {
                    //Caso o tiro esteja dentro da área vertical do inimigo
                    if(atual.getY() < posicaoY && posicaoY < (atual.getY() + atual.getTamanhoY()))
                        //Retorna o indíce do inimigo à ser ferido
                        return inimigos.indexOf(atual);
                }
            }

            //Nada
            return -3;
        }
        //Caso o tiro seja de um inimigo
        else
        {
            //Tiro avança para baixo
            posicaoY -= variacao / 2;

            //Caso o tiro esteja dentro da área horizontal do jogador
            if(naveJ.getX() < posicaoX && posicaoX < (naveJ.getX() + naveJ.getTamanho()))
            {
                //Caso o tiro esteja dentro da área vertical do jogador
                if(posicaoY < (naveJ.getY() + naveJ.getTamanho()))
                    //Retorna que o jogador foi ferido
                    return -2;
            }
            //Nada
            return -3;
        }
    }

    //Método para desenhar o tiro
    public void desenhar(Batch batch)
    {
        batch.draw(foto, posicaoX - tamanhoX/2, posicaoY, tamanhoX, tamanhoY);
    }
}
