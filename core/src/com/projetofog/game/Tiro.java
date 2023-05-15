package com.projetofog.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.LinkedList;

public class Tiro {
    private float tamanhoX, tamanhoY, posicaoX, posicaoY, variacao;
    private char tipo;
    private Texture foto;

    public Tiro(float pX, float pY, char tipo, float altura)
    {
        this.tamanhoX = altura / 50;
        this.tamanhoY = altura / 20;
        this.posicaoY = pY;
        this.posicaoX = pX;
        this.tipo = tipo;
        this.variacao = altura / 100;
        if(tipo == 'j')
            foto = new Texture("laserJogador.png");
        else
            foto = new Texture("laserInimigo.png");
    }

    public int tocouEmAlgo(Jogador naveJ, LinkedList<Inimigo> inimigos)
    {
        if(posicaoY > variacao*100 || posicaoY < 0)
            return -1; //Deletar o tiro

        if(tipo == 'j')
        {
            posicaoY += variacao;
            for (int i = 0; i < inimigos.size(); i++) {
                if(inimigos.get(i).getX() < posicaoX && posicaoX < (inimigos.get(i).getX() + inimigos.get(i).getTamanhoX()))
                {
                    if(inimigos.get(i).getY() < posicaoY && posicaoY < (inimigos.get(i).getY() + inimigos.get(i).getTamanhoY()))
                        return i; //Deletar o inimigo
                }
            }

            return -3; //Nada
        }
        else
        {
            posicaoY -= variacao;
            if(naveJ.getX() < posicaoX && posicaoX < (naveJ.getX() + naveJ.getTamanho()))
            {
                if(posicaoY < (naveJ.getY() + naveJ.getTamanho()))
                    return -2; //Tirar uma vida
            }
            return -3; //Nada
        }
    }

    public void desenhar(Batch batch)
    {
        batch.draw(foto, posicaoX - tamanhoX/2, posicaoY, tamanhoX, tamanhoY);
    }
}
