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

//Tela do jogo
public class Jogo implements Screen {
    private String estado;
    //Tamanho da tela
    private float largura, altura;
    //Dados de controle
    private int pontuacao, fase, movimento, atirando;
    private float tempo, ultimoTiroJ, ultimoTiroI, cdNaveG, valorFase;
    //Objeto que controla as texturas
    private SpriteBatch batch;
    //Objetos que controlas os botões (normalmente / quando pausado)
    private Stage stage,stageP;
    //Jogador
    private Jogador naveJ;
    //Sprites utilizados
    private Texture fundo;
    private Drawable fotoSetaE, fotoSetaD, fotoMira, fotoPause, fotoContinuar, fotoReiniciar;
    //Botões utilizados
    private ImageButton btnE, btnD, btnM, btnP, btnC, btnR;
    //Listas dos tiros, inimigos e explosões
    private LinkedList<Tiro> tiros;
    private LinkedList<Inimigo> inimigos;
    private LinkedList<Explosao> explosoes;
    //Fontes utilizadas para escrever
    private BitmapFont scoreboard, anunciador;
    //Objeto auxiliar para a formatação da escrita
    private GlyphLayout layout;
    //Música de fundo
    private Music intro, loop;

    //Ao abrir a tela
    @Override
    public void show() {
        //Instancia batche e os stages
        batch = new SpriteBatch();
        stage = new Stage();
        stageP = new Stage();
        //Define stage como o receptor de toques da tela
        Gdx.input.setInputProcessor(stage);

        //Pega a textura do fundo
        fundo = new Texture("space.jpg");

        //Cria as texturas dos botões
        fotoSetaE = new TextureRegionDrawable(new TextureRegion((new Texture("setaEsquerda.png"))));
        fotoSetaD = new TextureRegionDrawable(new TextureRegion((new Texture("setaDireita.png"))));
        fotoMira = new TextureRegionDrawable(new TextureRegion((new Texture("mira.png"))));
        fotoPause = new TextureRegionDrawable(new TextureRegion((new Texture("pause.png"))));
        fotoContinuar = new TextureRegionDrawable(new TextureRegion((new Texture("continuar.png"))));
        fotoReiniciar = new TextureRegionDrawable(new TextureRegion((new Texture("reiniciar.png"))));

        //Define os tamanhos dos botões
        fotoSetaE.setMinWidth(largura / 12);
        fotoSetaE.setMinHeight(largura / 12);
        fotoSetaD.setMinWidth(largura / 12);
        fotoSetaD.setMinHeight(largura / 12);
        fotoMira.setMinWidth(largura / 11);
        fotoMira.setMinHeight(largura / 11);
        fotoPause.setMinWidth(largura / 33);
        fotoPause.setMinHeight(largura / 33);
        fotoContinuar.setMinWidth(largura / 12);
        fotoContinuar.setMinHeight(largura / 12);
        fotoReiniciar.setMinWidth(largura / 12);
        fotoReiniciar.setMinHeight(largura / 12);

        //Cria os botões
        btnE = new ImageButton(fotoSetaE);
        btnD = new ImageButton(fotoSetaD);
        btnM = new ImageButton(fotoMira);
        btnP = new ImageButton(fotoPause);
        btnC = new ImageButton(fotoContinuar);
        btnR = new ImageButton(fotoReiniciar);

        //Define as posições dos botões
        btnE.setPosition(largura/40, largura/40);
        btnD.setPosition(largura/40 + largura / 12 + largura / 16, largura/40);
        btnM.setPosition(largura - (largura / 40 + largura / 11), largura/40);
        btnP.setPosition(largura - largura / 33 - largura / 40, altura - largura / 33 - largura / 40);
        btnC.setPosition(largura / 2 + largura / 24, altura / 2 - largura/24);
        btnR.setPosition(largura / 2 - largura / 12 - largura/24, altura / 2 - largura/24);

        //Criação de eventos para os botões
        btnE.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Ao ser tocado, define o valor de movimento
                movimento = -1;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //Ao deixar de ser tocado, define o valor de movimento
                movimento = 0;
            }
        });
        btnD.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Ao ser tocado, define o valor de movimento
                movimento = 1;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //Ao deixar de ser tocado, define o valor de movimento
                movimento = 0;
            }
        });
        btnM.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Ao ser tocado, define o valor de atirando
                atirando = 1;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //Ao deixar de ser tocado, define o valor de atirando
                atirando = 0;
            }
        });
        btnP.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Caso não esteja no meio de uma animação
                if (estado == "jogando") {
                    //Ao ser tocado, muda o estado
                    estado = "pausado";
                    //Define stageP como o receptor de toques
                    Gdx.input.setInputProcessor(stageP);
                }
                return true;
            }
        });
        btnC.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Ao ser tocado, muda o estado
                estado = "jogando";
                //Define stage como o receptor de toques
                Gdx.input.setInputProcessor(stage);
                return true;
            }
        });
        btnR.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Ao ser tocado, muda o estado
                estado = "reiniciar";
                return true;
            }
        });

        //Adiciona os botões ao stage
        stage.addActor(btnE);
        stage.addActor(btnD);
        stage.addActor(btnM);
        stage.addActor(btnP);

        //Adiciona os botões ao stageP
        stageP.addActor(btnC);
        stageP.addActor(btnR);

        //Cria os valores iniciais
        pontuacao = 0;
        fase = 0;
        movimento = 0;
        atirando = 0;
        valorFase = 1;

        //Instancia as fontes
        scoreboard = new BitmapFont();
        anunciador = new BitmapFont();

        //Define as cores e os tamanhos das fontes
        scoreboard.setColor(1,1,1,1);
        scoreboard.getData().setScale(largura / 500);

        anunciador.setColor(1,1,1,1);
        anunciador.getData().setScale(largura / 300);

        //Instancia layout
        layout = new GlyphLayout();

        //Cria o jogador
        naveJ = new Jogador(largura, altura);
        passarFase();

        //Pega os trechos das músicas
        intro = Gdx.audio.newMusic(Gdx.files.internal("intro.ogg"));
        loop = Gdx.audio.newMusic(Gdx.files.internal("loop.ogg"));

        //Define os volumes e se fazem loop
        intro.setVolume(0.2F);
        intro.setLooping(false);
        loop.setVolume(0.2F);
        loop.setLooping(true);

        //Inicia intro
        intro.play();
        //Define que, ao término de intro, iniciará loop
        intro.setOnCompletionListener(new Music.OnCompletionListener(){
            @Override
            public void onCompletion(Music a){
                loop.play();
            }
        });
    }

    //Método para passar de fase
    private void passarFase()
    {
        //Avança uma fase
        fase++;

        //Reinicia o tempo
        tempo = 0;

        //Define os valores inicias para
        //Criação da nave bônus
        cdNaveG = (float)Math.random() * 300 + 300;
        //Tiro do jogador
        ultimoTiroJ = 0;
        //Tiro dos inimigos
        ultimoTiroI = 60;

        //Limpa as listas
        tiros = new LinkedList<>();
        inimigos = new LinkedList<>();
        explosoes = new LinkedList<>();

        //Define qual será a distância vertical entre as naves
        float variacaoY = ((altura - altura/6) - altura/2) / 5;
        //Define o tamanho dos inimigos
        float tamanho = variacaoY - variacaoY / 20;

        //Define as variáveis de posição das naves inimigas
        float posicaoX;
        float posicaoY = altura - altura / 6;
        //Define qual será a distância horizontal entre as naves
        float variacaoX = (largura - largura / 3 + tamanho) / 11;

        //Valores para a quantidade de tanques e de atiradores
        int qtdTanques = fase + (int)Math.log(fase);
        int qtdAtiradores = fase / 3;

        //Cada linha de inimigos
        for(int i = 0; i < 5; i++)
        {
            //Posição horizontal do primeiro inimigo da linha
            posicaoX = (float) largura / 9.6F;
            //Cada coluna da linha
            for(int j = 0; j < 11; j++)
            {
                //Se for uma das últimas duas linhas
                if(i > 2) {
                    //Aleatoriza se terá um inimigo tanque ali e se podem ter mais tanques
                    if(Math.random() * fase < (float)Math.pow(fase, 1.1)/3.0F && qtdTanques > 0) {
                        //Caso sim, adiciona um inimigo tanque na posição
                        inimigos.add(new Inimigo(posicaoX, posicaoY, tamanho, tamanho, largura, altura, 3));
                        //Diminui o limite de tanques em 1
                        qtdTanques--;
                    }
                    //Caso não
                    else
                        //Adiciona um inimigo normal
                        inimigos.add(new Inimigo(posicaoX, posicaoY, tamanho, tamanho, largura, altura, 1));
                }
                //Se for uma das primeiras duas linhas
                else if(i < 2) {
                    //Aleatoriza se terá um inimigo atirador ali e se podem ter mais atiradores
                    if(Math.random() * fase < (float)fase/3.0F && qtdAtiradores > 0) {
                        //Caso sim, adiciona um inimigo atirador na posição
                        inimigos.add(new Inimigo(posicaoX, posicaoY, tamanho, tamanho, largura, altura, 4));
                        //Diminui o limite de atiradores em 1
                        qtdAtiradores--;
                    }
                    //Caso não
                    else
                        //Adiciona um inimigo normal
                        inimigos.add(new Inimigo(posicaoX, posicaoY, tamanho, tamanho, largura, altura, 1));
                }
                //Caso seja a linha do meio
                else
                    //Adiciona um inimigo normal
                    inimigos.add(new Inimigo(posicaoX, posicaoY, tamanho, tamanho, largura, altura, 1));

                //Aumenta posicaoX
                posicaoX += variacaoX;
            }
            //Diminui posicaoY
            posicaoY -= variacaoY;
        }

        //Aumenta a vida do jogador em 1
        naveJ.mudarVida(1);

        //Se for a segunda fase
        if(fase == 2)
            //Define o valor de valorFase
            valorFase = (float) (1/33);
        //Se não for
        else
            //Define valor de valorFase pela conta (ln(fase)^2 - 1) / 3
            valorFase = ((float) (Math.pow((float)(Math.log(fase)),2)) - 1) / 3;

        //Se não for a primeira fase
        if (fase != 1)
            //Para todos os inimigos
            for (Inimigo atual: inimigos)
                //Muda a velocidade
                atual.mudarVelocidade(valorFase);

        //Muda o estado
        estado = "esperando";
    }

    //Método principal
    @Override
    public void render(float delta) {
        //Caso esteja iniciando a fase
        if(estado == "esperando") {
            anunciarFase();
        }
        //Caso esteja na animação de morte
        if(estado == "morrendo")
        {
            //Espera até acabar as animações de morte
            if(explosoes.size() == 0)
            {
                //Quando acabarem, altera o estado
                estado = "jogando";
                //Reinicia a posição do jogador
                naveJ.reiniciarPosicao();
                //Inicia a intro da música
                intro.play();
            }
            //Caso ainda tenham animações
            else {
                //Desenha a tela sem a nave do jogador
                desenharSemAlterar(false);
            }
        }
        //Caso o jogador aperte a tecla ESC e não esteja numa animação
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && estado == "jogando") {
            //Muda o estado
            estado = "pausado";
            //Define stageP como receptor de toques na tela
            Gdx.input.setInputProcessor(stageP);
        }

        //Caso esteja na tela de pause
        if(estado == "pausado")
        {
            carregarPause();
        }
        //Caso esteja jogando
        else if (estado == "jogando") {
            //Se tiverem acabado os inimigos
            if (inimigos.size() == 0) {
                //Se tiverem acabado as animações de morte
                if (explosoes.size() == 0)
                    //Avança de fase
                    passarFase();
                //Caso ainda tenha uma animação de morte
                else
                {
                    //Desenha a tela sem interação do jogador
                    desenharSemAlterar(true);
                }
            }
            //Caso o jogador esteja sem vidas
            else if (!naveJ.estaVivo()) {
                //Muda o estado
                estado = "morreu";
            }
            //Se nenhuma condição anterior foi satisfeita
            else {
                //Tempo aumenta em 1 (ou seja, aumenta 60 à cada segundo)
                tempo++;

                //Manipula o jogador
                controlarJogador();

                //Limpa a tela
                ScreenUtils.clear(0, 0, 0, 1);
                //Começa à desenhar
                batch.begin();
                //Desenha o fundo
                batch.draw(fundo, 0, 0, largura, altura);

                //Desenha a nave do jogador e suas vidas
                naveJ.desenharNave(batch);
                naveJ.desenharVida(batch);

                //Manipula os tiros, inimigos e explosões
                manipularTiros();
                manipularInimigos();
                animarExplosoes();


                //Define que o texto à ser escrito é Score: pontuação
                layout.setText(scoreboard, "Score: " + pontuacao);
                //Escreve a pontuação
                scoreboard.draw(batch, "Score: " + pontuacao, largura / 2 - layout.width / 2, altura - altura / 40);

                //Termina de desenhar
                batch.end();

                //Chama todos os métodos dos botões de stage
                stage.act(Gdx.graphics.getDeltaTime());
                //Desenha todos os botões de stage
                stage.draw();
            }
        }
    }

    //Desenha a tela sem interação do jogador
    private void desenharSemAlterar(boolean comNave)
    {
        //Limpa a tela
        ScreenUtils.clear(0, 0, 0, 1);
        //Começa à desenhar
        batch.begin();
        //Desenha o fundo
        batch.draw(fundo, 0, 0, largura, altura);

        //Verifica se deve desenhar a nave do jogador (se não está na sua animação de morte)
        if(comNave)
            naveJ.desenharNave(batch);

        //Desenha as vidas do jogador
        naveJ.desenharVida(batch);

        //Desenha todos os tiros
        for (Tiro atual : tiros)
            atual.desenhar(batch);

        //Desenha todos os inimigos
        for (Inimigo atual : inimigos)
            atual.desenhar(batch);

        //Anima as explosões
        animarExplosoes();

        //Define que o texto à ser escrito é Score: pontuação
        layout.setText(scoreboard, "Score: " + pontuacao);
        //Escreve a pontuação
        scoreboard.draw(batch, "Score: " + pontuacao, largura / 2 - layout.width / 2, altura - altura / 40);

        //Para de desenhar
        batch.end();

        //Desenha todos os botões de stage
        stage.draw();
    }

    //Método
    private void anunciarFase()
    {
        //Desenha a tela sem interação do jogador
        desenharSemAlterar(true);

        //Começa à desenhar novamente
        batch.begin();

        //Define que o texto à ser escrito é Fase + número da fase
        layout.setText(anunciador, "Fase " + fase);
        //Escreve
        anunciador.draw(batch, "Fase " + fase, largura / 2 - layout.width / 2, altura/2 - layout.height / 2);

        //Para de desenhar
        batch.end();

        //Aumenta tempo em 1
        tempo++;
        //Se tiver passado um segundo
        if (tempo == 60) {
            //Reinicia tempo
            tempo = 0;
            //Define o estado
            estado = "jogando";
        }
    }

    //Carregar as opções de pause
    private void carregarPause()
    {
        //Limpa a tela de preto
        ScreenUtils.clear(0, 0, 0, 1);
        //Chama os métodos dos botões de stageP
        stageP.act(Gdx.graphics.getDeltaTime());
        //Desenha os botões de stageP
        stageP.draw();
    }

    //Método para controlar a nave do jogador
    private void controlarJogador()
    {
        //Se tiver movimento para a direita
        if (movimento == 1 || Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            //Define a variação do movimento para a direita
            naveJ.setVariacao(largura / 150);
        //Se tiver movimento para a esquerda
        else if (movimento == -1 || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            //Define a variação do movimento para a esquerda
            naveJ.setVariacao(-largura / 150);
        //Caso não haja movimento
        else
            //Define a variação do movimento como neutra
            naveJ.setVariacao(0);

        //Aumenta a posição horizontal da nave de acordo com a variacao
        naveJ.aumentarX();

        //Se ele estiver atirando
        if (atirando == 1 || Gdx.input.isKeyPressed(Input.Keys.SPACE))
            //Se tiver passado o intervalo entre tiros
            if (ultimoTiroJ <= tempo) {
                //Define quando ele poderá atirar novamente
                ultimoTiroJ = tempo + 30;
                //Adiciona um tiro na posição da nave
                tiros.add(new Tiro(naveJ.getX() + naveJ.getTamanho() / 2, naveJ.getY() + naveJ.getTamanho(), 'j', altura));
            }
    }

    //Método para manipular os tiros
    private void manipularTiros()
    {
        //Se um inimigo foi morto
        boolean matou = false;

        //O indíce do tiro à ser removido
        int tiroASerRemovido = -1;

        //Para todos os tiros
        for (Tiro atual: tiros) {
            //Desenha o tiro atual
            atual.desenhar(batch);
            //Verifica se ele tocou em algo
            int res = atual.tocouEmAlgo(naveJ, inimigos);
            //Se ele saiu da tela
            if (res == -1) {
                //Salva o índice para ser removido
                tiroASerRemovido = tiros.indexOf(atual);
            }
            //Se o tiro inimigo tocou no jogador
            else if (res == -2) {
                //Diminui uma vida do jogador
                naveJ.mudarVida(-1);
                //Define o estado para a animação de morte do jogador
                estado = "morrendo";
                //Limpa os tiros
                tiros = new LinkedList<>();

                //Define que nenhum tiro deve ser removido
                tiroASerRemovido = -1;
                //Adiciona uma explosão de 1 segundo na posição do jogador
                explosoes.add(new Explosao(naveJ.getX(), naveJ.getY(), 60));

                //Para a música
                if(intro.isPlaying())
                    intro.stop();
                if(loop.isPlaying())
                    loop.stop();

                //Encerra o loop
                break;
            }
            //Se tiver acertado um inimigo
            else if (res != -3) {
                //Tira uma vida do inimigo atingido
                inimigos.get(res).mudarVida(-1);

                //Define que o indíce do tiro à ser removido
                tiroASerRemovido = tiros.indexOf(atual);

                //Se o inimigo ficou sem vidas
                if(!inimigos.get(res).estaVivo()) {
                    //Calcula a quantidade de pontos à serem recebidos
                    float variacaoPontos = (float) (Math.pow(inimigos.get(res).getY(), 4) / (Math.pow(altura, 3)));
                    //Aumenta a pontuação
                    pontuacao += variacaoPontos + variacaoPontos * Math.pow(valorFase, 2);

                    //Adiciona uma explosão no local do inimigo morto
                    explosoes.add(new Explosao(inimigos.get(res).getX(), inimigos.get(res).getY(), 15));

                    //Remove o inimigo
                    inimigos.remove(res);
                    //Define que matou um inimigo
                    matou = true;
                }
            }
        }
        //Se há um tiro a ser removido
        if(tiroASerRemovido > -1)
            //Remove o tiro
            tiros.remove(tiroASerRemovido);

        //Se um inimigo morreu
        if (matou)
            //Para cada inimigo
            for (Inimigo atual: inimigos) {
                //Acelera o inimigo
                atual.mudarVelocidade((float) (15 - inimigos.size()) / (float) 55);
            }
    }

    //Método para manipular os inimigos
    private void manipularInimigos()
    {
        //Se algum deles tocou na lateral da tela
        boolean alternar = false;

        //Se passou o tempo para invocar outro inimigo bônus
        if(tempo > cdNaveG)
        {
            //Invoca um inimigo bônus fora da tela
            Inimigo bonus = new Inimigo(-largura/15, altura - largura/15, largura/15, largura/15 * 51/75, largura, altura, 2);
            //Acelera o inimigo de acordo com a fase
            bonus.mudarVelocidade(valorFase);
            //Adiciona o inimigo
            inimigos.add(bonus);
            //Define o momento de invocar o próximo
            cdNaveG = tempo + (float)Math.random() * 500 + 500;
        }

        //Se os inimigo deve atirar
        if (ultimoTiroI < tempo) {
            //Define o próximo momento de atirarem, variando de 0,5 à 3 segundos
            ultimoTiroI += Math.random() * 150 + 30;

            //Quantidade de tiros
            int quantidade = 1;

            //Dependendo da quantidade de inimigos, é definida a quantidade de tiros
            if (inimigos.size() > 30)
                quantidade = 3;
            else if (inimigos.size() > 15)
                quantidade = 2;
            else if(inimigos.size() == 0)
                quantidade = 0;

            //Escolhe inimigos aleatórios para atirarem
            for (int i = 0; i < quantidade; i++)
                inimigos.get((int) (Math.random() * inimigos.size())).atirar(tiros);
        }
        //Para cada inimigo
        for (Inimigo atual: inimigos) {
            //Pega a resposta do movimento do inimigo
            int res = atual.andar();

            //Se ele tocou na lateral
            if (res == 1)
                //Define que deve inverter o movimento dos inimigos
                alternar = true;
            //Se o inimigo alcançou a base da tela
            else if (res == 2) {
                //Define que o jogador perdeu
                estado = "perdeu";
                //Para o loop
                break;
            }
            //Se o inimigo bônus saiu da tela
            else if(res == 3)
                //Remove o inimigo bônus
                inimigos.remove(atual);
            //Se for um inimigo atirador e for sua hora de atirar
            else if(res == 4)
                //Ele atira
                atual.atirar(tiros);
            //Caso atual não seja o inimigo bônus que saiu da tela
            if(res != 3)
                //Desenha o inimigo
                atual.desenhar(batch);
        }
        //Se algum inimigo alcançou o limite lateral da tela
        if (alternar)
            //Todos os inimigos invertem seu movimento
            for (Inimigo atualA: inimigos) {
                atualA.alternar();
            }
    }

    //Método para animar as explosões
    private void animarExplosoes()
    {
        //Para todas as explosões
        for(Explosao atual: explosoes)
        {
            //Se tiver terminado a animação
            if(!atual.animar(batch))
            {
                //Remove a explosão
                explosoes.remove(atual);
                //Encerra o loop
                break;
            }
        }
    }

    //Método para definir o tamanho da tela
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

    //Ao fechar a tela
    @Override
    public void hide() {
        //Libera a memória
        batch.dispose();
        stage.dispose();
        stageP.dispose();
        naveJ = null;
        fundo.dispose();
        tiros = null;
        inimigos = null;
        explosoes = null;
        scoreboard.dispose();
        anunciador.dispose();
        intro.dispose();
        loop.dispose();
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
