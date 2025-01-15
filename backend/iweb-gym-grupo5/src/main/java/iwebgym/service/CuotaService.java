package iwebgym.service;

import iwebgym.model.Ingreso;
import iwebgym.model.Moroso;
import iwebgym.model.Socio;
import iwebgym.model.Suscripcion;
import iwebgym.repository.MorosoRepository;
import iwebgym.repository.SocioRepository;
import iwebgym.repository.SuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class CuotaService {

    @Autowired
    private UserService userService;
    @Autowired
    private SocioRepository socioRepository;
    @Autowired
    private SuscripcionRepository suscripcionRepository;
    @Autowired
    private MorosoRepository morosoRepository;

    public float get_precio_cuota(String nombre_cuota){
        List<Suscripcion> suscripcions = suscripcionRepository.findAll();
        for (Suscripcion suscripcion: suscripcions){
            if (suscripcion.getTipoSuscripcion().equals(nombre_cuota)){
                return suscripcion.getPrice();
            }
        }
        return 0;
    }
    public void descontar_saldo(Socio socio, float price_cuota){
        if(socio.getSaldo() < price_cuota){
            Moroso nuevoMoroso = new Moroso();
            nuevoMoroso.setId_socio(socio.getId());

            ZoneId madridZone = ZoneId.of("Europe/Madrid");
            LocalDate currentDate = LocalDate.now(madridZone);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
            String fechaMesAño = currentDate.format(formatter);

            nuevoMoroso.setMensualidad_no_pagada(fechaMesAño);

            Long nextId = 1L; // Valor por defecto si no hay reservas
            List<Moroso> allMorosos = morosoRepository.findAll();
            if (!allMorosos.isEmpty()) {
                Long maxId = allMorosos.stream()
                        .mapToLong(Moroso::getId)
                        .max()
                        .orElse(0L);
                nextId = maxId + 1;
            }
            nuevoMoroso.setId(nextId);

            morosoRepository.save(nuevoMoroso);
            socio.setActivo(false);
        }
        else{
            float saldo_usuario = socio.getSaldo();

            socio.setSaldo(saldo_usuario - price_cuota);
        }

        socioRepository.save(socio);

    }

    @Scheduled(cron = "0 0 0 1 * *")  // Esto ejecuta el método el 1er día de cada mes a las 00:00
    public void descontarCuota() {
        LocalDate fechaActual = LocalDate.now();
        if (fechaActual.getDayOfMonth() == 1) {
            List<Socio> lista_socios = socioRepository.findAll();

            for (Socio socio : lista_socios){
                if (socio.getActivo()){
                    String cuota_usario = socio.getTipoCuota();
                    float price_cuota = get_precio_cuota(cuota_usario);
                    descontar_saldo(socio, price_cuota);
                }
            }
        }
    }
}
