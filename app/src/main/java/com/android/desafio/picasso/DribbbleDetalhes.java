package com.android.desafio.picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 29/09/15.
 */
public class DribbbleDetalhes extends Activity {

    String id;
    ProgressDialog dialog;
    ImageView imgPrincipal;
    ImageView imgAutor;
    TextView txtNome;
    TextView txtDescricao;

    public void onCreate(Bundle icicle){
        super.onCreate(icicle);
        setContentView(R.layout.dribbble_detalhes);

        Intent it = getIntent();
        id = it.getStringExtra("id");

        MontaDetalhes(id);

        imgPrincipal = (ImageView) findViewById(R.id.imgPrincipal);
        imgAutor = (ImageView) findViewById(R.id.imgAutor);
        txtNome = (TextView) findViewById(R.id.txtAutor);
        txtDescricao = (TextView) findViewById(R.id.txtDescricao);

        Button btnVoltar = (Button) findViewById(R.id.btnVoltarDetalhes);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void MontaDetalhes(final String id){

        AsyncTask<String, Object, String> asyncTask = new AsyncTask<String, Object, String>() {

            @Override
            protected void onPreExecute(){
                dialog = new ProgressDialog(DribbbleDetalhes.this);
                dialog.setMessage("Carregando Detalhes...");
                dialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = null;
                post = new HttpPost("http://10.55.1.242/nova_intranet/views/ti/dribbble.php");

                try {

                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("acao", "detalhes"));
                    pairs.add(new BasicNameValuePair("id", id));
                    post.setEntity(new UrlEncodedFormEntity(pairs));

                    HttpResponse response = client.execute(post);
                    String responseString = EntityUtils.toString(response.getEntity());

                    return responseString.trim();

                } catch (Exception e) {
                    return null;
                }
            }

            protected void onPostExecute(String result) {

                dialog.dismiss();

                String r[] = result.split(";");

                //Toast.makeText(DribbbleDetalhes.this, "Sucesso: "+result, Toast.LENGTH_LONG).show();

                txtNome.setText(r[2]);
                txtDescricao.setText(r[0]);

                Picasso.with(DribbbleDetalhes.this).load(r[1]).placeholder(R.mipmap.ic_launcher).into(imgPrincipal);
                Picasso.with(DribbbleDetalhes.this).load(r[3]).placeholder(R.mipmap.ic_launcher).into(imgAutor);

            }

        };
        asyncTask.execute();

    }

}
