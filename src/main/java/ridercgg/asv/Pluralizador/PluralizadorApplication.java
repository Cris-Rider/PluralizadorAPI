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

		//Se crean las listas que contendran los resultados de las transformaciones.

		List<String> resultado = new ArrayList<>();
		List<String> textoContadores = new ArrayList<>();
		List<Integer> contadores = new ArrayList<>(List.of(0,0,0,0));


		//Se recorre la lista de palabras con un ciclo forEach.
		//Luego cada una de estas palabras es transformada a minúsculas.
		//Y usando la condicional switch, dependiendo de la regla, se pasa la palabra a plural.
		//Por ultimo, se despliega el resultado en formato JSON.

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
		textoContadores.add("Contador regla 1: " + contadores.get(0));
		textoContadores.add("Contador regla 2: " + contadores.get(1));
		textoContadores.add("Contador regla 3: " + contadores.get(2));
		textoContadores.add("Contador regla 4: " + contadores.get(3));

		return new RespuestaModificacion(resultado, textoContadores);
	}

	//Clase pivote para recibir los resultados y desplegarlos adecuadamente.

	private static class RespuestaModificacion {
		private List<String> palabrasModificadas;
		private List<String> contadores;

		public RespuestaModificacion(List<String> palabrasModificadas, List<String> contadores) {
			this.palabrasModificadas = palabrasModificadas;
			this.contadores = contadores;
		}

		public List<String> getPalabrasModificadas() {
			return palabrasModificadas;
		}

		public List<String> getContadores() {
			return contadores;
		}
	}
}



