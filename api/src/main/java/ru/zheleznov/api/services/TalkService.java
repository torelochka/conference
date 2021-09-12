package ru.zheleznov.api.services;

import ru.zheleznov.api.dto.RequestResult;
import ru.zheleznov.api.dto.TalkDto;
import ru.zheleznov.api.forms.TalkCreateForm;

import java.util.List;

public interface TalkService {
    List<TalkDto> getAllTalk();
    RequestResult<TalkDto> save(TalkCreateForm talkForm);
}
