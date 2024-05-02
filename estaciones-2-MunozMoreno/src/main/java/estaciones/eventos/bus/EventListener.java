package estaciones.eventos.bus;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import estaciones.eventos.config.RabbitMQConfig;
import estaciones.repositorio.EntidadNoEncontrada;
import estaciones.servicio.ServicioEstaciones;


@Component
public class EventListener {

    private final ServicioEstaciones servicioEstaciones;
    // private final ObjectMapper objectMapper;

    @Autowired
    public EventListener(ServicioEstaciones servicioEstaciones) {
	this.servicioEstaciones = servicioEstaciones;
    }

    @RabbitListener(queues = "citybike-estaciones")
    public void handleEvent(Message mensaje) throws EntidadNoEncontrada, JsonMappingException, JsonProcessingException {

	String routingKey = mensaje.getMessageProperties().getReceivedRoutingKey();

	// Convertir el cuerpo del mensaje a un objeto AlquilerDTO
	String jsonEvento = new String(mensaje.getBody());
	ObjectMapper objectMapper = new ObjectMapper();

	switch (routingKey) {
	case RabbitMQConfig.ALQUILER_ACTIVADO_ROUTING_KEY:
	    EventoAlquilar evento1 = objectMapper.readValue(jsonEvento, EventoAlquilar.class);
	    System.out.println("EVENTO-ALQUILER LEIDO");
	    System.out.println(evento1);
	    servicioEstaciones.gestionarEventoAlquiler(evento1);
	    break;
	case RabbitMQConfig.ALQUILER_CONCLUIDO_ROUTING_KEY:
	    EventoAlquilar evento2 = objectMapper.readValue(jsonEvento, EventoAlquilarFin.class);
	    System.out.println("EVENTO-ALQUILER-FIN LEIDO");
	    System.out.println(evento2);
	    servicioEstaciones.gestionarEventoAlquiler(evento2);
	    break;
	default:
	    System.out.println("No se pudo manejar el evento con clave de enrutamiento: " + routingKey);
	    break;
	}
    }
}
