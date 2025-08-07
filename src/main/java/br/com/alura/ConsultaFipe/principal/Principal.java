package br.com.alura.ConsultaFipe.principal;

import br.com.alura.ConsultaFipe.service.ConsumoApi;

import java.util.Scanner;

public class Principal {

    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private

    public void exibeMenu(){

        System.out.println("*******OPCOES******** \n carros \n motos \n caminhaos");
        var veiculo = leitura.nextLine();

        ConsumoApi consumoApi = new ConsumoApi();

        var json = consumo.obterDados(ENDERECO+veiculo+"/marcas");

        System.out.println(json);




    }
}
