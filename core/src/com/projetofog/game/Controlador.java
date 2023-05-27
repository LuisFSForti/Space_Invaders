package com.projetofog.game;

import static java.lang.System.exit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Controlador extends Game {
    private String estado;
    private MenuPrincipal menuPrincipal;
    private ComoJogar comoJogar;
    private Jogo jogo;
    private Pontuacao pontuacao;
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
        pontuacao = null;
        comoJogar = null;
        menuPrincipal = new MenuPrincipal();
        menuPrincipal.resize(largura, altura);
        setScreen(menuPrincipal);
    }
    void trocarParaComoJogar()
    {
        menuPrincipal = null;
        comoJogar = new ComoJogar();
        comoJogar.resize(largura, altura);
        setScreen(comoJogar);
    }

    void trocarParaJogo()
    {
        menuPrincipal = null;
        pontuacao = null;
        jogo = new Jogo();
        jogo.resize(largura, altura);
        setScreen(jogo);
    }
    void trocarParaPontuacao()
    {
        menuPrincipal = null;
        pontuacao = new Pontuacao();
        pontuacao.resize(largura, altura);
        pontuacao.setPontuacao(jogo.getPontuacao());
        jogo = null;
        setScreen(pontuacao);
        switch (estado)
        {
            case "pontuacao1": pontuacao.setMensagem("Você morreu!"); break;
            case "pontuacao2": pontuacao.setMensagem("A Terra foi invadida!"); break;
            case "pontuacao3": pontuacao.setMensagem("Você ganhou!"); break;
        }
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
                        estado = "como_jogar";
                }
                break;
            case "como_jogar":
                if(comoJogar == null)
                    trocarParaComoJogar();
                else
                {
                    comoJogar.render(60);
                    if(comoJogar.getEstado() == "sair")
                        estado = "menuPrincipal";
                }
                break;
            case "jogando":
                if(jogo == null)
                    trocarParaJogo();
                else {
                    jogo.render(60);
                    if(jogo.getEstado() == "reiniciar")
                        trocarParaJogo();
                    if (jogo.getEstado() == "morreu")
                        estado = "pontuacao1";
                    if(jogo.getEstado() == "perdeu")
                        estado = "pontuacao2";
                    if (jogo.getEstado() == "venceu")
                        estado = "pontuacao3";
                }

                break;
            case "pontuacao1":
            case "pontuacao2":
            case "pontuacao3":
                if(pontuacao == null)
                    trocarParaPontuacao();
                else {
                    pontuacao.render(60);
                    if(pontuacao.getEstado() == "sair")
                        estado = "menuPrincipal";
                }
                break;
            default:
                exit(1);
                break;
        }
    }
}
