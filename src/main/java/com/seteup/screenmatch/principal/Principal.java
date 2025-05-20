package com.seteup.screenmatch.principal;

import com.seteup.screenmatch.model.DadosEpisodio;
import com.seteup.screenmatch.model.DadosSerie;
import com.seteup.screenmatch.model.DadosTemporadas;
import com.seteup.screenmatch.service.ConsumoApi;
import com.seteup.screenmatch.service.ConverterDados;

import java.util.*;
import java.util.stream.Collectors;

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
//o foreach abaixo resume tudo esse for

//        for(int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for(int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
        temporadas.forEach(System.out::println);

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\n Top 5 episodios");
        dadosEpisodios.stream()
                .filter( e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

    }
}
