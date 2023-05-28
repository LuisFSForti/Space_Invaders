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

//Tela de pontuação
public class Pontuacao implements Screen {
    //Tamanho da tela
    private float largura, altura;
    //Objeto que controla as texturas
    private SpriteBatch batch;
    //Objeto que controla o botão
    private Stage stage;
    //Sprites utilizados
    private Texture fundo;
    private Drawable fotoSair;
    //Botão para sair
    private ImageButton btnSair;
    //Fonte utilizada para escrever
    private BitmapFont scoreboard;
    //Objeto auxiliar para a formatação
    private GlyphLayout layout;
    //Estado e a mensagem à ser escrita
    private String estado, mensagem;
    private int pontuacao;

    //Ao abrir a tela
    @Override
    public void show() {
        //Define um estado neutro
        estado = "nada";

        //Instancia batch e stage
        batch = new SpriteBatch();
        stage = new Stage();
        //Define stage como receptor de toques da tela
        Gdx.input.setInputProcessor(stage);

        //Pega a textura do fundo
        fundo = new Texture("space.jpg");

        //Cria a textura do botão
        fotoSair = new TextureRegionDrawable(new TextureRegion((new Texture("exit.png"))));
        //Define o tamanho
        fotoSair.setMinWidth(largura / 8);
        fotoSair.setMinHeight(largura / 8);

        //Cria o botão
        btnSair = new ImageButton(fotoSair);
        //Define a posição
        btnSair.setPosition(largura/2 - largura/16, altura/3 - altura / 8);
        //Criação de eventos para o botão
        btnSair.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Ao ser tocado, define o novo estado
                estado = "sair";
                return true;
            }
        });

        //Adiicona o botão ao stage
        stage.addActor(btnSair);

        //Instancia scoreboard usando a fonte padrão
        scoreboard = new BitmapFont();
        //Define a cor da letra como branca
        scoreboard.setColor(1,1,1,1);
        //Define a escala da fonte
        scoreboard.getData().setScale(largura / 200);
        //Instancia layout
        layout = new GlyphLayout();
    }

    //Método principal
    @Override
    public void render(float delta) {
        //Limpa a tela
        ScreenUtils.clear(0, 0, 0, 1);
        //Começa à desenhar
        batch.begin();
        //Desenha o fundo
        batch.draw(fundo, 0, 0, largura, altura);

        //Define que o texto à ser escrito será a mensagem de derrota
        layout.setText(scoreboard, mensagem);
        //Escreve a mensagem
        scoreboard.draw(batch, mensagem, largura / 2 - layout.width / 2, altura / 2 + 3 * altura / 8);

        //Define que o texto à ser escrito será a pontuação
        layout.setText(scoreboard, "Score: " + pontuacao);
        //Escreve a pontuação
        scoreboard.draw(batch, "Score: " + pontuacao, largura / 2 - layout.width / 2, altura / 2 + altura / 8);

        //Para de desenhar
        batch.end();

        //Chama o método do botão
        stage.act(Gdx.graphics.getDeltaTime());
        //Desenha o botão
        stage.draw();
    }

    //Método para definir o tamanho da tela
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

    //Ao fechar a tela
    @Override
    public void hide() {
        //Libera a memória
        batch.dispose();
        stage.dispose();
        fundo.dispose();
        scoreboard.dispose();
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
