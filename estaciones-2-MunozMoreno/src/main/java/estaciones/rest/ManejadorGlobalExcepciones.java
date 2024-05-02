package estaciones.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import estaciones.repositorio.EntidadNoEncontrada;
import estaciones.servicio.RequisitosException;

@ControllerAdvice
public class ManejadorGlobalExcepciones extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), null, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(EntidadNoEncontrada.class)
	public ResponseEntity<Object> handleEntidadNoEncontrada(EntidadNoEncontrada ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), null, HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(RequisitosException.class)
	public ResponseEntity<Object> handleRequisitosException(RequisitosException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), null, HttpStatus.FORBIDDEN, request);
	}
}
