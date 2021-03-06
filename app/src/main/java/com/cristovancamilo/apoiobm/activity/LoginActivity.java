package com.cristovancamilo.apoiobm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cristovancamilo.apoiobm.R;
import com.cristovancamilo.apoiobm.api.ApoioBMService;
import com.cristovancamilo.apoiobm.helper.Base64Custom;
import com.cristovancamilo.apoiobm.helper.PreferenciasSistema;
import com.cristovancamilo.apoiobm.helper.RetrofitConfig;
import com.cristovancamilo.apoiobm.model.ValidaUsuario;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private List<ValidaUsuario> listaValidaUsuario = new ArrayList<>();
    private TextInputEditText textUsuario, textSenha;
    private PreferenciasSistema preferenciasSistema;

    private final int MINUTOS_LOGIN_EXPIRACAO = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUsuario = findViewById(R.id.textUsuario);
        textSenha = findViewById(R.id.textSenha);
        preferenciasSistema = new PreferenciasSistema(getApplicationContext());

        if (diferencaDatas(System.currentTimeMillis(), Long.parseLong(preferenciasSistema.recuperarHorarioLogin()))/1000/60 <= MINUTOS_LOGIN_EXPIRACAO) {
            abrirSplashScreen(preferenciasSistema.recuperarCGC(), preferenciasSistema.recuperarTipo());
        }

        //Configurando Retrofit
        retrofit = RetrofitConfig.getRetrofit();

    }

    public void abrirMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirSplashScreen(String cgc, String tipo) {
        Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
        intent.putExtra("cgc", cgc);
        intent.putExtra("tipo", tipo);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

    public void validarUsuario(View view) {

        ApoioBMService apoioBMService = retrofit.create(ApoioBMService.class);
        Call<List<ValidaUsuario>> call = apoioBMService.validarUsuario(Base64Custom.codificarBase64(textUsuario.getText().toString()), Base64Custom.codificarBase64(textSenha.getText().toString()));

        call.enqueue(new Callback<List<ValidaUsuario>>() {
            @Override
            public void onResponse(Call<List<ValidaUsuario>> call, Response<List<ValidaUsuario>> response) {
                String mensagem = "";
                if(response.isSuccessful()) {
                    listaValidaUsuario.clear();
                    listaValidaUsuario = response.body();
                    if(listaValidaUsuario.get(0).getResult().equals("RET000")) {
                        abrirSplashScreen(listaValidaUsuario.get(0).getCgc(), listaValidaUsuario.get(0).getTipo());
                        //preferenciasSistema.salvarHorarioLogin(DateFormat.getInstance().format(new Date(System.currentTimeMillis())));
                        preferenciasSistema.salvarHorarioLogin(String.valueOf(System.currentTimeMillis()), listaValidaUsuario.get(0).getCgc(), listaValidaUsuario.get(0).getTipo());
                    } else if(listaValidaUsuario.get(0).getResult().equals("RET001")) {
                        mensagem = "Usuário não encontrado!";
                    }else if(listaValidaUsuario.get(0).getResult().equals("RET002")) {
                        mensagem = "Senha Incorreta!";
                    }
                    if(!mensagem.equals("")) {
                        Toast.makeText(LoginActivity.this, mensagem, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ValidaUsuario>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void byPass(View view) {
        abrirMainActivity();
    }

    public long diferencaDatas(Long data1, Long data2) {
        return data1 - data2;
    }
}