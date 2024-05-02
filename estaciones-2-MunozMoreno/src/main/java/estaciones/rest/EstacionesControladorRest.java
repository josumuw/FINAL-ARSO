package estaciones.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import estaciones.dto.AltaBicicletaDTO;
import estaciones.dto.AltaEstacionDTO;
import estaciones.dto.BajaBicicletaDTO;
import estaciones.dto.BicicletaDTO;
import estaciones.dto.EstacionDTO;
import estaciones.dto.EstacionInfoDTO;
import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import estaciones.servicio.EstacionInfo;
import estaciones.servicio.IServicioEstaciones;

@RestController
@RequestMapping("/estaciones")
public class EstacionesControladorRest {

	private IServicioEstaciones servicio;

	@Autowired
	public EstacionesControladorRest(IServicioEstaciones servicio) {
		this.servicio = servicio;
	}

	@Autowired
	private PagedResourcesAssembler<EstacionDTO> pagedResourcesAssembler;

	// ### ADMIN ###
	@PostMapping
	public ResponseEntity<Void> altaEstacion(@RequestBody AltaEstacionDTO altaEstacion) throws Exception {
		String idEstacion = this.servicio.altaEstacion(altaEstacion.getNombre(), altaEstacion.getNumPuestos(),
				altaEstacion.getDirPostal(), altaEstacion.getLatitud(), altaEstacion.getLongitud());
		// Construye la URL completa del nuevo recurso
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEstacion}").buildAndExpand(idEstacion)
				.toUri();
		return ResponseEntity.created(nuevaURL).build();
	}

	@PostMapping("/{idEstacion}/bicicletas")
	public ResponseEntity<Void> altaBicicleta(@PathVariable String idEstacion,
			@RequestBody AltaBicicletaDTO altaBicicleta) throws Exception {
		String idBicicleta = this.servicio.altaBicicleta(altaBicicleta.getModelo(), idEstacion);
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idBicicleta}")
				.buildAndExpand(idBicicleta).toUri();
		return ResponseEntity.created(nuevaURL).build();
	}
	
	// Actualizar bicicleta
	//@PatchMapping("/bicicletas/{idBicicleta}/")
	
	@PostMapping("/bicicletas/{idBicicleta}/baja")
	public ResponseEntity<Void> darBajaBicicleta(@PathVariable String idBicicleta,
			@RequestBody BajaBicicletaDTO bajaBicicleta) throws Exception {
		this.servicio.darBajaBicicleta(idBicicleta, bajaBicicleta.getMotivoBaja());
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(idBicicleta).toUri();
		return ResponseEntity.created(nuevaURL).build();
	}

	@GetMapping("/{idEstacion}/bicicletas/all")
	public Page<EntityModel<BicicletaDTO>> getAllBicicletaEstacionPaginado(@PathVariable String idEstacion,
			@RequestParam int page, @RequestParam int size) throws Exception {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("modelo").ascending());
		Page<Bicicleta> bicicletas = this.servicio.getAllBicicletasEstacionPaginado(idEstacion, paginacion);
		Page<EntityModel<BicicletaDTO>> bicicletasDTO = bicicletas.map(bicicleta -> {
			BicicletaDTO bicicletaDTO = new BicicletaDTO(bicicleta.getModelo(), bicicleta.getEstado());
			EntityModel<BicicletaDTO> model = EntityModel.of(bicicletaDTO);

			// Construir la URL del selfref utilizando la URL base de la solicitud y el ID
			String selfUrl = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idBicicleta}")
					.buildAndExpand(bicicleta.getId()).toUriString();

			model.add(Link.of(selfUrl, "self"));

			// Construir la URL para dar de baja la bicicleta
			String bajaUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/estaciones/bicicletas/{idBicicleta}/baja").queryParam("motivoBaja", "Baja_directa")
					.buildAndExpand(bicicleta.getId()).toUriString();

			model.add(Link.of(bajaUrl, "darBaja"));

			return model;

		});
		return bicicletasDTO;
	}

	// ### CLIENTE ###
	@GetMapping
	public Page<EntityModel<EstacionDTO>> getAllEstacionesPaginado(@RequestParam int page, @RequestParam int size)
			throws Exception {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("nombre").ascending());
		Page<Estacion> estaciones = this.servicio.getAllEstacionesPaginado(paginacion);

		Page<EntityModel<EstacionDTO>> estacionesDTO = estaciones.map(estacion -> {
			EstacionDTO estacionDTO = new EstacionDTO(estacion.getNombre(), estacion.getNumPuestos(),
					estacion.getDirPostal());
			EntityModel<EstacionDTO> model = EntityModel.of(estacionDTO);

			// Construir la URL del selfref utilizando la URL base de la solicitud y el ID
			// de la estación
			String selfUrl = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEstacion}")
					.buildAndExpand(estacion.getId()).toUriString();
			model.add(Link.of(selfUrl, "self"));

			String bajaUrl = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEstacion}")
					.buildAndExpand(estacion.getId()).toUriString();
			model.add(Link.of(selfUrl, "self"));

			return model;
		});
		return estacionesDTO;
	}

	@GetMapping("/{idEstacion}")
	public EstacionInfoDTO getEstacionInfo(@PathVariable String idEstacion) throws Exception {
		EstacionInfo estacionInfo = this.servicio.getEstacionInfo(idEstacion);
		EstacionInfoDTO estacionInfoDTO = new EstacionInfoDTO(estacionInfo.getNombre(), estacionInfo.getNumPuestos(), estacionInfo.getDirPostal());

		// .from()
		return estacionInfoDTO;
	}

	@GetMapping("/{idEstacion}/bicicletas")
	public Page<EntityModel<BicicletaDTO>> getBicicletaEstacion(@PathVariable String idEstacion, @RequestParam int page,
			@RequestParam int size) throws Exception {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("nombre").ascending());
		Page<Bicicleta> bicicletas = this.servicio.getBicicletasEstacionPaginado(idEstacion, paginacion);

		Page<EntityModel<BicicletaDTO>> bicicletasDTO = bicicletas.map(bicicleta -> {
			BicicletaDTO bicicletaDTO = new BicicletaDTO(bicicleta.getModelo(), bicicleta.getEstado());
			EntityModel<BicicletaDTO> model = EntityModel.of(bicicletaDTO);

			// Construir la URL del selfref utilizando la URL base de la solicitud y el ID
			String selfUrl = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idBicicleta}")
					.buildAndExpand(bicicleta.getId()).toUriString();

			model.add(Link.of(selfUrl, "self"));
			return model;
		});
		return bicicletasDTO;

	}

	@PostMapping("/{idEstacion}/bicicletas/{idBicicleta}")
	public ResponseEntity<Void> estacionarBicicleta(@PathVariable String idEstacion, @PathVariable String idBicicleta)
			throws Exception {
		servicio.estacionarBicicleta(idEstacion, idBicicleta);
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(idEstacion, idBicicleta).toUri();
		return ResponseEntity.created(nuevaURL).build();
	}
}
