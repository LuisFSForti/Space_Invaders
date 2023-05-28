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

//Tela de instruções
public class ComoJogar implements Screen {
    //Tamanho da tela
    float largura, altura;
    //Objeto que controla as texturas
    private SpriteBatch batch;
    //Objeto que controla o botão
    private Stage stage;
    //Sprites utilizados
    private Texture fundo, instrucoes;
    private Drawable fotoSair;
    //Botão para sair
    private ImageButton btnSair;
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
        instrucoes = new Texture("telaComoJogar.png");

        //Cria a textura do botão
        fotoSair = new TextureRegionDrawable(new TextureRegion((new Texture("exit.png"))));
        //Define o tamanho
        fotoSair.setMinWidth(altura / 8);
        fotoSair.setMinHeight(altura / 8);

        //Cria o botão
        btnSair = new ImageButton(fotoSair);
        //Define a postição
        btnSair.setPosition(largura/2 + altura/4 - altura / 16, altura/2 - altura / 3 - altura / 16);
        //Criação de eventos para o botão
        btnSair.addListener(new ClickListener(){
            //Ao ser tocado
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Define o novo estado
                estado = "sair";
                return true;
            }
        });

        //Adiciona o botão ao stage
        stage.addActor(btnSair);
    }

    //Método principal
    @Override
    public void render(float delta) {
        //Limpa a tela
        ScreenUtils.clear(0, 0, 0, 1);
        //Começa à desenhar
        batch.begin();
        //Desenha o fundo e as instruções
        batch.draw(fundo, 0, 0, largura, altura);
        batch.draw(instrucoes, largura/2 - altura/2, 0, altura, altura);
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
