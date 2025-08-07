package br.com.alura.ConsultaFipe.principal;

import br.com.alura.ConsultaFipe.model.Dados;
import br.com.alura.ConsultaFipe.model.Modelos;
import br.com.alura.ConsultaFipe.service.ConsumoApi;
import br.com.alura.ConsultaFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final String URL_BASE  = "https://parallelum.com.br/fipe/api/v1/";
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){

        var menu = """
                 *******OPCOES******** 
                 carros 
                 motos 
                 caminhaos
                 """;

        System.out.println(menu);
        var opcao = leitura.nextLine();

        String endereco;

        if (opcao.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);

        System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);

//        Dados dados = conversor.obterLista(json, Dados.class);

        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite um codigo de marca");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";

        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\n Modelso dessa Marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);







    }
}
