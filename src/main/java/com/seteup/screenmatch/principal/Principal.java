package com.seteup.screenmatch.principal;

import com.seteup.screenmatch.model.DadosSerie;
import com.seteup.screenmatch.model.DadosTemporadas;
import com.seteup.screenmatch.service.ConsumoApi;
import com.seteup.screenmatch.service.ConverterDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverterDados conversor = new ConverterDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados( ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporadas> temporadas = new ArrayList<>();
		for(int i = 1; i<=dados.totalTemporadas(); i++) {
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+"&season=" + i + API_KEY);
			DadosTemporadas dadosTemporada = conversor.obterDados(json, DadosTemporadas.class);
			temporadas.add(dadosTemporada);

		}
		temporadas.forEach(System.out::println);
    }
}
