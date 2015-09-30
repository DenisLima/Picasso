package com.android.desafio.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by denis on 28/09/15.
 */
public class ListaDribbbleAdapter extends ArrayAdapter<Dribbble> {

    private Context context;
    private List<Dribbble> dribbbles = null;
    ProgressBar prog;

    public ListaDribbbleAdapter(Context context, List<Dribbble> dribbbles) {
        super(context, 0, dribbbles);
        this.dribbbles = dribbbles;
        this.context = context;
    }

    public class ViewHolder {

        public TextView tituloText;
        public ImageView imagem;

    }

    public View getView(int position, View view, ViewGroup parent) {

        Dribbble dribbble = dribbbles.get(position);

        view = LayoutInflater.from(context).inflate(R.layout.lista_custom, null);

        ImageView imageViewDribbble = (ImageView) view.findViewById(R.id.imageView);

        Picasso.with(getContext()).load(dribbble.getImagem())
                    .placeholder(R.drawable.carregando)
                    .into(imageViewDribbble);

        //imageViewDribbble.setImageResource(dribbble.getImagem());

        TextView textViewDribbble = (TextView) view.findViewById(R.id.textView);
        textViewDribbble.setText(dribbble.getId());

        return view;

    }

}
