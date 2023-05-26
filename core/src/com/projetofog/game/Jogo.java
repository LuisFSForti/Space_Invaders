package com.projetofog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.LinkedList;

public class Jogo implements Screen {
    private String estado;
    private float largura, altura;
    private SpriteBatch batch;
    private Texture fundo, btnSetaE, btnSetaD, btnMira, btnPause, btnContinuar, btnReiniciar;
    private Jogador naveJ;
    private AnalizarSeTocou btnE, btnD, btnM, btnP, btnC, btnR;
    private float tempo, ultimoTiroJ, ultimoTiroI, cdNaveG, valorFase;
    private LinkedList<Tiro> tiros;
    private LinkedList<Inimigo> inimigos;
    private LinkedList<Explosao> explosoes;
    private int pontuacao, fase;
    private BitmapFont scoreboard, anunciador;
    private GlyphLayout layout;
    private Music intro, loop;

    @Override
    public void show() {
        estado = "jogando";
        batch = new SpriteBatch();

        fundo = new Texture("space.jpg");

        btnSetaE = new Texture("setaEsquerda.png");
        btnSetaD = new Texture("setaDireita.png");
        btnMira = new Texture("mira.png");

        btnPause = new Texture("pause.png");
        btnContinuar = new Texture("continuar.png");
        btnReiniciar = new Texture("reiniciar.png");

        btnE = new AnalizarSeTocou(largura/12, largura/12, largura/40, largura/40, altura);
        btnD = new AnalizarSeTocou(largura/12, largura/12, largura/40 + largura/12 + largura/16, largura/40, altura);
        btnM = new AnalizarSeTocou(largura/11, largura/11, largura - (largura/40 + largura/11), largura/40, altura);

        btnP = new AnalizarSeTocou(largura/33, largura/33, largura - (largura/40 + largura/33), altura - largura / 33 - largura / 40, altura);
        btnC = new AnalizarSeTocou(largura/12, largura/12, largura / 2 + largura / 24, altura / 2 - largura/24, altura);
        btnR = new AnalizarSeTocou(largura/12, largura/12, largura / 2 - largura / 12 - largura/24, altura / 2 - largura/24, altura);

        pontuacao = 0;

        fase = 0;

        valorFase = 1;

        scoreboard = new BitmapFont();
        scoreboard.setColor(1,1,1,1);
        scoreboard.getData().setScale(largura / 500);
        anunciador = new BitmapFont();
        anunciador.setColor(1,1,1,1);
        anunciador.getData().setScale(largura / 300);
        layout = new GlyphLayout();

        passarFase();
        intro = Gdx.audio.newMusic(Gdx.files.internal("intro.ogg"));
        intro.setVolume(0.2F);
        intro.play();
        intro.setLooping(false);
        intro.setOnCompletionListener(new Music.OnCompletionListener(){
            @Override
            public void onCompletion(Music a){
                loop.play();
            }
        });

        loop = Gdx.audio.newMusic(Gdx.files.internal("loop.ogg"));
        loop.setVolume(0.2F);
        loop.setLooping(true);
    }

    private void passarFase()
    {
        fase++;

        tempo = 0;
        cdNaveG = (float)Math.random() * 300 + 300;
        ultimoTiroJ = 0;
        ultimoTiroI = 60;

        tiros = new LinkedList<>();
        inimigos = new LinkedList<>();
        explosoes = new LinkedList<>();

        float variacaoY = ((altura - altura/6) - altura/2) / 5;
        float tamanho = variacaoY - variacaoY / 20;

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

        if(fase == 1)
            naveJ = new Jogador(largura, altura);

        naveJ.mudarVida(1);

        if(fase == 2)
            valorFase = (float) (1/11);
        else
            valorFase = (float) (Math.pow((float)(Math.log(fase)),2)) - 1;

        for (Inimigo atual: inimigos)
            atual.mudarVelocidade(valorFase);

        estado = "esperando";
    }

