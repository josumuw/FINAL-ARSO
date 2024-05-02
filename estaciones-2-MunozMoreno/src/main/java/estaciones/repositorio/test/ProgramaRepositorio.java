package estaciones.repositorio.test;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import estaciones.EstacionesApp;
import estaciones.repositorio.RepositorioEstaciones;

public class ProgramaRepositorio {

	public static void main(String[] args) {

		ConfigurableApplicationContext contexto = SpringApplication.run(EstacionesApp.class, args);

		RepositorioEstaciones repositorio = contexto.getBean(RepositorioEstaciones.class);

		System.out.println(repositorio.count());
		contexto.close();
	}

}
