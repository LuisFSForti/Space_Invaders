package com.projetofog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;


public class MenuPrincipal implements Screen {
    float largura, altura;
    private SpriteBatch batch;
    private Texture fundo, titulo;
    private String estado;
    private Stage stage;
    private Drawable fotoJogar, fotoComoJogar;
    private ImageButton btnJogar, btnComoJogar;


    @Override
    public void show() {
        estado = "nada";

        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        fundo = new Texture("space.jpg");

        titulo = new Texture("titulo.png");

        fotoJogar = new TextureRegionDrawable(new TextureRegion((new Texture("jogar.png"))));
        fotoJogar.setMinWidth(altura / 2);
        fotoJogar.setMinHeight(altura / 8);

        fotoComoJogar = new TextureRegionDrawable(new TextureRegion((new Texture("comojogar.png"))));
        fotoComoJogar.setMinWidth(altura / 2);
        fotoComoJogar.setMinHeight(altura / 8);

        btnJogar = new ImageButton(fotoJogar);
        btnJogar.setPosition(largura/2 - altura/4, altura/2 - altura / 8);
        btnJogar.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                estado = "jogar";
                return true;
            }
            /*public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                testeT = false;
            }*/
        });

        btnComoJogar = new ImageButton(fotoComoJogar);
        btnComoJogar.setPosition(largura/2 - altura/4, altura/2 - altura / 3);
        btnComoJogar.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                estado = "como_jogar";
                return true;
            }
            /*public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                testeT = false;
            }*/
        });

        stage.addActor(btnJogar);
        stage.addActor(btnComoJogar);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(fundo, 0, 0, largura, altura);
        batch.draw(titulo, largura/2 - altura/3, altura/2 - altura/30, 2 * altura/3, 2 * altura/3);
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
        stage.dispose();
        fundo.dispose();
        titulo.dispose();
    }

    @Override
    public void dispose() {

    }

    public String getEstado()
    {
        return estado;
    }
}
