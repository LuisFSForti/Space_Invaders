package com.projetofog.game;

import com.badlogic.gdx.Gdx;

public class AnalizarSeTocou {
    private float posicaoX, posicaoY;
    private float tamanhoX, tamanhoY, telaY;

    public AnalizarSeTocou(float tX, float tY, float pX, float pY, float tlY)
    {
        tamanhoX = tX;
        tamanhoY = tY;
        posicaoX = pX;
        posicaoY = pY;
        telaY = tlY;
    }

    public boolean tocou()
    {
        if(Gdx.input.isTouched())
        {
            float x = Gdx.input.getX();
            float y = Math.abs(Gdx.input.getY() - telaY);
            if(x > posicaoX && x < (posicaoX + tamanhoX))
            {
                if(y > posicaoY && y < (posicaoY + tamanhoY))
                    return true;
            }
        }
        return false;
    }
}
