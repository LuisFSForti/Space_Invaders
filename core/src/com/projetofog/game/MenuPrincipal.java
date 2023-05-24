package com.projetofog.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MenuPrincipal implements Screen {
    float largura, altura;
    private SpriteBatch batch;
    private Texture fundo, titulo, btnJogar, btnComoJogar;
    private AnalizarSeTocou btnJ, btnC;
    private String estado;


    @Override
    public void show() {
        estado = "nada";

        batch = new SpriteBatch();
        fundo = new Texture("space.jpg");

        titulo = new Texture("titulo.png");

        btnJogar = new Texture("jogar.png");
        btnComoJogar = new Texture("comojogar.png");

        btnJ = new AnalizarSeTocou(altura/2, altura/8, largura/2 - altura/4, altura/2 - altura/8, altura);
        btnC = new AnalizarSeTocou(altura/2, altura/8, largura/2 - altura/4, altura/2 - altura/3, altura);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(fundo, 0, 0, largura, altura);
        batch.draw(titulo, largura/2 - altura/3, altura/2 - altura/30, 2 * altura/3, 2 * altura/3);
        batch.draw(btnJogar, largura/2 - altura/4, altura/2 - altura / 8, altura/2, altura/8);
        batch.draw(btnComoJogar, largura/2 - altura/4, altura/2 - altura / 3, altura/2, altura/8);
        batch.end();

        if(btnJ.tocou())
            estado = "jogar";
        if(btnC.tocou())
            estado = "como_jogar";
    }

    @Override
    public void resize(int width, int height) {
        largura = width;
        altura = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch.dispose();
        fundo.dispose();
        titulo.dispose();
        btnJogar.dispose();
        btnComoJogar.dispose();
    }

    @Override
    public void dispose() {

    }

    public String getEstado()
    {
        return estado;
    }
}
