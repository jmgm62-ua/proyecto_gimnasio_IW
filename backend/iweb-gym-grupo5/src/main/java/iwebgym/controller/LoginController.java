package iwebgym.controller;

import iwebgym.authentication.ManagerUserSession;
import iwebgym.dto.LoginData;
import iwebgym.dto.UserData;
import iwebgym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
@Controller

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

    @PostMapping("/login")
    public String loginSubmit(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
        UserService.LoginStatus loginStatus = usuarioService.login(email, password);

        if (loginStatus == UserService.LoginStatus.LOGIN_OK_SOCIO ||
                loginStatus == UserService.LoginStatus.LOGIN_OK_MONITOR ||
                loginStatus == UserService.LoginStatus.LOGIN_OK_WEBMASTER) {

            UserData usuario = usuarioService.findByEmail(email);
            if (usuario != null) {
                managerUserSession.logearUsuario(usuario.getId());
            } else {
                model.addAttribute("error", "Error interno: usuario no encontrado tras autenticación.");
            }
        } else if (loginStatus == UserService.LoginStatus.USER_NOT_FOUND) {
            model.addAttribute("error", "No existe usuario");
        } else if (loginStatus == UserService.LoginStatus.ERROR_PASSWORD) {
            model.addAttribute("error", "Contraseña incorrecta");
        }
        return loginStatus.toString();
    }



}
