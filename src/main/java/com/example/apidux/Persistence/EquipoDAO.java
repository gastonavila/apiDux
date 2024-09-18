package com.example.apidux.Persistence;

import com.example.apidux.Models.Equipo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class EquipoDAO {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public List<Equipo> buscarTodos(){
        return em.createQuery("from Equipo").getResultList();
    }

    public void crear(Equipo equipo){
        try {
            em.persist(equipo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el equipo", e);
        }
    }

    public void actualizar(Equipo equipo){
        if (em.find(Equipo.class, equipo.getId()) == null) {
            throw new IllegalArgumentException("Equipo no encontrado para actualizar");
        }
        em.merge(equipo);
    }

    public void eliminar(int id){
        Equipo equipo = buscarPorId(id);
        if (equipo != null) {
            em.remove(equipo);
        } else {
            throw new IllegalArgumentException("Equipo no encontrado para eliminar");
        }
    }

    public Equipo buscarPorId(int id){
        return em.find(Equipo.class, id);
    }

    public List<Equipo> buscarPorNombre(String nombre) {
        try {
            return em.createQuery("SELECT e FROM Equipo e WHERE e.nombre LIKE :nombre", Equipo.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
