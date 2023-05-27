package com.projetofog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class ComoJogar implements Screen {
    float largura, altura;
    private SpriteBatch batch;
    private Texture fundo, instrucoes;
    private String estado;
    private Stage stage;
    private Drawable fotoSair;
    private ImageButton btnSair;


    @Override
    public void show() {
        estado = "nada";

        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        fundo = new Texture("space.jpg");

        instrucoes = new Texture("telaComoJogar.png");

        fotoSair = new TextureRegionDrawable(new TextureRegion((new Texture("exit.png"))));
        fotoSair.setMinWidth(altura / 8);
        fotoSair.setMinHeight(altura / 8);

        btnSair = new ImageButton(fotoSair);
        btnSair.setPosition(largura/2 + altura/4 - altura / 16, altura/2 - altura / 3 - altura / 16);
        btnSair.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                estado = "sair";
                return true;
            }
            /*public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                testeT = false;
            }*/
        });

        stage.addActor(btnSair);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(fundo, 0, 0, largura, altura);
        batch.draw(instrucoes, largura/2 - altura/2, 0, altura, altura);
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
        instrucoes.dispose();
    }

    @Override
    public void dispose() {

    }

    public String getEstado()
    {
        return estado;
    }
}
