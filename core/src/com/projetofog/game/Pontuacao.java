package com.projetofog.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Pontuacao implements Screen {
    private float largura, altura;
    private int pontuacao;
    private SpriteBatch batch;
    private Texture fundo, btnSair;
    private AnalizarSeTocou btnS;
    private String estado, mensagem;
    private BitmapFont scoreboard;
    private GlyphLayout layout;

    @Override
    public void show() {
        estado = "nada";
        batch = new SpriteBatch();

        fundo = new Texture("space.jpg");
        btnSair = new Texture("exit.png");

        btnS = new AnalizarSeTocou(largura/8, largura/8, largura/2 - largura/16, altura/3 - altura/16, altura);

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

        batch.draw(btnSair, largura/2 - largura/16, altura/3 - altura / 8, largura/8, largura/8);
        batch.end();

        if(btnS.tocou())
            estado = "sair";
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
