package com.seteup.screenmatch;

import com.seteup.screenmatch.model.DadosEpisodio;
import com.seteup.screenmatch.model.DadosSerie;
import com.seteup.screenmatch.model.DadosTemporadas;
import com.seteup.screenmatch.service.ConsumoApi;
import com.seteup.screenmatch.service.ConverterDados;
import com.seteup.screenmatch.service.IConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoApi consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=19b4b4f7");
		System.out.println(json);

		IConverteDados conversor = new ConverterDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
		json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=19b4b4f7");
		DadosEpisodio dadosEpsodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpsodio);

		List<DadosTemporadas> temporadas = new ArrayList<>();
		for(int i = 1; i<=dados.totalTemporadas(); i++) {
			json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=6585022c");
			DadosTemporadas dadosTemporada = conversor.obterDados(json, DadosTemporadas.class);
			temporadas.add(dadosTemporada);

		}
		temporadas.forEach(System.out::println);
	}
}
