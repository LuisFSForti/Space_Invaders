package com.projetofog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

import java.util.EventListener;
import java.util.LinkedList;

public class Jogo implements Screen {
    private String estado;
    private float largura, altura;
    private SpriteBatch batch;
    private Stage stage,stageP;
    private Texture fundo;
    private Jogador naveJ;
    private Drawable fotoSetaE, fotoSetaD, fotoMira, fotoPause, fotoContinuar, fotoReiniciar;
    private ImageButton btnE, btnD, btnM, btnP, btnC, btnR;
    private float tempo, ultimoTiroJ, ultimoTiroI, cdNaveG, valorFase;
    private LinkedList<Tiro> tiros;
    private LinkedList<Inimigo> inimigos;
    private LinkedList<Explosao> explosoes;
    private int pontuacao, fase, movimento, atirando;
    private BitmapFont scoreboard, anunciador;
    private GlyphLayout layout;
    private Music intro, loop;

    @Override
    public void show() {
        estado = "jogando";
        batch = new SpriteBatch();

        stage = new Stage();
        stageP = new Stage();
        Gdx.input.setInputProcessor(stage);


        fundo = new Texture("space.jpg");

        fotoSetaE = new TextureRegionDrawable(new TextureRegion((new Texture("setaEsquerda.png"))));
        fotoSetaE.setMinWidth(largura / 12);
        fotoSetaE.setMinHeight(largura / 12);

        btnE = new ImageButton(fotoSetaE);
        btnE.setPosition(largura/40, largura/40);
        btnE.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                movimento = -1;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                movimento = 0;
            }
        });

        stage.addActor(btnE);
        System.out.println(stage.getActors());

        fotoSetaD = new TextureRegionDrawable(new TextureRegion((new Texture("setaDireita.png"))));
        fotoSetaD.setMinWidth(largura / 12);
        fotoSetaD.setMinHeight(largura / 12);

        btnD = new ImageButton(fotoSetaD);
        btnD.setPosition(largura/40 + largura / 12 + largura / 16, largura/40);
        btnD.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                movimento = 1;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                movimento = 0;
            }
        });

        stage.addActor(btnD);

        fotoMira = new TextureRegionDrawable(new TextureRegion((new Texture("mira.png"))));
        fotoMira.setMinWidth(largura / 11);
        fotoMira.setMinHeight(largura / 11);

        btnM = new ImageButton(fotoMira);
        btnM.setPosition(largura - (largura / 40 + largura / 11), largura/40);
        btnM.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                atirando = 1;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                atirando = 0;
            }
        });

        stage.addActor(btnM);

        fotoPause = new TextureRegionDrawable(new TextureRegion((new Texture("pause.png"))));
        fotoPause.setMinWidth(largura / 33);
        fotoPause.setMinHeight(largura / 33);

        btnP = new ImageButton(fotoPause);
        btnP.setPosition(largura - largura / 33 - largura / 40, altura - largura / 33 - largura / 40);
        btnP.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                estado = "pausado";
                Gdx.input.setInputProcessor(stageP);
                return true;
            }
        });

        stage.addActor(btnP);

        fotoContinuar = new TextureRegionDrawable(new TextureRegion((new Texture("continuar.png"))));
        fotoContinuar.setMinWidth(largura / 12);
        fotoContinuar.setMinHeight(largura / 12);

        btnC = new ImageButton(fotoContinuar);
        btnC.setPosition(largura / 2 + largura / 24, altura / 2 - largura/24);
        btnC.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                estado = "jogando";
                Gdx.input.setInputProcessor(stage);
                return true;
            }
        });

        stageP.addActor(btnC);

        fotoReiniciar = new TextureRegionDrawable(new TextureRegion((new Texture("reiniciar.png"))));
        fotoReiniciar.setMinWidth(largura / 12);
        fotoReiniciar.setMinHeight(largura / 12);

        btnR = new ImageButton(fotoReiniciar);
        btnR.setPosition(largura / 2 - largura / 12 - largura/24, altura / 2 - largura/24);
        btnR.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                estado = "reiniciar";
                return true;
            }
        });

        stageP.addActor(btnR);

        pontuacao = 0;

        fase = 0;

        movimento = 0;
        atirando = 0;

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

        int qtdTanques = 0;
        int qtdAtiradores = 0;

        for(int i = 0; i < 5; i++)
        {
            posicaoX = 200;
            for(int j = 0; j < 11; j++)
            {
                if(i > 2) {
                    if(Math.random() * fase < (float)Math.pow(fase, 1.1)/3.0F && qtdTanques < fase + (int)Math.log(fase)) {
                        inimigos.add(new Inimigo(posicaoX, posicaoY, tamanho, tamanho, largura, altura, 3));
                        qtdTanques++;
                    }
                    else
                        inimigos.add(new Inimigo(posicaoX, posicaoY, tamanho, tamanho, largura, altura, 1));
                }
                else if(i < 2) {
                    if(Math.random() * fase < (float)fase/3.0F && qtdAtiradores < fase / 3) {
                        inimigos.add(new Inimigo(posicaoX, posicaoY, tamanho, tamanho, largura, altura, 4));
                        qtdAtiradores++;
                    }
                    else
                        inimigos.add(new Inimigo(posicaoX, posicaoY, tamanho, tamanho, largura, altura, 1));
                }
                else
                    inimigos.add(new Inimigo(posicaoX, posicaoY, tamanho, tamanho, largura, altura, 1));

                posicaoX += variacaoX;
            }
            posicaoY -= variacaoY;
        }

        if(fase == 1)
            naveJ = new Jogador(largura, altura);

        naveJ.mudarVida(1);

        if(fase == 2)
            valorFase = (float) (1/33);
        else
            valorFase = ((float) (Math.pow((float)(Math.log(fase)),2)) - 1) / 3;

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
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && estado != "pausado") {
            estado = "pausado";
            Gdx.input.setInputProcessor(stageP);
        }

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

                String texto = "Score: " + pontuacao;
                layout.setText(scoreboard, texto);
                scoreboard.draw(batch, texto, largura / 2 - layout.width / 2, altura - altura / 40);

                batch.end();

                stage.act(Gdx.graphics.getDeltaTime());
                stage.draw();
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

        String texto = "Score: " + pontuacao;
        layout.setText(scoreboard, texto);
        scoreboard.draw(batch, texto, largura / 2 - layout.width / 2, altura - altura / 40);

        batch.end();

        stage.draw();
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

        String texto = "Score: " + pontuacao;
        layout.setText(scoreboard, texto);
        scoreboard.draw(batch, texto, largura / 2 - layout.width / 2, altura - altura / 40);

        texto = "Fase " + fase;
        layout.setText(anunciador, texto);
        anunciador.draw(batch, texto, largura / 2 - layout.width / 2, altura/2 - layout.height / 2);

        batch.end();
        stage.draw();

        tempo++;
        if (tempo == 60) {
            tempo = 0;
            estado = "jogando";
        }
    }

    private void carregarPause()
    {
        ScreenUtils.clear(0, 0, 0, 1);
        stageP.act(Gdx.graphics.getDeltaTime());
        stageP.draw();
    }

    private void controlarJogador()
    {
        if (movimento == 1 || Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            naveJ.setVariacao(largura / 150);
        else if (movimento == -1 || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            naveJ.setVariacao(-largura / 150);
        else
            naveJ.setVariacao(0);

        naveJ.aumentarX();

        if (atirando == 1 || Gdx.input.isKeyPressed(Input.Keys.SPACE))
            if (ultimoTiroJ <= tempo) {
                ultimoTiroJ = tempo + 30;
                tiros.add(new Tiro(naveJ.getX() + naveJ.getTamanho() / 2, naveJ.getY() + naveJ.getTamanho(), 'j', altura));
            }
    }

    private void manipularTiros()
    {
        boolean matou = false;
        int tiroASerRemovido = -1;
        for (Tiro atual: tiros) {
            atual.desenhar(batch);
            int res = atual.tocouEmAlgo(naveJ, inimigos);
            if (res == -1) {
                tiroASerRemovido = tiros.indexOf(atual);
            }
            else if (res == -2) {
                naveJ.mudarVida(-1);
                estado = "morrendo";
                tiros = new LinkedList<>();

                tiroASerRemovido = -1;
                explosoes.add(new Explosao(naveJ.getX(), naveJ.getY(), 60));

                if(intro.isPlaying())
                    intro.stop();
                if(loop.isPlaying())
                    loop.stop();

                break;
            } else if (res != -3) {
                inimigos.get(res).mudarVida(-1);

                tiroASerRemovido = tiros.indexOf(atual);

                if(!inimigos.get(res).estaVivo()) {
                    float variacaoPontos = (float) (Math.pow(inimigos.get(res).getY(), 4) / (Math.pow(altura, 3)));
                    pontuacao += variacaoPontos + variacaoPontos * Math.pow(valorFase, 2);

                    explosoes.add(new Explosao(inimigos.get(res).getX(), inimigos.get(res).getY(), 15));

                    inimigos.remove(res);
                    matou = true;
                }
            }
        }
        if(tiroASerRemovido > -1)
            tiros.remove(tiroASerRemovido);

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
            Inimigo bonus = new Inimigo(-largura/15, altura - largura/15, largura/15, largura/15 * 51/75, largura, altura, 2);
            bonus.mudarVelocidade(valorFase);
            inimigos.add(bonus);
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

            if (res == 1)
                alternar = true;
            else if (res == 2) {
                estado = "perdeu";
                break;
            }
            else if(res == 3)
                inimigos.remove(atual);
            else if(res == 4)
                atual.atirar(tiros);
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
