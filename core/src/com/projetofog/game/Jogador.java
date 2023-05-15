package com.projetofog.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Jogador {
    private float posicaoX,posicaoY, tamanho, variacao;
    private Texture foto;

    public Jogador(float largura)
    {
        foto = new Texture("nave.png");
        tamanho = largura / 16;
        posicaoX = largura/2 - tamanho/2;
        posicaoY = largura / 30;
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
    }
}
