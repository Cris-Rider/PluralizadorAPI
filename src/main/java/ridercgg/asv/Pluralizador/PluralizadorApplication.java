package ridercgg.asv.Pluralizador;
import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PluralizadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PluralizadorApplication.class, args);
	}

	@PostMapping("/pluralizador")
	public RespuestaModificacion palabrasPlurales(@RequestBody List<String> palabras) {

		List<String> resultado = new ArrayList<>();
		List<Integer> contadores = new ArrayList<>(List.of(0,0,0,0));

		for(String palabra : palabras){
			palabra = palabra.toLowerCase();
			char ultimaLetra = Character.toLowerCase(palabra.charAt(palabra.length() - 1));
			switch (ultimaLetra) {
				case 'a','e','i','o','u':
					resultado.add(palabra + "s");
					contadores.set(0, contadores.getFirst() + 1);
					break;
				case 's', 'x':
					resultado.add(palabra);
					contadores.set(1, contadores.get(1) + 1);
					break;
				case 'z':
					resultado.add(palabra.substring(0, palabra.length() - 1) + "ces") ;
					contadores.set(2, contadores.get(2) + 1);
					break;
				default:
					resultado.add(palabra + "es") ;
					contadores.set(3, contadores.get(3) + 1);
			}
		}
		return new RespuestaModificacion(resultado, contadores);
	}

	private static class RespuestaModificacion {
		private List<String> palabrasModificadas;
		private List<Integer> contadores;

		public RespuestaModificacion(List<String> palabrasModificadas, List<Integer> contadores) {
			this.palabrasModificadas = palabrasModificadas;
			this.contadores = contadores;
		}

		public List<String> getPalabrasModificadas() {
			return palabrasModificadas;
		}

		public List<Integer> getContadores() {
			return contadores;
		}
	}
}



