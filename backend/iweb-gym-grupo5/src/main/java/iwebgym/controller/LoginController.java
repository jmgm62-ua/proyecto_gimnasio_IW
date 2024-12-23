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

import javax.servlet.http.HttpSession;

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
        return "formLogin";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginData loginData, Model model, HttpSession session) {
        UserService.LoginStatus loginStatus = usuarioService.login(loginData.geteMail(), loginData.getPassword());

        if (loginStatus == UserService.LoginStatus.LOGIN_OK) {
            UserData usuario = usuarioService.findByEmail(loginData.geteMail());
            managerUserSession.logearUsuario(usuario.getId());

            if ("XMLHttpRequest".equals(session.getAttribute("X-Requested-With"))) {
                return "{\"redirect\":\"/usuarios/" + usuario.getId() + "/tareas\"}";
            }

            return "redirect:/usuarios/" + usuario.getId() + "/tareas";
        } else if (loginStatus == UserService.LoginStatus.USER_NOT_FOUND) {
            return "{\"error\":\"No existe usuario\"}";
        } else if (loginStatus == UserService.LoginStatus.ERROR_PASSWORD) {
            return "{\"error\":\"Contraseña incorrecta\"}";
        }
        return "{\"error\":\"Error desconocido\"}";
    }

}
