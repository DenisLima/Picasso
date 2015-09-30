package com.android.desafio.picasso;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by denis on 28/09/15.
 */
public class Dribbble {

    private String id;
    private String imagem;

    public Dribbble() {
    }

    public Dribbble(String id, String imagem){
        super();
        this.id = id;
        this.imagem = imagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String toString(){
        return id;
    }

}
