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

//Tela do menu principal
public class MenuPrincipal implements Screen {
    //Tamanho da tela
    float largura, altura;
    //Objeto que controla as texturas
    private SpriteBatch batch;
    //Objeto que controla o botão
    private Stage stage;
    //Sprites utilizados
    private Texture fundo, titulo;
    private Drawable fotoJogar, fotoComoJogar;
    //Botões para jogar e para ir para a tela de como jogar
    private ImageButton btnJogar, btnComoJogar;
    private String estado;

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

        //Pega as texturas
        fundo = new Texture("space.jpg");
        titulo = new Texture("titulo.png");

        //Cria as texturas dos botões
        fotoJogar = new TextureRegionDrawable(new TextureRegion((new Texture("jogar.png"))));
        fotoComoJogar = new TextureRegionDrawable(new TextureRegion((new Texture("comojogar.png"))));

        //Define os tamanhos
        fotoJogar.setMinWidth(altura / 2);
        fotoJogar.setMinHeight(altura / 8);
        fotoComoJogar.setMinWidth(altura / 2);
        fotoComoJogar.setMinHeight(altura / 8);

        //Cria os botões
        btnJogar = new ImageButton(fotoJogar);
        btnComoJogar = new ImageButton(fotoComoJogar);

        //Define as posições
        btnJogar.setPosition(largura/2 - altura/4, altura/2 - altura / 8);
        btnComoJogar.setPosition(largura/2 - altura/4, altura/2 - altura / 3);

        //Criação de eventos para os botões
        btnJogar.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Caso seja clicado, muda o estado
                estado = "jogar";
                return true;
            }
            /*public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                testeT = false;
            }*/
        });
        btnComoJogar.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Caso seja clicado, muda o estado
                estado = "como_jogar";
                return true;
            }
            /*public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                testeT = false;
            }*/
        });

        //Adiciona os botões ao stage
        stage.addActor(btnJogar);
        stage.addActor(btnComoJogar);
    }

    //Método principal
    @Override
    public void render(float delta) {
        //Limpa a tela
        ScreenUtils.clear(0, 0, 0, 1);
        //Começa à desenhar
        batch.begin();
        //Desenha o fundo e o título
        batch.draw(fundo, 0, 0, largura, altura);
        batch.draw(titulo, largura/2 - altura/3, altura/2 - altura/30, 2 * altura/3, 2 * altura/3);
        //Para de desenhar
        batch.end();

        //Chama os métodos dos botões
        stage.act(Gdx.graphics.getDeltaTime());
        //Desenha os botões
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
