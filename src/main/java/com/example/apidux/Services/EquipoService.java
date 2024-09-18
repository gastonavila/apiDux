package com.example.apidux.Services;

import com.example.apidux.Models.Equipo;
import com.example.apidux.Persistence.EquipoDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {

    @Autowired
    private EquipoDAO equipoDAO = new EquipoDAO();

    @Transactional()
    public List<Equipo> findAllService() {
        return equipoDAO.buscarTodos();
    }

    @Transactional()
    public void saveService(Equipo equipo) {
        if (equipo == null || equipo.getNombre() == null || equipo.getNombre().isEmpty()) {
            throw new IllegalArgumentException("Datos del equipo inválidos");
        }
        Optional<Integer> maxId = equipoDAO.buscarTodos().stream()
                .map(Equipo::getId)
                .max(Comparator.naturalOrder());
        equipo.setId(maxId.get() + 1);
        equipoDAO.crear(equipo);
    }

    @Transactional()
    public Equipo findByIdService(int id) {
        return equipoDAO.buscarPorId(id);
    }

    @Transactional()
    public List<Equipo> findByNombreService(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre del equipo no proporcionado");
        }
        return equipoDAO.buscarPorNombre(valor);
    }

    @Transactional()
    public void updateService(Equipo equipo) {
        if (equipo == null || equipo.getId() <= 0 || equipo.getNombre() == null || equipo.getNombre().isEmpty()) {
            throw new IllegalArgumentException("Datos del equipo inválidos");
        }
        if (equipoDAO.buscarPorId(equipo.getId()) == null) {
            throw new IllegalArgumentException("Equipo no encontrado");
        }
        equipoDAO.actualizar(equipo);
    }

    @Transactional()
    public void deleteService(int id) {
        if (equipoDAO.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Equipo no encontrado");
        }
        equipoDAO.eliminar(id);
    }

    @Transactional
    public boolean existsByIdService(int id) {
        return equipoDAO.buscarPorId(id) != null;
    }
}
