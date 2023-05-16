package com.projetofog.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.LinkedList;

public class Inimigo {
    private float posicaoX, posicaoY, tamanhoX, tamanhoY, largura, altura, variacao;
    private int vida;
    private Texture foto;

    public Inimigo(float pX, float pY, float tX, float tY, float largura, float altura, int vida)
    {
        this.posicaoX = pX;
        this.posicaoY = pY;
        this.tamanhoX = tX;
        this.tamanhoY = tY;
        this.largura = largura;
        this.altura = altura;
        variacao = largura/750;
        this.vida = vida;
        if(vida == 1)
            foto = new Texture("inimigo.png");
    }

    public int andar()
    {
        posicaoX += variacao;

        if(posicaoY < (float)(altura / 15.0 + largura / 16))
            return 2;

        if(posicaoX > largura - largura / 24 - tamanhoX || posicaoX < largura / 24)
            return 1;

        return 0;
    }

    public void alternar()
    {
        variacao *= -1;
        posicaoY -= altura / 30;
    }

    public void mudarVelocidade(float mudanca)
    {
        if(mudanca > 0)
            variacao += variacao * mudanca;
    }

    public void desenhar(Batch batch)
    {
        batch.draw(foto, posicaoX, posicaoY, tamanhoX, tamanhoY);
    }

    public void atirar(LinkedList<Tiro> tiros)
    {
        tiros.add(new Tiro(posicaoX + tamanhoX / 2, posicaoY - altura / 50, 'I', altura));
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

    public float getTamanhoX()
    {
        return tamanhoX;
    }

    public float getTamanhoY()
    {
        return tamanhoY;
    }

    public boolean estaVivo()
    {
        return vida > 0;
    }
}
