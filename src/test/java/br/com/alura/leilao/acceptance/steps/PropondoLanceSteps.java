package br.com.alura.leilao.acceptance.steps;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PropondoLanceSteps {
    private Lance lance;
    private Leilao leilao;

    private ArrayList<Lance> listaLance;

    @Dado("um lance valido")
    public void dado_um_lance_valido() {
        Usuario usuario = new Usuario("fulano");
        lance = new Lance(usuario, BigDecimal.TEN);
        leilao = new Leilao("Tablet XPTO");
    }

    @Quando("propoe ao leilao")
    public void quando_propoe_o_lance() {
        leilao.propoe(lance);
    }

    @Entao("o lance eh aceito")
    public void entao_o_lance_eh_aceito() {
        Assert.assertEquals(1, leilao.getLances().size());
        Assert.assertEquals(BigDecimal.TEN,leilao.getLances().get(0).getValor());
    }

//    @Dado("varios lances validos")
//    public void varios_lances_validos() {
//        Usuario usuario1 = new Usuario("fulano");
//        lance10 = new Lance(usuario1, BigDecimal.TEN);
//        Usuario usuario2 = new Usuario("beltrano");
//        lance11 = new Lance(usuario2, new BigDecimal("11.0"));
//        leilao = new Leilao("TekPix");
//    }

    @Before
    public void setup(){
        this.listaLance = new ArrayList<>();
        this.leilao = new Leilao("Camera Tekpix");
    }

    @Dado("um lance de {double} reais do usuario {string}")
    public void um_lance_de_reais_do_usuario_fulano(Double valor, String nomeUsuario){
        Lance lance = new Lance(
                new Usuario(nomeUsuario),
                new BigDecimal(valor)
        );
        listaLance.add(lance);
    }

    @Quando("propoe varios lances ao leilao")
    public void propoe_varios_lances_ao_leilao() {
        listaLance.forEach(lance ->
            this.leilao.propoe(lance)
        );
    }

    @Entao("os lances sao aceitos")
    public void os_lances_sao_aceitos() {

        Assert.assertEquals(
                this.listaLance.size(),
                leilao.getLances().size()
        );

        Assert.assertEquals(
                this.listaLance.get(0).getValor(),
                this.leilao.getLances().get(0).getValor()
        );

        Assert.assertEquals(
                this.listaLance.get(1).getValor(),
                leilao.getLances().get(1).getValor()
        );
    }

    @Dado("um lance de {double} reais e do usuario {string}")
    public void um_lance_de_reais(Double valor, String nomeUsuario) {
        this.lance = new Lance(
                new Usuario(nomeUsuario),
                new BigDecimal(valor)
        );
    }

    @Entao("o lance nao eh aceito")
    public void o_lance_nao_eh_aceito() {
        Assert.assertEquals(
        0,
            leilao.getLances().size()
        );
    }

    @Dado("dois lances")
    public void dois_lances(DataTable dataTable) {
        List<Map<String,String>> listaInformacoes = dataTable.asMaps();
        for (Map<String, String> mapa : listaInformacoes){
            String nomeUsuario = mapa.get("nomeUsuario");
            Double valor = Double.valueOf(mapa.get("valor"));

            this.listaLance.add(
                    new Lance(
                            new Usuario(nomeUsuario),
                            new BigDecimal(valor)
                    )
            );
        }
    }

    @Entao("o segundo lance nao eh aceitos")
    public void o_segundo_lance_nao_eh_aceitos() {
        Assert.assertEquals(1, leilao.getLances().size());
        Assert.assertEquals(this.listaLance.get(0).getValor(),leilao.getLances().get(0).getValor());
    }
}
