package estaciones.eventos.servicio;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estaciones.eventos.config.RabbitMQConfig;

@Service
public class ServicioMensajeria implements IServicioMensajeria {

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ServicioMensajeria(ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate) {
	this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicar(Object obj) {
	rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, obj);
    }
}