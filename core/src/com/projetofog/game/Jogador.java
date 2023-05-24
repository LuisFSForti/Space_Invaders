package com.projetofog.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Jogador {
    private float posicaoX,posicaoY, tamanho, variacao, altura;
    private int vida;
    private Texture foto;

    public Jogador(float largura, float altura)
    {
        this.foto = new Texture("nave.png");
        this.tamanho = largura / 16;
        this.posicaoX = largura/2 - tamanho/2;
        this.posicaoY = largura / 30;
        this.vida = 2;
        this.altura = altura;
    }

    public void setVariacao(float aumento)
    {
        variacao = aumento;
    }
    public void aumentarX()
    {
        posicaoX += variacao;
        if(posicaoX < 20)
            posicaoX = 20;
        if (posicaoX > tamanho * 16 - (tamanho + 20))
            posicaoX = tamanho * 16 - (tamanho + 20);
    }

    public void desenhar(Batch batch)
    {
        batch.draw(foto, posicaoX, posicaoY, tamanho, tamanho);
        float posicao = tamanho / 20;
        for(int i = 0; i < vida; i++) {
            batch.draw(foto, posicao, altura - tamanho / 20 - tamanho / 3, tamanho / 3, tamanho / 3);
            posicao += tamanho / 3;
        }
    }

    public void mudarVida(int mudanca)
    {
        vida += mudanca;
    }

    public float getX()
    {
        return posicaoX;
    }

    public float getY()
    {
        return posicaoY;
    }

    public float getTamanho()
    {
        return tamanho;
    }

    public boolean estaVivo()
    {
        return vida > 0;
    }
}
