package com.eric.mooncoin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ericqi on 21/01/2018.
 */
@RestController
public class MoonCoin {

    @RequestMapping("/")
    public String hello() {
        return String.join(
                "\n",
                "Usage:",
                "/ print usage",
                ""
        );
    }
}
