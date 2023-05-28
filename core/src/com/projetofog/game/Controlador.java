package com.projetofog.game;

import static java.lang.System.exit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

//Esta é a classe responsável por manipular qual tela está ativa
public class Controlador extends Game {
    //String que define qual tela carregar
    private String estado;
    //Todas as telas
    private MenuPrincipal menuPrincipal;
    private ComoJogar comoJogar;
    private Jogo jogo;
    private Pontuacao pontuacao;
    //Tamanho da tela
    int largura, altura;

    //Ao abrir o programa
    @Override
    public void create() {
        //Define que deve ir para o menuPrincipal
        estado = "menuPrincipal";

        //Salva a largura e a altura da tela
        largura = Gdx.graphics.getWidth();
        altura = Gdx.graphics.getHeight();
    }

    //Para ir para o menu
    void trocarParaMenu()
    {
        //Limpa as demais telas
        jogo = null;
        pontuacao = null;
        comoJogar = null;
        //Cria a tela de menuPrincipal
        menuPrincipal = new MenuPrincipal();
        //Passa o tamanho da tela
        menuPrincipal.resize(largura, altura);
        //Define que será a tela ativa
        setScreen(menuPrincipal);
    }
    //Para ir para as instruções
    void trocarParaComoJogar()
    {
        //Limpa apenas o menuPrincipal, pois os demais já estarão limpos
        menuPrincipal = null;
        //Cria a tela, passa o tamanho e define como a tela atual
        comoJogar = new ComoJogar();
        comoJogar.resize(largura, altura);
        setScreen(comoJogar);
    }

    //Para ir para o jogo
    void trocarParaJogo()
    {
        //Limpa o menu principal
        menuPrincipal = null;
        //Cria a tela, passa o tamanho e define como a tela atual
        jogo = new Jogo();
        jogo.resize(largura, altura);
        setScreen(jogo);
    }

    //Para ir para a tela de pontuação
    void trocarParaPontuacao()
    {
        //Cria a tela e passa o tamanho
        pontuacao = new Pontuacao();
        pontuacao.resize(largura, altura);
        //Passa a pontuação do jogador e limpa a tela do jogo
        pontuacao.setPontuacao(jogo.getPontuacao());
        jogo = null;
        //Define como sendo a tela atual
        setScreen(pontuacao);
        //Dependendo de como o jogo terminou, define a mensagem
        if(estado == "pontuacao1")
            pontuacao.setMensagem("Você morreu!");
        else
            pontuacao.setMensagem("A Terra foi invadida!");
    }

    //Método que é chamado 60 vezes por segundo
    @Override
    public void render()
    {
        //Dependendo da tela ativa
        switch (estado) {
            //Caso seja o menu principal
            case "menuPrincipal":
                //Se ele estiver vazio, chama o método para trocar para o menu
                if(menuPrincipal == null)
                    trocarParaMenu();
                else {
                    //Chama o método principal da tela
                    menuPrincipal.render(60);
                    //Caso o usuário tenha clicado no botão de jogar
                    if (menuPrincipal.getEstado() == "jogar")
                        //Define o novo estado
                        estado = "jogando";
                    //Caso o usuário tenha clicado no botão de como jogar
                    else if (menuPrincipal.getEstado() == "como_jogar")
                        //Define o novo estado
                        estado = "como_jogar";
                }
                break;
            //Caso seja as instruções
            case "como_jogar":
                //Caso esteja vazio, chama o método para trocar
                if(comoJogar == null)
                    trocarParaComoJogar();
                else
                {
                    //Chama o método principal
                    comoJogar.render(60);
                    //Caso o usuário queira sair da tela, troca o estado
                    if(comoJogar.getEstado() == "sair")
                        estado = "menuPrincipal";
                }
                break;
            //Caso esteja jogando
            case "jogando":
                //Caso esteja vazio, chama o método para trocar
                if(jogo == null)
                    trocarParaJogo();
                else {
                    //Chama o método principal
                    jogo.render(60);
                    //Caso o usuário tenha mandado reiniciar
                    if(jogo.getEstado() == "reiniciar")
                        //Chama o método para trocar para o jogo, efetivamente reiniciando
                        trocarParaJogo();
                    //Caso tenha morrido
                    if (jogo.getEstado() == "morreu")
                        estado = "pontuacao1";
                    //Caso os inimigos tenham alcançado a base da tela
                    if(jogo.getEstado() == "perdeu")
                        estado = "pontuacao2";
                }

                break;
            //Caso esteja na tela de pontuação
            case "pontuacao1":
            case "pontuacao2":
                //Caso esteja vazio, chama o método para trocar
                if(pontuacao == null)
                    trocarParaPontuacao();
                else {
                    //Chama o método principal
                    pontuacao.render(60);
                    //Caso queira sair, troca o estado
                    if(pontuacao.getEstado() == "sair")
                        estado = "menuPrincipal";
                }
                break;
            //Caso seja um estado não definido (ou seja, um erro), fecha o jogo
            default:
                exit(1);
                break;
        }
    }
}
