package ru.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.zheleznov.api.dto.RequestResult;
import ru.zheleznov.api.dto.TalkDto;
import ru.zheleznov.api.forms.TalkCreateForm;
import ru.zheleznov.api.services.TalkService;

@RestController
public class TalkCreateController {

    private final TalkService talkService;

    @Autowired
    public TalkCreateController(TalkService talkService) {
        this.talkService = talkService;
    }

    @PostMapping("/talk")
    public RequestResult<TalkDto> createTalk(@RequestBody TalkCreateForm talkCreateForm) {
        return talkService.save(talkCreateForm);
    }
}
