package com.projetofog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class Pontuacao implements Screen {
    private float largura, altura;
    private int pontuacao;
    private SpriteBatch batch;
    private Texture fundo;
    private Stage stage;
    private Drawable fotoSair;
    private ImageButton btnSair;
    private String estado, mensagem;
    private BitmapFont scoreboard;
    private GlyphLayout layout;

    @Override
    public void show() {
        estado = "nada";
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        fundo = new Texture("space.jpg");

        fotoSair = new TextureRegionDrawable(new TextureRegion((new Texture("exit.png"))));
        fotoSair.setMinWidth(largura / 8);
        fotoSair.setMinHeight(largura / 8);

        btnSair = new ImageButton(fotoSair);
        btnSair.setPosition(largura/2 - largura/16, altura/3 - altura / 8);
        btnSair.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                estado = "sair";
                return true;
            }
        });

        stage.addActor(btnSair);

        scoreboard = new BitmapFont();
        scoreboard.setColor(1,1,1,1);
        scoreboard.getData().setScale(largura / 200);
        layout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(fundo, 0, 0, largura, altura);

        layout.setText(scoreboard, mensagem);
        scoreboard.draw(batch, mensagem, largura / 2 - layout.width / 2, altura / 2 + 3 * altura / 8);

        String texto = "Score: " + pontuacao;
        layout.setText(scoreboard, texto);
        scoreboard.draw(batch, texto, largura / 2 - layout.width / 2, altura / 2 + altura / 8);

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

    }

    @Override
    public void dispose() {

    }

    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public String getEstado()
    {
        return estado;
    }
    public void setPontuacao(int pontuacao)
    {
        this.pontuacao = pontuacao;
    }
}
