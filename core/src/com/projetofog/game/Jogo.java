package com.projetofog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo implements Screen {
    private String estado;
    float largura, altura;
    private SpriteBatch batch;
    private Stage stage;
    private Texture fundo, btnSetaE, btnSetaD;
    //private ImageButton btnSetaE, btnSetaD;
    private Drawable fotoSetaE,fotoSetaD;
    private Jogador naveJ;
    private AnalizarSeTocou btnE, btnD;

    @Override
    public void show() {
        estado = "nada";
        batch = new SpriteBatch();
        stage = new Stage();

        naveJ = new Jogador(largura);

        fundo = new Texture("space.jpg");

        btnSetaE = new Texture("setaEsquerda.png");
        btnSetaD = new Texture("setaDireita.png");

        btnE = new AnalizarSeTocou(largura/12, largura/12, largura/40, largura/40, altura);
        btnD = new AnalizarSeTocou(largura/12, largura/12, largura/40 + largura/12 + largura/16, largura/40, altura);
    }

    @Override
    public void render(float delta) {
        naveJ.aumentarX();

        if(Gdx.input.isTouched()) {
            if(btnD.tocou())
                naveJ.setVariacao(largura/150);
            else if (btnE.tocou())
                naveJ.setVariacao(-largura/150);
        }
        else
            naveJ.setVariacao(0);

        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(fundo, 0, 0, largura, altura);
        naveJ.desenhar(batch);
        batch.draw(btnSetaE, largura/40, largura/40, largura/12, largura/12);
        batch.draw(btnSetaD, largura/40 + largura/12 + largura/16, largura/40, largura/12, largura/12);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        altura = height;
        largura = width;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public String getEstado()
    {
        return estado;
    }
}
