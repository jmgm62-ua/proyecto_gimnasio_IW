package iwebgym.service;

import iwebgym.authentication.ManagerUserSession;
import iwebgym.dto.*;
import iwebgym.model.*;
import iwebgym.repository.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {


    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private IngresoRepository ingresoRepository;
    @Autowired
    private IngresoPendienteRepository ingresoPendienteRepository;

    public enum LoginStatus {LOGIN_OK_SOCIO,LOGIN_OK_MONITOR,LOGIN_OK_WEBMASTER, USER_NOT_FOUND, ERROR_PASSWORD, SOCIO_NO_ACTIVO}

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private WebMasterRepository webMasterRepository;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private ManagerUserSession managerUserSession;

    @Autowired
    private ModelMapper modelMapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public LoginStatus login(String eMail, String password) {

        System.out.println("Email recibido en loginSubmit: " + eMail);
        System.out.println("contraseña recibido en loginSubmit: " + password);
        if (eMail == null || eMail.trim().isEmpty()) {
            return LoginStatus.USER_NOT_FOUND;
        }
        Optional<Socio> socio = socioRepository.findByEmail(eMail);
        if (socio.isPresent()) {
            Socio foundSocio = socio.get();

            // Verificar si el socio está activo
            if (!foundSocio.getActivo()) {
                return LoginStatus.SOCIO_NO_ACTIVO;
            }

            // Verificar contraseña
            if (!foundSocio.getPassword().equals(password)) {
                return LoginStatus.ERROR_PASSWORD;
            } else {
                managerUserSession.logearUsuario(eMail);
                return LoginStatus.LOGIN_OK_SOCIO;
            }
        }
        Optional<WebMaster> webMaster = webMasterRepository.findByEmail(eMail);
        if (webMaster.isPresent()) {
            if (!webMaster.get().getPassword().equals(password)) {
                return LoginStatus.ERROR_PASSWORD;
            } else {
                managerUserSession.logearUsuario(eMail);
                return LoginStatus.LOGIN_OK_WEBMASTER;
            }
        }
        Optional<Monitor> monitor = monitorRepository.findByEmail(eMail);
        if (monitor.isPresent()) {
            if (!monitor.get().getPassword().equals(password)) {
                return LoginStatus.ERROR_PASSWORD;
            } else {
                managerUserSession.logearUsuario(eMail);
                return LoginStatus.LOGIN_OK_MONITOR;
            }
        }

        return LoginStatus.USER_NOT_FOUND;
    }

    @Transactional(readOnly = true)
    public UserData findByEmail(String email) {
        User usuario = userRepository.findByEmail(email).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, UserData.class);
        }
    }


    @Transactional(readOnly = true)
    public SocioData findSocioByEmail(String email) {
        Socio usuario = socioRepository.findByEmail(email).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, SocioData.class);
        }
    }

    @Transactional(readOnly = true)
    public MonitorData findMonitorByEmail(String email) {
        Monitor usuario = monitorRepository.findByEmail(email).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, MonitorData.class);
        }
    }

    @Transactional(readOnly = true)
    public WebMasterData findWebMasterByEmail(String email) {
        WebMaster usuario = webMasterRepository.findByEmail(email).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, WebMasterData.class);
        }
    }

    @Transactional
    public boolean actualizarTipoCuota(String email, String nuevaCuota) {
        Optional<Socio> socioOpt = socioRepository.findByEmail(email);
        if (socioOpt.isPresent()) {
            Socio socio = socioOpt.get();
            socio.setTipoCuota(nuevaCuota);
            socioRepository.save(socio);
            return true;
        }
        return false;
    }
    @Transactional
    public Socio registrarNuevoSocio(SocioRegistroRequestData request) {
        // Verificar si el email ya existe
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        // Obtener el ID más alto actual
        Long nextId = 1L; // Valor por defecto si no hay socios
        List<Socio> allSocios = socioRepository.findAll();
        if (!allSocios.isEmpty()) {
            Long maxId = allSocios.stream()
                    .mapToLong(Socio::getId)
                    .max()
                    .orElse(0L);
            nextId = maxId + 1;
        }

        // Crear nuevo socio
        Socio socio = new Socio(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getFechaNacimiento(),
                request.getTelefono(),
                request.getDireccion(),
                false, // activo = false hasta que el webmaster lo apruebe
                request.getTipoCuota(),
                null,
                null, // fecha baja = null
                0.0f  // saldo inicial = 0
        );

        socio.setId(nextId);

        return socioRepository.save(socio);
    }


    @Transactional
    public boolean cargar_saldo(String referencia, float nuevo_saldo, String email) throws Exception {
        List<IngresoPendiente> ingresoPendientes = ingresoPendienteRepository.findAll();
        boolean pendiete_de_pago = false;

        List<Ingreso> lista_ingresos = ingresoRepository.findAll();

        for (Ingreso ingreso_a_revisar :  lista_ingresos){
            if (ingreso_a_revisar.getReferencia().equals(referencia)){
                throw new Exception("Este ingreso ya ha sido realizado");
            }
        }

        Socio socio_a_cargar_saldo = new Socio();

        try{
            socio_a_cargar_saldo = socioRepository.findByEmail(email).get();
        } catch (Exception e) {
            throw new Exception("Usuario no encontrado");
        }

        for (IngresoPendiente ip : ingresoPendientes){
            if(ip.getReferencia().equals(referencia)){
                pendiete_de_pago = true;
                ingresoPendienteRepository.delete(ip);
            }
        }

        if (pendiete_de_pago == false){
            throw new Exception("Este ingreso de saldo no se puede hacer");
        }

        Ingreso nuevo_ingreso = new Ingreso();

        ZonedDateTime madridTime = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
        Date fechaActual = Date.from(madridTime.toInstant());
        nuevo_ingreso.setFecha(fechaActual);
        nuevo_ingreso.setCantidad(new BigDecimal(nuevo_saldo));
        nuevo_ingreso.setReferencia(referencia);

        ingresoRepository.save(nuevo_ingreso);
        float saldo_actual = socio_a_cargar_saldo.getSaldo();
        socio_a_cargar_saldo.setSaldo(saldo_actual + nuevo_saldo);

        socioRepository.save(socio_a_cargar_saldo);

        return true;
    }


    @Transactional
    public boolean registrarNuevaReferencia(String referencia) throws Exception {

        List<IngresoPendiente> ingresosPendientes_list = ingresoPendienteRepository.findAll();
        for (IngresoPendiente ip : ingresosPendientes_list){
            if(ip.getReferencia().equals(referencia)){
                throw new Exception("Esta referencia ya existe");
            }
        }

        IngresoPendiente ingresoPendiente = new IngresoPendiente();
        ingresoPendiente.setReferencia(referencia);

        ingresoPendienteRepository.save(ingresoPendiente);

        return true;
    }


    public List<SocioData> getPendientesDeActivacion() {
        return socioRepository.findInactiveSociosWithoutFechaAlta()
                .stream()
                .map(socio -> new SocioData(
                        socio.getId(),
                        socio.getName(),
                        socio.getEmail(),
                        socio.getDireccion(),
                        socio.getTelefono(),
                        socio.getActivo()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void activarSocio(Long id) {
        System.out.println("ID recibido: " + id);
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));
        socio.setActivo(true);
        socio.setFechaAlta(LocalDate.now().toString());
        socio.setFechaBaja(null);
        socioRepository.save(socio);
    }

    @Transactional
    public void desactivarSocio(Long id) {
        System.out.println("ID recibido: " + id);
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));
        socio.setActivo(false);
        socio.setFechaBaja(LocalDate.now().toString());
        socio.setFechaAlta(null);
        socioRepository.save(socio);
    }

    @Transactional
    public void eliminarSocio(Long id) {
        if (!socioRepository.existsById(id)) {
            throw new RuntimeException("Socio no encontrado");
        }
        socioRepository.deleteById(id);
    }


    public List<SocioData> getAllSocios() {
        return socioRepository.findAllbutNotSolicitudes()
                .stream()
                .map(socio -> new SocioData(
                        socio.getId(),
                        socio.getName(),
                        socio.getEmail(),
                        socio.getTipoCuota(),
                        socio.getFechaAlta(),
                        socio.getFechaBaja(),
                        socio.getSaldo(),
                        socio.getInscripcion().getTipoSuscripcion(),
                        socio.getDireccion(),
                        socio.getFechaNacimiento(),
                        socio.getTelefono(),
                        socio.getActivo()))
                .collect(Collectors.toList());
    }
}