    @Override
    public void render(float delta) {
        if(estado == "esperando") {
            anunciarFase();
        }
        if(estado == "morrendo")
        {
            if(explosoes.size() == 0)
            {
                estado = "jogando";
                naveJ.reiniciarPosicao();
                intro.play();
            }
            else {
                desenharSemAlterar(false);
            }
        }
        if (btnP.tocou())
            estado = "pausado";

        if(estado == "pausado")
        {
            carregarPause();
        }
        else if (estado == "jogando") {
            if (inimigos.size() == 0) {
                if (explosoes.size() == 0)
                    passarFase();
                else
                {
                    desenharSemAlterar(true);
                }
            }
            else if (!naveJ.estaVivo()) {
                    estado = "morreu";
            }
            else {
                tempo++;

                controlarJogador();

                ScreenUtils.clear(0, 0, 0, 1);
                batch.begin();
                batch.draw(fundo, 0, 0, largura, altura);

                naveJ.desenharNave(batch);
                naveJ.desenharVida(batch);

                manipularTiros();

                manipularInimigos();

                animarExplosoes();

                batch.draw(btnSetaE, largura / 40, largura / 40, largura / 12, largura / 12);
                batch.draw(btnSetaD, largura / 40 + largura / 12 + largura / 16, largura / 40, largura / 12, largura / 12);
                batch.draw(btnMira, largura - (largura / 40 + largura / 11), largura / 40, largura / 11, largura / 11);
                batch.draw(btnPause, largura - largura / 33 - largura / 40, altura - largura / 33 - largura / 40, largura / 33, largura / 33);

                String texto = "Score: " + pontuacao;
                layout.setText(scoreboard, texto);
                scoreboard.draw(batch, texto, largura / 2 - layout.width / 2, altura - altura / 40);

                batch.end();
            }
        }
    }

    private void desenharSemAlterar(boolean comNave)
    {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(fundo, 0, 0, largura, altura);

        if(comNave)
            naveJ.desenharNave(batch);

        naveJ.desenharVida(batch);

        for (Tiro atual : tiros)
            atual.desenhar(batch);

        for (Inimigo atual : inimigos)
            atual.desenhar(batch);

        animarExplosoes();

        batch.draw(btnSetaE, largura / 40, largura / 40, largura / 12, largura / 12);
        batch.draw(btnSetaD, largura / 40 + largura / 12 + largura / 16, largura / 40, largura / 12, largura / 12);
        batch.draw(btnMira, largura - (largura / 40 + largura / 11), largura / 40, largura / 11, largura / 11);
        batch.draw(btnPause, largura - largura / 33 - largura / 40, altura - largura / 33 - largura / 40, largura / 33, largura / 33);

        String texto = "Score: " + pontuacao;
        layout.setText(scoreboard, texto);
        scoreboard.draw(batch, texto, largura / 2 - layout.width / 2, altura - altura / 40);

        batch.end();
    }

    private void anunciarFase()
    {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(fundo, 0, 0, largura, altura);

        naveJ.desenharNave(batch);
        naveJ.desenharVida(batch);

        for (Inimigo atual: inimigos)
            atual.desenhar(batch);

        batch.draw(btnSetaE, largura / 40, largura / 40, largura / 12, largura / 12);
        batch.draw(btnSetaD, largura / 40 + largura / 12 + largura / 16, largura / 40, largura / 12, largura / 12);
        batch.draw(btnMira, largura - (largura / 40 + largura / 11), largura / 40, largura / 11, largura / 11);
        batch.draw(btnPause, largura - largura / 33 - largura / 40, altura - largura / 33 - largura / 40, largura / 33, largura / 33);

        String texto = "Score: " + pontuacao;
        layout.setText(scoreboard, texto);
        scoreboard.draw(batch, texto, largura / 2 - layout.width / 2, altura - altura / 40);

        texto = "Fase " + fase;
        layout.setText(anunciador, texto);
        anunciador.draw(batch, texto, largura / 2 - layout.width / 2, altura/2 - layout.height / 2);

        batch.end();
        tempo++;
        if (tempo == 60) {
            tempo = 0;
            estado = "jogando";
        }
    }

