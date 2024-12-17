package ru.sphinx.hhclicker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sphinx.hhclicker.service.HHService;

import java.io.IOException;

/**
 * Copyright (c) 2023 Igor Kartashov SphinxCode
 * All rights reserved.
 */
@RestController
@RequiredArgsConstructor
public class TestController {
    private final HHService hhService;

    @GetMapping("test")
    public void test() throws IOException {
        hhService.auth();
    }
}
