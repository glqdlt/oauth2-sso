package com.glqdlt.sso.clientA;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @PreAuthorize("hasAuthority('hello_auth')")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PreAuthorize("hasAuthority('world_auth')")
    @GetMapping("world")
    public String world() {
        return "world";
    }

    @GetMapping("/")
    public String notNeed() {
        return "root_page";
    }
}
