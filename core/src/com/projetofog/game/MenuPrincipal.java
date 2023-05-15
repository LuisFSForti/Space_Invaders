package com.projetofog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;


public class MenuPrincipal implements Screen {
    float largura, altura;
    private SpriteBatch batch;
    private Texture fundo, titulo;
    private Drawable fotoJogar, fotoComoJogar;
    private ImageButton btnJogar, btnComoJogar;
    private Stage stage;
    private String estado;


    @Override
    public void show() {
        estado = "nada";

        batch = new SpriteBatch();
        fundo = new Texture("space.jpg");

        titulo = new Texture("titulo.png");

        fotoJogar = new TextureRegionDrawable(new TextureRegion(new Texture("jogar.png")));
        fotoJogar.setMinWidth(largura/4);
        fotoJogar.setMinHeight(largura/16);

        fotoComoJogar = new TextureRegionDrawable(new TextureRegion(new Texture("comojogar.png")));
        fotoComoJogar.setMinWidth(largura/4);
        fotoComoJogar.setMinHeight(largura/16);

        btnJogar = new ImageButton(fotoJogar);
        btnJogar.setPosition(0, 0);
        btnJogar.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                estado = "jogar";
                return true;
            }
        });

        btnComoJogar = new ImageButton(fotoComoJogar);
        btnComoJogar.setPosition(0, 0);
        btnComoJogar.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                estado = "como_jogar";
                return true;
            }
        });

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        btnJogar.setPosition(largura/2 - largura/8, altura/2 - altura/8);
        btnJogar.setWidth(largura/4);
        btnJogar.setHeight(largura/16);

        stage.addActor(btnJogar);

        btnComoJogar.setPosition(largura/2 - largura/8, altura/2 - altura/3);
        btnComoJogar.setWidth(largura/4);
        btnComoJogar.setHeight(largura/16);

        stage.addActor(btnComoJogar);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(fundo, 0, 0, largura, altura);
        batch.draw(titulo, largura/2 - largura/6, altura/2, largura/3, largura/3);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        stage.dispose();
    }

    @Override
    public void dispose() {

    }

    public String getEstado()
    {
        return estado;
    }
}
