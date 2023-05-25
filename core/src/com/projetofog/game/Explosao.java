package com.projetofog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Explosao {
    private int frame;
    private Texture animacao1, animacao2, animacao3, animacao4;
    private float x, y, duracao;
    private Music explosao;

    public Explosao(float x, float y, float duracao)
    {
        //animacao = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("explosao.gif").read());
        animacao1 = new Texture(Gdx.files.internal("explosao1.png"));
        animacao2 = new Texture(Gdx.files.internal("explosao2.png"));
        animacao3 = new Texture(Gdx.files.internal("explosao3.png"));
        animacao4 = new Texture(Gdx.files.internal("explosao4.png"));
        frame = 0;
        this.x = x;
        this.y = y;
        explosao = Gdx.audio.newMusic(Gdx.files.internal("explosao.mp3"));
        explosao.setVolume(0.075F);
        explosao.play();
        explosao.setLooping(false);
        this.duracao = duracao;
    }

    public boolean animar(SpriteBatch batch)
    {
        if(frame < 4 * duracao / 3) {
                if(frame < duracao/3)
                {
                    batch.draw(animacao1, x, y);
                }
                else if(frame < 2*duracao/3)
                {
                    batch.draw(animacao2, x, y);
                }
                else if(frame < duracao)
                {
                    batch.draw(animacao3, x, y);
                }
                else
                {
                    batch.draw(animacao4, x, y);
                }
            frame++;
            return true;
        }
        else
        {
            return false;
        }
    }
}