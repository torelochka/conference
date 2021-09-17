package ru.zheleznov.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.zheleznov.api.dto.MessageResponse;
import ru.zheleznov.api.dto.TalkDto;
import ru.zheleznov.api.dto.UserDto;
import ru.zheleznov.api.forms.TalkCreateForm;
import ru.zheleznov.api.services.TalkService;
import ru.zheleznov.impl.models.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/talk")
public class TalkCrudController {

    private final TalkService talkService;

    private final ModelMapper modelMapper;

    @Autowired
    public TalkCrudController(TalkService talkService, ModelMapper modelMapper) {
        this.talkService = talkService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTalk(@PathVariable Long id) {
        TalkDto talkDto = talkService.getTalk(id);
        return ResponseEntity.ok(talkDto);
    }

    @GetMapping
    public List<TalkDto> getTalks() {
        return talkService.getAllTalk();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTalk(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (talkService.deleteTalk(id, modelMapper.map(user, UserDto.class))) {
            return ResponseEntity.ok().body(new MessageResponse("Success"));
        }

        return ResponseEntity.status(403).body(new MessageResponse("It's not your talk"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TalkDto> updateTalk(@PathVariable Long id, @RequestBody @Valid TalkCreateForm talkCreateForm) {
        return ResponseEntity.ok(talkService.updateTalk(id, talkCreateForm));
    }

    @PostMapping
    public ResponseEntity<TalkDto> createTalk(@RequestBody @Valid TalkCreateForm talkCreateForm) {
        return ResponseEntity.ok(talkService.save(talkCreateForm));
    }
}
