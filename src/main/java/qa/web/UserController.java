package qa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qa.domain.QaUser;
import qa.service.UserService;
import qa.vo.QaUserVo;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public QaUser showLoginForm() {
        return new QaUser();
    }

    @ModelAttribute("user")
    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public QaUser showRegisterForm() {
        return new QaUser();
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("user") QaUser user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user/register";
        }
        userService.addOneUser(user);
        return "redirect:/questions";
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/questions";
    }
    @ModelAttribute("users")
    @RequestMapping(value = "/user/users", method = RequestMethod.GET)
    public List<QaUser> showUsers() {
        List<QaUser> list = userService.findAll();
        return list;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String userInfo(@PathVariable("id") int id,Model model) {
        QaUser user = userService.findById(id);
        model.addAttribute("user",user);
        model.addAttribute("questions",user.getQuestions());
        return "user/userInfo";
    }

    @RequestMapping(value = "/user/findName", method = RequestMethod.POST)
    public String showUserByName(@RequestParam("user.name") String username,Model model) {
        List<QaUser> list = userService.findName( username );
        model.addAttribute("users",list);
        return "user/users";
    }


}
