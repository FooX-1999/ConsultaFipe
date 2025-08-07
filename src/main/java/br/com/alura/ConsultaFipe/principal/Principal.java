package br.com.alura.ConsultaFipe.principal;

import br.com.alura.ConsultaFipe.model.Dados;
import br.com.alura.ConsultaFipe.model.Modelos;
import br.com.alura.ConsultaFipe.model.Veiculo;
import br.com.alura.ConsultaFipe.service.ConsumoApi;
import br.com.alura.ConsultaFipe.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

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

        System.out.println("\n Modelos dessa Marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("\nModelos filtrados");
        modelosFiltrados.forEach(System.out::println);


        System.out.println("Digite por favor o codigo do modelo para buscar os valores");
        var codigoModelo = leitura.nextLine();

        endereco = endereco +"/" + codigoModelo+ "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" +  anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
            
        }

        System.out.println("Todos os veiculos filtrados com avaliacao por anos: ");
        veiculos.forEach(System.out::println);







    }
}
