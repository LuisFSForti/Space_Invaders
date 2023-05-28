package com.projetofog.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.LinkedList;

//Classe que controla os inimigos
public class Inimigo {
    //Dados sobre a posição, o tamanho do inimigo, o tamanho da tela, a velocidade de movimento
    private float posicaoX, posicaoY, tamanhoX, tamanhoY, largura, altura, variacao;
    //Variável exclusiva do inimigo atirador para controlar seus tiros
    private float tempoTiroAtirador;
    //A vida e o tipo do inimigo
    private int vida, tipo;
    //O sprite
    private Texture foto;

    //Ao criar um objeto da classe devem ser passados: a posição, o tamanho do inimigo, o tamanho da
    //tela e o tipo de inimigo
    public Inimigo(float pX, float pY, float tX, float tY, float largura, float altura, int tipo) {
        //Salva os dados passados
        this.posicaoX = pX;
        this.posicaoY = pY;
        this.tamanhoX = tX;
        this.tamanhoY = tY;
        this.largura = largura;
        this.altura = altura;

        //Define a velocidade de movimento
        variacao = largura / 750;

        //Salva a vida e o tipo
        this.vida = 1;
        this.tipo = tipo;

        //Dependendo do tipo de inimigo
        switch (tipo) {
            //Caso seja o básico
            case 1:
                //Salva o sprite dele
                foto = new Texture("inimigo.png"); break;

            //Caso seja o inimigo bônus
            case 2:
                //Salva o sprite dele
                foto = new Texture("inimigoG.png"); break;
            //Caso seja o inimigo tanque
            case 3:
                //Salva o sprite dele
                foto = new Texture("inimigoT.png");
                //Define sua vida máxima como 3
                vida = 3;
                break;
            //Caso seja o inimigo atirador
            case 4:
                //Salva o sprite dele
                foto = new Texture("inimigoA.png");
                //Reinicia sua variável de controle dos tiros
                tempoTiroAtirador = 0;
                break;
        }
    }

    //Método que analisa o movimento
    public int andar()
    {
        //Se não for o inimigo bônus
        if(tipo != 2) {
            //Anda para o lado
            posicaoX += variacao;

            //Caso tenha alcançado a base da tela
            if (posicaoY < (float) (altura / 15.0 + largura / 16))
                //Retorna que o jogador perdeu
                return 2;

            //Caso tenha tocado algum lado da tela
            if (posicaoX > largura - largura / 24 - tamanhoX || posicaoX < largura / 24)
                //Retorna que eles devem inverter seu movimento
                return 1;

            //Caso seja o atirador
            if(tipo == 4)
            {
                //Aumenta seu tempoAtirador em 1
                tempoTiroAtirador++;
                //Caso sua variável seja divisível por 90 (ou seja, a cada 1 segundo e meio)
                if(tempoTiroAtirador % 90 == 0)
                    //Retorna que ele deve disparar
                    return 4;
            }
        }
        //Caso seja o inimigo bônus
        else
        {
            //Ele anda o dobro dos demais
            posicaoX += variacao * 2;

            //Caso tenha saído da tela
            if (posicaoX > largura + tamanhoX)
                //Alerta para removê-lo
                return 3;
        }

        //Retorna que não aconteceu nada especial
        return 0;
    }

    //Método para inverter a direção do movimento
    public void alternar()
    {
        //Se não for o inimigo bônus
        if(tipo != 2) {
            //Desce um pouco
            posicaoY -= altura / 30;
            //Inverte seu movimento
            variacao *= -1;
        }
    }

    //Método para alterar a velocidade
    public void mudarVelocidade(float mudanca)
    {
        //Contanto que acelere
        if(mudanca > 0)
            //Aumenta proporcionalmente ao valor passado
            variacao += variacao * mudanca;
    }

    //Método para desenhar o inimigo
    public void desenhar(Batch batch)
    {
        //Desenha
        batch.draw(foto, posicaoX, posicaoY, tamanhoX, tamanhoY);
    }

    //Método para atirar
    public void atirar(LinkedList<Tiro> tiros)
    {
        //Caso seja o inimigo básico ou o atirador
        if(tipo == 1 || tipo == 4) {
            //Adiciona um tiro, inicialmente na posição do inimigo
            tiros.add(new Tiro(posicaoX + tamanhoX / 2, posicaoY - altura / 50, 'I', altura));
        }
    }

    //Métodos para alterações básicas e para pegar dados
    public void mudarVida(int mudanca)
    {
        vida += mudanca;
    }

    public float getX()
    {
        return posicaoX;
    }

    public float getY()
    {
        return posicaoY;
    }

    public float getTamanhoX()
    {
        return tamanhoX;
    }

    public float getTamanhoY()
    {
        return tamanhoY;
    }

    public boolean estaVivo()
    {
        return vida > 0;
    }
}
