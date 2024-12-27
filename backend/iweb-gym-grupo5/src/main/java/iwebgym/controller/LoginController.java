package iwebgym.controller;

import iwebgym.authentication.ManagerUserSession;
import iwebgym.dto.LoginData;
import iwebgym.dto.UserData;
import iwebgym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@RestController  // Cambiar @Controller por @RestController
@CrossOrigin(origins = "http://localhost:5173") // Asumiendo que Vue corre en el puerto 5173
public class LoginController {

    @Autowired
    UserService usuarioService;

    @Autowired
    ManagerUserSession managerUserSession;

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginData", new LoginData());
        return "hola";
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> loginSubmit(@RequestBody LoginData loginData) {
        Map<String, Object> response = new HashMap<>();

        UserService.LoginStatus loginStatus = usuarioService.login(loginData.getEmail(), loginData.getPassword());

        if (loginStatus == UserService.LoginStatus.USER_NOT_FOUND) {
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        if (loginStatus == UserService.LoginStatus.ERROR_PASSWORD) {
            response.put("error", "Contraseña incorrecta");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.put("status", "success");
        response.put("userType", loginStatus.toString());
        return ResponseEntity.ok(response);
    }
}


