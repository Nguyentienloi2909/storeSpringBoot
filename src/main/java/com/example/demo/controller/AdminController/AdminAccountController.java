package com.example.demo.controller.AdminController;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRoleRepository;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.User_Role;
import com.example.demo.service.UserService;
import com.example.demo.validation.RegisterUser;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminAccountController {
    public static final Logger logger = LoggerFactory.getLogger(AdminAccountController.class);
    private UserService userService;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;
    @Autowired
    public AdminAccountController(UserService userService,
                           RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }
    // rest api
    @GetMapping("getUserByEmail/{email}")
    @ResponseBody
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        User user = this.userService.getUserByEmail(email);
//        RegisterUser registerUser = new RegisterUser();
//        registerUser.setEmail(user.getEmail());
//        registerUser.setPhoneNumber(user.getPhoneNumber());
//        registerUser.setPassword(user.getPassword());
//        registerUser.setId(user.getIdUser());
//        registerUser.setFullName(user.getFullname());
//        registerUser.setEnble(user.isEnble());
        return ResponseEntity.ok(user);
    }
    @GetMapping("getAllAccount")
    @ResponseBody
    public ResponseEntity<List<RegisterUser>> getAllAccount(){
        List<User> users = this.userService.getALlUser();
        List<RegisterUser> registerUsers = users.stream()
                .map( user -> new RegisterUser(
                        user.getIdUser(),
                        user.getFullname(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getPassword(),
                        user.isEnble()
                )
        ).collect(Collectors.toList());
        return ResponseEntity.ok(registerUsers);
    }

//    @PutMapping("addAccount")

    //đóng rest api

    //    Mở ACCOUNT
    @GetMapping("/quanLyAccount")
    public String getALlUser(Model model){
        List<User> users = this.userService.getALlUser();
        model.addAttribute("users", users);
        return "/admin/account/indexAccount";
    }

    @GetMapping("add_account")
    public String addAccount(Model model){
        RegisterUser registerUser = new RegisterUser();
        registerUser.setEnble(true);
        model.addAttribute("registerUser", registerUser);
        return "/admin/account/addAccount";
    }

    @PostMapping("add_account")
    public String addAccount(@Valid @ModelAttribute("registerUser") RegisterUser registerUser,
                             BindingResult result,
                             Model model,
                             @RequestParam("rePassword") String rePassword){

        logger.info("rePasssword:{}",rePassword);
        logger.info("Passsword:{}",registerUser.getPassword());
        User userExisting = this.userService.getUserByEmail(registerUser.getEmail());
        if(result.hasErrors() || !registerUser.getPassword().equals(rePassword) || userExisting != null){
            if(!registerUser.getPassword().equals(rePassword))
                model.addAttribute("EPassword", "mật khẩu nhập lại không đúng");
            if(userExisting != null)
                model.addAttribute("EAccount", "Tài khoản đã tồn tại");

            model.addAttribute("registerUser", registerUser);
            return "/admin/account/addAccount";
        }
//        nếu không lỗi
        User user = new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setEmail(registerUser.getEmail());
        user.setFullname(registerUser.getFullName());
        user.setPassword( passwordEncoder.encode(registerUser.getPassword()));
        user.setPhoneNumber(registerUser.getPhoneNumber());
        user.setEnble(registerUser.isEnble());

        Role role = this.roleRepository.findByRoleName("USER");
        User_Role user_role = new User_Role();
        if(this.userService.addUser(user)){
            User user1 = this.userService.getUserByEmail(registerUser.getEmail());
            user_role.setUser(user1);
            user_role.setRole(role);
            this.userRoleRepository.save(user_role);
            return "redirect:/admin/quanLyAccount";
        }else {
            model.addAttribute("registerUser", registerUser);
            return "/admin/account/addAccount";
        }
    }

    @GetMapping("remove_account/{idUser}")
    public String removeUser(@PathVariable("idUser") int id, Model model) {
        if (this.userService.deleteUserById(id)) {
            model.addAttribute("ThongBao", "đã xóa thành công");
        } else {
            model.addAttribute("ThongBao", "Không thể xóa mục này");
        }
        return "redirect:/admin/quanLyAccount";
    }

    @GetMapping("search_account")
    public String searchUser(@RequestParam("search") String search, Model model){
        List<User> users = this.userService.findByFullName(search);
        model.addAttribute("users", users);
        return "/admin/account/indexAccount";
    }
}
