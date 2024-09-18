package com.example.apidux.Controllers;

import com.example.apidux.Models.Equipo;
import com.example.apidux.Models.EquipoDTO;
import com.example.apidux.Models.ErrorResponse;
import com.example.apidux.Services.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EquipoController {

    @Autowired
    private EquipoService eqS;

    @Operation(summary = "Obtener todos los equipos", description = "Devuelve una lista de todos los equipos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de equipos obtenida correctamente")
    @GetMapping("/equipos")
    public ResponseEntity<List<Equipo>> findAll() {
        List<Equipo> equipos = eqS.findAllService();
        return ResponseEntity.ok(equipos);
    }

    @Operation(summary = "Guardar un nuevo equipo", description = "Crea un nuevo equipo en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Equipo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/equipos")
    public ResponseEntity<?> save(@RequestBody EquipoDTO equipoDTO) {
        if (equipoDTO == null || equipoDTO.getNombre() == null || equipoDTO.getNombre().isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("La solicitud es inválida", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Equipo equipo = new Equipo();
        equipo.setNombre(equipoDTO.getNombre());
        equipo.setLiga(equipoDTO.getLiga());
        equipo.setPais(equipoDTO.getPais());

        eqS.saveService(equipo);

        EquipoResponseDTO responseDTO = new EquipoResponseDTO();
        responseDTO.setId(equipo.getId());
        responseDTO.setNombre(equipo.getNombre());
        responseDTO.setLiga(equipo.getLiga());
        responseDTO.setPais(equipo.getPais());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @Operation(summary = "Actualizar un equipo", description = "Actualiza un equipo existente en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipo actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @PutMapping("/equipos")
    public ResponseEntity<?> update(@RequestBody Equipo equipo) {
        if (equipo == null || equipo.getId() <= 0 || equipo.getNombre() == null || equipo.getNombre().isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("La solicitud es inválida", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if (eqS.findByIdService(equipo.getId()) == null) {
            ErrorResponse errorResponse = new ErrorResponse("Equipo no encontrado", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        eqS.updateService(equipo);
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "Obtener equipo por ID", description = "Devuelve un equipo basado en el ID proporcionado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @GetMapping("/equipos/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Equipo equipo = eqS.findByIdService(id);
        if (equipo == null) {
            ErrorResponse errorResponse = new ErrorResponse("Equipo no encontrado", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(equipo);
    }

    @Operation(summary = "Buscar equipo por nombre", description = "Devuelve un equipo basado en el nombre proporcionado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @GetMapping("/equipos/buscar")
    public ResponseEntity<?> findByNombre(@RequestParam("nombre") String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("La solicitud es inválida", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }
        List<Equipo> equipos = eqS.findByNombreService(valor);
        if (equipos.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Equipo no encontrado", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(equipos);
    }


    @Operation(summary = "Eliminar un equipo", description = "Elimina un equipo basado en el ID proporcionado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Equipo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (eqS.findByIdService(id) == null) {
            ErrorResponse errorResponse = new ErrorResponse("Equipo no encontrado", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        eqS.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    static class EquipoResponseDTO {

        private int id;
        private String nombre;
        private String liga;
        private String pais;

        // Getters y Setters

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getLiga() {
            return liga;
        }

        public void setLiga(String liga) {
            this.liga = liga;
        }

        public String getPais() {
            return pais;
        }

        public void setPais(String pais) {
            this.pais = pais;
        }
    }

}
