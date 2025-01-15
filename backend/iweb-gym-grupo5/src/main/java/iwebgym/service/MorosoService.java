package iwebgym.service;

import iwebgym.dto.MorosoDTO;
import iwebgym.model.Moroso;
import iwebgym.model.Socio;
import iwebgym.repository.MorosoRepository;
import iwebgym.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MorosoService {

    @Autowired
    private MorosoRepository morosoRepository;

    @Autowired
    private SocioRepository socioRepository;

    /**
     * Obtiene todos los socios morosos
     * @return Lista de morosos
     */
    @Transactional(readOnly = true)
    public List<Moroso> getAllMorosos() {
        return morosoRepository.findAll();
    }

    /**
     * Verifica si un socio es moroso
     * @param idSocio ID del socio
     * @return true si el socio es moroso, false en caso contrario
     */
    @Transactional(readOnly = true)
    public boolean isSocioMoroso(Long idSocio) {
        List<Moroso> morosos = morosoRepository.findAll();
        return morosos.stream().anyMatch(m -> m.getId_socio().equals(idSocio));
    }

    @Transactional(readOnly = true)
    public List<MorosoDTO> getAllMorososWithDetails() {
        List<Moroso> morosos = morosoRepository.findAll();
        List<MorosoDTO> morosoDTOs = new ArrayList<>();

        for (Moroso moroso : morosos) {
            Optional<Socio> socioOpt = socioRepository.findById(moroso.getId_socio());
            if (socioOpt.isPresent()) {
                Socio socio = socioOpt.get();
                MorosoDTO dto = new MorosoDTO();
                dto.setId(moroso.getId());
                dto.setSocioId(socio.getId());
                dto.setName(socio.getName());
                dto.setEmail(socio.getEmail());
                dto.setDireccion(socio.getDireccion());
                dto.setTelefono(socio.getTelefono());
                dto.setFechaNacimiento(socio.getFechaNacimiento());
                dto.setMensualidadNoPagada(moroso.getMensualidad_no_pagada());
                dto.setActivo(socio.getActivo());
                morosoDTOs.add(dto);
            }
        }
        return morosoDTOs;
    }

    @Transactional
    public void activarSocioMoroso(Long socioId) {
        Optional<Socio> socioOpt = socioRepository.findById(socioId);
        if (socioOpt.isPresent()) {
            Long idMoroso = morosoRepository.obtainIdFromIdSocio(socioId);
            morosoRepository.deleteById(idMoroso);
            Socio socio = socioOpt.get();
            socio.setActivo(true);
            socio.setFechaAlta(LocalDate.now().toString());
            socioRepository.save(socio);
        } else {
            throw new RuntimeException("Socio no encontrado");
        }
    }

}