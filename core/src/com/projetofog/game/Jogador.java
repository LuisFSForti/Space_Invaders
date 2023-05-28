package com.projetofog.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

//Classe que controla o jogador
public class Jogador {
    //Dados sobre a posição, o tamanho, a velocidade de movimento e a altura da tela
    private float posicaoX,posicaoY, tamanho, variacao, altura;
    //A vida do jogador
    private int vida;
    //A foto da nave
    private Texture foto;

    //Ao criar um objeto da classe deve ser passado o tamanho da tela
    public Jogador(float largura, float altura)
    {
        //Pega o sprite da nave
        this.foto = new Texture("nave.png");
        //Calcula o tamanho baseado na largura da tela
        this.tamanho = largura / 16;
        //Define o restante dos dados
        reiniciarPosicao();
        this.vida = 2;
        //Salva a altura
        this.altura = altura;
    }

    //Método para reiniciar a posição
    public void reiniciarPosicao()
    {
        this.posicaoX = tamanho * 8 - tamanho/2;
        this.posicaoY = tamanho / 2;
    }

    //Método de movimento
    public void aumentarX()
    {
        //Aumenta a posição de acordo com a variação
        posicaoX += variacao;
        //Caso esteja saindo da tela para a esquerda
        if(posicaoX < 20)
            //Coloca a nave de volta no limite da esquerda
            posicaoX = 20;
        //Caso esteja saindo da tela para a direita
        if (posicaoX > tamanho * 16 - (tamanho + 20))
            //Coloca a nave de volta no limite da direita
            posicaoX = tamanho * 16 - (tamanho + 20);
    }

    //Método para desenhar a nave
    public void desenharNave(Batch batch)
    {
        batch.draw(foto, posicaoX, posicaoY, tamanho, tamanho);

    }

    //Método para desenhar as vidas
    public void desenharVida(Batch batch)
    {
        //Define a posição horizontal baseada no tamanho (o qual é 1/16 da largura da tela)
        float posicao = tamanho / 20;

        //Para cada vida
        for(int i = 0; i < vida; i++) {
            //Desenha a vida no canto superior esquerdo
            batch.draw(foto, posicao, altura - tamanho / 20 - tamanho / 2, tamanho / 2, tamanho / 2);
            //Aumenta a posição, movendo a próxima vida mais para a direita
            posicao += tamanho / 2 * 1.1F;
        }
    }

    //Método para mudar a vida
    public void mudarVida(int mudanca)
    {
        //Aumenta a vida de acordo com mudanca
        vida += mudanca;
        //Caso tenha mais que 10 vidas
        if(vida > 10)
            //Define que ele tem 10 vidas
            vida = 10;
    }

    //Método para definir a velocidade de movimento
    public void setVariacao(float aumento)
    {
        variacao = aumento;
    }

    //Métodos básicos para pegar informações
    public float getX()
    {
        return posicaoX;
    }

    public float getY()
    {
        return posicaoY;
    }

    public float getTamanho()
    {
        return tamanho;
    }

    public boolean estaVivo()
    {
        return vida > 0;
    }
}
