package com.seteup.screenmatch;

import com.seteup.screenmatch.model.DadosEpsodio;
import com.seteup.screenmatch.model.DadosSerie;
import com.seteup.screenmatch.service.ConsumoApi;
import com.seteup.screenmatch.service.ConverterDados;
import com.seteup.screenmatch.service.IConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		DadosEpsodio dadosEpsodio = conversor.obterDados(json, DadosEpsodio.class);
		System.out.println(dadosEpsodio);
	}
}
