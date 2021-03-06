package com.cristovancamilo.apoiobm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cristovancamilo.apoiobm.R;
import com.cristovancamilo.apoiobm.api.ApoioBMService;
import com.cristovancamilo.apoiobm.helper.RetrofitConfig;
import com.cristovancamilo.apoiobm.model.AcompanhaAbate;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private TextView textoTotalAbate, textoAbatidos, textoRestam;
    private ProgressBar pbQuantidadeAbate;
    private List<AcompanhaAbate> listaAcompanhaAbate = new ArrayList<>();
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        retrofit = RetrofitConfig.getRetrofit();
        textoAbatidos = findViewById(R.id.textViewAbatidos);
        textoRestam = findViewById(R.id.textViewRestam);
        textoTotalAbate = findViewById(R.id.textViewTotalAbate);
        pbQuantidadeAbate = findViewById(R.id.pbProgressoAbate);

    }



    public void abrirEstoqueBloqueado(View view) {
        Intent intent = new Intent(MainActivity.this, EstoqueBloqueadoActivity.class);
        startActivity(intent);
    }

    public void abrirCamaras(View view) {
        Intent intent = new Intent(MainActivity.this, CamarasActivity.class);
        startActivity(intent);
    }

    public void abrirEscalaAbate(View view) {
        Intent intent = new Intent(MainActivity.this, EscalaAbateActivity.class);
        startActivity(intent);
    }

    public void abrirQuantidadesLote(View view) {
        Intent intent = new Intent(MainActivity.this, QuantidadesLoteActivity.class);
        intent.putExtra("num_lote", "00");
        startActivity(intent);
    }

    public void abrirQuantidadesAbate(View view) {
        Intent intent = new Intent(MainActivity.this, QuantidadesAbateActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ativaTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    public void ativaTimer() {
        int delay = 0;
        int interval = 10000;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                atualizarQuantidadesAbate();
            }
        }, delay, interval);
    }

    public void atualizarQuantidadesAbate() {
        ApoioBMService apoioBMService = retrofit.create(ApoioBMService.class);
        Call<List<AcompanhaAbate>> call = apoioBMService.recuperarAcompanhaAbate();

       call.enqueue(new Callback<List<AcompanhaAbate>>() {
            @Override
            public void onResponse(Call<List<AcompanhaAbate>> call, Response<List<AcompanhaAbate>> response) {
                if(response.isSuccessful()) {

                    listaAcompanhaAbate.clear();
                    listaAcompanhaAbate = response.body();

                    int abatidos = (int) Float.parseFloat(listaAcompanhaAbate.get(0).getAbatidos().replace(",", "."));
                    int restam = (int) Float.parseFloat(listaAcompanhaAbate.get(0).getRestam().replace(",", "."));

                    textoTotalAbate.setText("Total: " + listaAcompanhaAbate.get(0).getTotal());
                    textoAbatidos.setText("Abatidos: " + abatidos);
                    textoRestam.setText("Restam: " + restam);

                    textoTotalAbate.setTextColor(getApplicationContext().getResources().getColor(R.color.verde_texto_online));
                    textoAbatidos.setTextColor(getApplicationContext().getResources().getColor(R.color.verde_texto_online));
                    textoRestam.setTextColor(getApplicationContext().getResources().getColor(R.color.verde_texto_online));

                    pbQuantidadeAbate.setMax(Integer.parseInt(listaAcompanhaAbate.get(0).getTotal()));
                    pbQuantidadeAbate.setProgress(abatidos);

                }
            }

            @Override
            public void onFailure(Call<List<AcompanhaAbate>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                textoAbatidos.setText("OFFLINE");
                textoRestam.setText("OFFLINE");
                textoTotalAbate.setText("OFFLINE");

                textoTotalAbate.setTextColor(getApplicationContext().getResources().getColor(R.color.vermelho_texto_offline));
                textoAbatidos.setTextColor(getApplicationContext().getResources().getColor(R.color.vermelho_texto_offline));
                textoRestam.setTextColor(getApplicationContext().getResources().getColor(R.color.vermelho_texto_offline));
            }
        });
    }
}