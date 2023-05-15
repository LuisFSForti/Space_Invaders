package com.projetofog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.LinkedList;

public class Jogo implements Screen {
    private String estado;
    float largura, altura;
    private SpriteBatch batch;
    private Stage stage;
    private Texture fundo, btnSetaE, btnSetaD, btnMira;
    private Jogador naveJ;
    private AnalizarSeTocou btnE, btnD, btnM;
    private float tempo, ultimoTiroJ, ultimoTiroI;
    private LinkedList<Tiro> tiros;
    private LinkedList<Inimigo> inimigos;

    @Override
    public void show() {
        estado = "nada";
        batch = new SpriteBatch();
        stage = new Stage();

        fundo = new Texture("space.jpg");

        btnSetaE = new Texture("setaEsquerda.png");
        btnSetaD = new Texture("setaDireita.png");

        btnMira = new Texture("mira.png");

        btnE = new AnalizarSeTocou(largura/12, largura/12, largura/40, largura/40, altura);
        btnD = new AnalizarSeTocou(largura/12, largura/12, largura/40 + largura/12 + largura/16, largura/40, altura);
        btnM = new AnalizarSeTocou(largura/11, largura/11, largura - (largura/40 + largura/11), largura/40, altura);

        tempo = 0;
        ultimoTiroJ = 0;
        ultimoTiroI = 60;

        tiros = new LinkedList<>();
        inimigos = new LinkedList<>();

        float variacaoY = ((altura - altura/6) - altura/2) / 5;
        float tamanho = variacaoY - variacaoY / 20;

        naveJ = new Jogador(largura, altura);

        float posicaoX;
        float posicaoY = altura - altura / 6;
        float variacaoX = (largura - largura / 3 + tamanho) / 11;

        for(int i = 0; i < 5; i++)
        {
            posicaoX = 200;
            for(int j = 0; j < 11; j++)
            {
                inimigos.add(new Inimigo(posicaoX, posicaoY, tamanho, tamanho, largura, altura, 1));
                posicaoX += variacaoX;
            }
            posicaoY -= variacaoY;
        }
    }

    @Override
    public void render(float delta) {
        naveJ.aumentarX();
        tempo++;

        if (btnD.tocou() || Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            naveJ.setVariacao(largura / 150);
        else if (btnE.tocou() || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            naveJ.setVariacao(-largura / 150);
        else
            naveJ.setVariacao(0);

        if (btnM.tocou() || Gdx.input.isKeyPressed(Input.Keys.SPACE))
            if (ultimoTiroJ <= tempo) {
                ultimoTiroJ = tempo + 60;
                tiros.add(new Tiro(naveJ.getX() + naveJ.getTamanho() / 2, naveJ.getY() + naveJ.getTamanho(), 'j', altura));
            }

        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(fundo, 0, 0, largura, altura);

        naveJ.desenhar(batch);

        boolean alternar = false;
        for (int i = 0; i < tiros.size(); i++) {
            tiros.get(i).desenhar(batch);
            int res = tiros.get(i).tocouEmAlgo(naveJ, inimigos);
            if (res == -1)
                tiros.remove(i);
            else if (res == -2) {
                naveJ.mudarVida(-1);
                tiros.remove(i);
            }
            else if (res != -3) {
                inimigos.get(res).mudarVida(-1);
                tiros.remove(i);
                alternar = true;
            }
        }

        if(alternar)
            for (int i = 0; i < inimigos.size(); i++) {
                inimigos.get(i).mudarVelocidade((float)(15 - inimigos.size())/(float)55);
            }

        alternar = false;

        if(ultimoTiroI < tempo)
        {
            ultimoTiroI += Math.random() * 150 + 30;
            int quantidade = 1;

            if(inimigos.size() > 30)
                quantidade = 3;
            else if(inimigos.size() > 15)
                quantidade = 2;

            for(int i = 0; i < quantidade; i++)
                inimigos.get((int)(Math.random() * inimigos.size())).atirar(tiros);
        }
        for (int i = 0; i < inimigos.size(); i++) {
            if(inimigos.get(i).estaVivo()) {
                if (inimigos.get(i).andar()) {
                    alternar = true;
                }
                inimigos.get(i).desenhar(batch);
            }
            else
                inimigos.remove(i);
        }
        if(alternar)
            for (int j = 0; j < inimigos.size(); j++) {
                inimigos.get(j).alternar();
            }

        batch.draw(btnSetaE, largura / 40, largura / 40, largura / 12, largura / 12);
        batch.draw(btnSetaD, largura / 40 + largura / 12 + largura / 16, largura / 40, largura / 12, largura / 12);
        batch.draw(btnMira, largura - (largura / 40 + largura / 11), largura / 40, largura / 11, largura / 11);

        batch.end();

        if(inimigos.size() == 0)
            estado = "venceu";
        if(!naveJ.estaVivo())
            estado = "morreu";
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
