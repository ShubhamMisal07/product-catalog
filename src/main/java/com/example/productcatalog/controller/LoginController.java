// package com.example.productcatalog.controller;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.servlet.view.RedirectView;

// @Controller
// public class LoginController {

//     private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
//     /**
//      * Initiates the Logto SSO authentication flow
//      * @return Redirect to Logto authorization endpoint
//      */
//     @GetMapping("/login")
//     public RedirectView login() {
//         logger.info("Initiating Logto SSO authentication flow");
//         RedirectView redirectView = new RedirectView();
//         redirectView.setUrl("/oauth2/authorization/logto");
//         return redirectView;
//     }

//     /**
//      * Optional: Home endpoint that redirects to login
//      */
//     @GetMapping("/")
//     public RedirectView home() {
//         return new RedirectView("/login");
//     }
// }