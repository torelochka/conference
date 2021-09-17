package ru.zheleznov.api.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.zheleznov.api.validations.annotations.CheckDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@CheckDate
public class TalkCreateForm {

    @NotNull(message = "Tile can't be null")
    @NotEmpty(message = "Tile can't be empty")
    private String title;

    @NotNull(message = "Speakers can't be null")
    @NotEmpty(message = "Speakers can't be empty")
    private Set<Long> speakersId;

    @NotNull(message = "Room can't be null")
    private Long auditoriumId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @FutureOrPresent(message = "Date start must be present or future")
    private Date dateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @FutureOrPresent(message = "Date end must be present or future")
    private Date dateEnd;

}
