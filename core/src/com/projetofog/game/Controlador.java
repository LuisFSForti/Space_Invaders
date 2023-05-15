package com.projetofog.game;

import static java.lang.System.exit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Controlador extends Game {
    private String estado;
    private MenuPrincipal menuPrincipal;
    private Jogo jogo;
    int largura, altura;

    @Override
    public void create() {
        estado = "menuPrincipal";

        largura = Gdx.graphics.getWidth();
        altura = Gdx.graphics.getHeight();
    }

    void trocarParaMenu()
    {
        jogo = null;
        menuPrincipal = new MenuPrincipal();
        menuPrincipal.resize(largura, altura);
        setScreen(menuPrincipal);
    }

    void trocarParaJogo()
    {
        menuPrincipal = null;
        jogo = new Jogo();
        jogo.resize(largura, altura);
        setScreen(jogo);
    }

    @Override
    public void render()
    {
        switch (estado) {
            case "menuPrincipal":
                if(menuPrincipal == null)
                    trocarParaMenu();
                else {
                    menuPrincipal.render(60);
                    if (menuPrincipal.getEstado() == "jogar")
                        estado = "jogando";
                    else if (menuPrincipal.getEstado() == "como_jogar")
                        estado = "instrucoes";
                }
                break;
            case "jogando":
                if(jogo == null)
                    trocarParaJogo();
                else {
                    jogo.render(60);
                    if (jogo.getEstado() == "acabou")
                        estado = "pontuacao";
                }

                break;
            case "pontuacao":
            case "instrucoes":

            default:
                exit(1);
                break;
        }
    }
}
