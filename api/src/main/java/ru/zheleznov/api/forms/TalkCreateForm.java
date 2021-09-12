package ru.zheleznov.api.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalkCreateForm {

    private String title;
    private Set<Long> speakersId;
    private Long auditoriumId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateStart;

    // TODO: 12.09.2021 поставить ограничение на разницу между ними в 2 часа
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateEnd;

}
