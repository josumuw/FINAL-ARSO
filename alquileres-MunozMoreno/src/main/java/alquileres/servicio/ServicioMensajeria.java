package alquileres.servicio;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import eventos.rabbitmq.EventoAlquilar;
import eventos.rabbitmq.EventoAlquilarFin;
import servicio.FactoriaServicios;

public class ServicioMensajeria implements IServicioMensajeria {

    // IServicioAlquileres
    //IServicioAlquileres servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);

    private static final String EXCHANGE_NAME = "citybike";
    private static final String QUEUE_ALQUILERES_NAME = "citybike-alquileres";
    private static final String QUEUE_ESTACIONES_NAME = "citybike-estaciones";
    private static final String[] ROUTING_KEYS_ALQUILERES = { 
	    "citybike.alquileres.bicicleta-alquilada",
	    "citybike.alquileres.bicicleta-alquiler-concluido" };
    private static final String[] ROUTING_KEYS_ESTACIONES = { "citybike.estaciones.bicicleta-desactivada" };

    private String rabbitUrl = "amqps://atjwdszd:WKrnhGlOIyVKNFF7ohkUDyXDjLwBrw5M@roedeer.rmq.cloudamqp.com/atjwdszd";

    private Connection connection;
    private Channel channel;

    public ServicioMensajeria() throws ServicioMensajeriaException {
	try {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setUri(rabbitUrl);
	    this.connection = factory.newConnection();
	    this.channel = connection.createChannel();
	    
	    // Declara el exchange
	    channel.exchangeDeclare(EXCHANGE_NAME, "topic");

	    // Declara la cola citybike-alquileres
	    channel.queueDeclare(QUEUE_ALQUILERES_NAME, false, false, false, null);

	    // Asocia la cola citybike-alquileres con el exchange y las routing keys
	    // especificadas
	    for (String routingKey : ROUTING_KEYS_ESTACIONES) {
		channel.queueBind(QUEUE_ALQUILERES_NAME, EXCHANGE_NAME, routingKey);
	    }

	    // Declara la cola citybike-estaciones
	    channel.queueDeclare(QUEUE_ESTACIONES_NAME, false, false, false, null);

	    // Asocia la cola citybike-estaciones con el exchange y las routing keys
	    // especificadas
	    for (String routingKey : ROUTING_KEYS_ALQUILERES) {
		channel.queueBind(QUEUE_ESTACIONES_NAME, EXCHANGE_NAME, routingKey);
	    }
	    escuharCola("citybike-alquileres");
	} catch (Exception e) {
	    throw new ServicioMensajeriaException("Error en SerivicioMensajeria: construcción del servicio.");
	}

    }

    @Override
    public void publicar(Object obj) throws ServicioMensajeriaException {
	try {
	    // Convertir el objeto a JSON
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")); // Establecer un formato de fecha
	    String jsonMessage = mapper.writeValueAsString(obj);

	    
	    String routingKey = null;
	    // Elegir clave según la clase del evento.
	    if (obj instanceof EventoAlquilarFin)
		routingKey = ROUTING_KEYS_ALQUILERES[1];
	    else if (obj instanceof EventoAlquilar)
		routingKey = ROUTING_KEYS_ALQUILERES[0];

	    // Publicar mensaje.
	    channel.basicPublish(EXCHANGE_NAME, routingKey, null,
		    jsonMessage.getBytes());
	    
	} catch (Exception e) {
	    throw new ServicioMensajeriaException("Error en ServicioMensajeria al publiar un mensaje: " + e);
	}
    }

    private void escuharCola(String cola) throws ServicioMensajeriaException {
	try {
	    Channel canal = connection.createChannel();
	    boolean autoAck = true;
	    canal.basicConsume("citybike-alquileres", autoAck, "alquileres-consumidor", new DefaultConsumer(canal) {
		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
			byte[] body) throws IOException {
		    String routingKey = envelope.getRoutingKey();
		    switch (routingKey) {
		    case "citybike.estaciones.bicicleta-desactivada":
			String contenido = new String(body);
			String idBicicleta = contenido;
			// TODO: GESTIONAR EVENTO.
			//servicioAlquileres.eliminarReservas(idBicicleta);
			System.out.println(contenido);
			break;
		    default:
			System.out.println("Evento con routingKey " + routingKey + "no esperado.");
			break;
		    }
		}
	    });
	} catch (Exception e) {
	    throw new ServicioMensajeriaException("Error en ServicioMensajeria al escuchar una cola: " + e);
	}

    }
}
