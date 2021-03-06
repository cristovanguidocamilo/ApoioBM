package com.cristovancamilo.apoiobm.api;

import com.cristovancamilo.apoiobm.model.AbatesPecuarista;
import com.cristovancamilo.apoiobm.model.AcompanhaAbate;
import com.cristovancamilo.apoiobm.model.Camaras;
import com.cristovancamilo.apoiobm.model.EscalaAbate;
import com.cristovancamilo.apoiobm.model.EstoqueBloqueado;
import com.cristovancamilo.apoiobm.model.QuantidadesAbate;
import com.cristovancamilo.apoiobm.model.QuantidadesLote;
import com.cristovancamilo.apoiobm.model.ValidaUsuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApoioBMService {

    @GET("/datasnap/rest/TSM_BM/EstoqueBloqueado")
    Call<List<EstoqueBloqueado>> recuperarEstoqueBloqueado();

    @GET("/datasnap/rest/TSM_BM/Camaras")
    Call<List<Camaras>> recuperarCamaras();

    @GET("/datasnap/rest/TSM_BM/Acompanha")
    Call<List<AcompanhaAbate>> recuperarAcompanhaAbate();

    @GET("/datasnap/rest/TSM_BM/EscalaAbate")
    Call<List<EscalaAbate>> recuperarEscalaAbate();

    @GET("/datasnap/rest/TSM_BM/QuantidadesLote/{num_lote}")
    Call<List<QuantidadesLote>> recuperarQuantidadesLote(@Path("num_lote") String num_lote);

    @GET("/datasnap/rest/TSM_BM/QuantidadesAbate")
    Call<List<QuantidadesAbate>> recuperarQuantidadesAbate();

    @GET("/datasnap/rest/TSM_BM/ValidaUsuario/{usuario}/{senha}")
    Call<List<ValidaUsuario>> validarUsuario(@Path("usuario") String usuario, @Path("senha") String senha);

    @GET("/datasnap/rest/TSM_BM/CadastraUsuario/{usuario}/{senha}/{cpf_cnpj}")
    Call<List<ValidaUsuario>> cadasatraUsuario(@Path("usuario") String usuario, @Path("senha") String senha, @Path("cpf_cnpj") String cpf_cnpj);

    @GET("/datasnap/rest/TSM_BM/AbatesPecuarista/{cgc}")
    Call<List<AbatesPecuarista>> recuperarAbatesPecuarista(@Path("cgc") String cgc);

}
