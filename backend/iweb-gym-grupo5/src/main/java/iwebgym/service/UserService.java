package iwebgym.service;

import iwebgym.authentication.ManagerUserSession;
import iwebgym.dto.*;
import iwebgym.model.Monitor;
import iwebgym.model.Socio;
import iwebgym.model.User;
import iwebgym.model.WebMaster;
import iwebgym.repository.MonitorRepository;
import iwebgym.repository.SocioRepository;
import iwebgym.repository.UserRepository;
import iwebgym.repository.WebMasterRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    Logger logger = LoggerFactory.getLogger(UserService.class);

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
                LocalDate.now().toString(), // fecha alta = fecha actual
                null, // fecha baja = null
                0.0f  // saldo inicial = 0
        );

        return socioRepository.save(socio);
    }
}