    private void carregarPause()
    {
        batch.begin();
        batch.draw(btnContinuar, largura / 2 + largura / 24, altura / 2 - largura/24, largura / 12, largura / 12);
        batch.draw(btnReiniciar, largura / 2 - largura / 12 - largura/24, altura / 2 - largura/24, largura / 12, largura / 12);
        batch.end();

        if(btnC.tocou())
            estado = "jogando";
        if(btnR.tocou())
            estado = "reiniciar";
    }

    private void controlarJogador()
    {
        if (btnD.tocou() || Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            naveJ.setVariacao(largura / 150);
        else if (btnE.tocou() || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            naveJ.setVariacao(-largura / 150);
        else
            naveJ.setVariacao(0);

        naveJ.aumentarX();

        if (btnM.tocou() || Gdx.input.isKeyPressed(Input.Keys.SPACE))
            if (ultimoTiroJ <= tempo) {
                ultimoTiroJ = tempo + 30;
                tiros.add(new Tiro(naveJ.getX() + naveJ.getTamanho() / 2, naveJ.getY() + naveJ.getTamanho(), 'j', altura));
            }
    }

    private void manipularTiros()
    {
        boolean matou = false;
        for (Tiro atual: tiros) {
            atual.desenhar(batch);
            int res = atual.tocouEmAlgo(naveJ, inimigos);
            if (res == -1) {
                tiros.remove(atual);
                break;
            }
            else if (res == -2) {
                naveJ.mudarVida(-1);
                tiros.remove(atual);
                explosoes.add(new Explosao(naveJ.getX(), naveJ.getY(), 60));
                estado = "morrendo";
                tiros = new LinkedList<>();
                if(intro.isPlaying())
                    intro.stop();
                if(loop.isPlaying())
                    loop.stop();
                break;
            } else if (res != -3) {
                inimigos.get(res).mudarVida(-1);
                tiros.remove(atual);

                if(!inimigos.get(res).estaVivo()) {
                    float variacaoPontos = (float) (Math.pow(inimigos.get(res).getY(), 4) / (Math.pow(altura, 3)));
                    pontuacao += variacaoPontos + variacaoPontos * Math.pow(valorFase, 2);

                    explosoes.add(new Explosao(inimigos.get(res).getX(), inimigos.get(res).getY(), 15));

                    inimigos.remove(res);
                    matou = true;
                }
                break;
            }
        }
        if (matou)
            for (Inimigo atual: inimigos) {
                atual.mudarVelocidade((float) (15 - inimigos.size()) / (float) 55);
            }
    }

    private void manipularInimigos()
    {
        boolean alternar = false;

        if(tempo > cdNaveG)
        {
            inimigos.add(new Inimigo(-largura/15, altura - largura/15, largura/15, largura/15 * 51/75, largura, altura, 2));
            cdNaveG = tempo + (float)Math.random() * 500 + 500;
        }

        if (ultimoTiroI < tempo) {
            ultimoTiroI += Math.random() * 150 + 30;
            int quantidade = 1;

            if (inimigos.size() > 30)
                quantidade = 3;
            else if (inimigos.size() > 15)
                quantidade = 2;
            else if(inimigos.size() == 0)
                quantidade = 0;

            for (int i = 0; i < quantidade; i++)
                inimigos.get((int) (Math.random() * inimigos.size())).atirar(tiros);
        }
        for (Inimigo atual: inimigos) {
            int res = atual.andar();

            if (res == 1) {
                alternar = true;
            } else if (res == 2) {
                estado = "perdeu";
                break;
            }
            else if(res == 3)
            {
                inimigos.remove(atual);
            }
            if(res != 3)
                atual.desenhar(batch);
        }
        if (alternar)
            for (Inimigo atualA: inimigos) {
                atualA.alternar();
            }
    }

    private void animarExplosoes()
    {
        for(Explosao atual: explosoes)
        {
            if(!atual.animar(batch))
            {
                explosoes.remove(atual);
                break;
            }
        }
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
        intro.stop();
        loop.stop();
    }

    @Override
    public void dispose() {

    }

    public String getEstado()
    {
        return estado;
    }
    public int getPontuacao()
    {
        return pontuacao;
    }
}
