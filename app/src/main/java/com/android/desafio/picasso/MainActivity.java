package com.android.desafio.picasso;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    String page = "1";
    ProgressDialog dialog;
    private List<Dribbble> listagem = null;

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        GeraDribbbles(page);

        //new GeraDribbbles().execute();

        Button btnAnterior = (Button) findViewById(R.id.btnAnterior);
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ant_page = (Integer.parseInt(page)-1);

                if (ant_page <= 0){
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Aviso");
                    alert.setMessage("Não existe página anterior!");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.show();
                }else {
                    ant_page = (Integer.parseInt(page)-1);
                    page = Integer.toString(ant_page);
                    GeraDribbbles(page);
                }

            }
        });

        Button btnProx = (Button) findViewById(R.id.btnProximo);
        btnProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prox_page = (Integer.parseInt(page)+1);
                page = Integer.toString(prox_page);

                GeraDribbbles(page);

            }
        });

    }

 /*   public List<Dribbble> MontaDribbbles() {

        //ValidaAcesso();

        List<Dribbble> dribbbles = new ArrayList<Dribbble>();
        dribbbles.add(criarDribbbles("Res: ", R.drawable.linux));
        dribbbles.add(criarDribbbles("Res: ", R.drawable.windows));
        dribbbles.add(criarDribbbles("Res: ", R.drawable.android));
        dribbbles.add(criarDribbbles("Res: ", R.mipmap.ic_launcher));

        return dribbbles;

    }*/

    public void GeraDribbbles (final String pagina) {

        AsyncTask<String, Object, String> asyncTask = new AsyncTask<String, Object, String>(){

            @Override
            protected void onPreExecute () {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Obtendo Dados da API...");
                dialog.show();
            }

            @Override
            protected String doInBackground (String...params){
                HttpClient client = new DefaultHttpClient();
                HttpPost post = null;
                post = new HttpPost("http://10.55.1.242/nova_intranet/views/ti/dribbble.php");

                try {

                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("acao", "montagem"));
                    pairs.add(new BasicNameValuePair("pagina", pagina));
                    post.setEntity(new UrlEncodedFormEntity(pairs));

                    HttpResponse response = client.execute(post);
                    String responseString = EntityUtils.toString(response.getEntity());

                    return responseString.trim();

                } catch (Exception e) {
                    return null;
                }
            }

        protected void onPostExecute (String result){

            dialog.dismiss();

            String r[] = result.split(";");
            int contador = Integer.parseInt(r[0]);
            List<Dribbble> dribbbles = new ArrayList<Dribbble>();

                for(int n = 1; n <= contador; n++){

                    String id[] = r[n].split("/");
                    dribbbles.add(criarDribbbles(id[6], r[n]));

                }


        /*    dribbbles.add(criarDribbbles("Res: ", "https://d13yacurqjgara.cloudfront.net/users/23587/screenshots/2266494/bear.png"));
            dribbbles.add(criarDribbbles("Res: ", "https://d13yacurqjgara.cloudfront.net/users/3820/screenshots/2266786/reject-idea.gif"));
            dribbbles.add(criarDribbbles("Res: ", "https://d13yacurqjgara.cloudfront.net/users/146798/screenshots/2267069/111-01.png"));
            dribbbles.add(criarDribbbles("Res: ", "https://d13yacurqjgara.cloudfront.net/users/320587/screenshots/2266508/heron.jpg"));
        */
            final List<Dribbble> dribbble = dribbbles;

            final ListaDribbbleAdapter listaDribbbleAdapter = new ListaDribbbleAdapter(MainActivity.this, dribbble);
            setListAdapter(listaDribbbleAdapter);

            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Dribbble dribbble1 = listaDribbbleAdapter.getItem(position);

                    //Toast.makeText(MainActivity.this, dribbble1.getId(), Toast.LENGTH_LONG).show();

                    Intent it = new Intent(MainActivity.this, DribbbleDetalhes.class);
                    it.putExtra("id",dribbble1.getId());
                    startActivity(it);

                }
            });

        }

        };asyncTask.execute();
    }


    private Dribbble criarDribbbles(String id, String image) {
        Dribbble dribbble = new Dribbble(id, image);
        return dribbble;
    }

}
