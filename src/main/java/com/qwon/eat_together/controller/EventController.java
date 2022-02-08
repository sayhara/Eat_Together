package com.qwon.eat_together.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meeting/{url}")
@RequiredArgsConstructor
public class EventController {
}
