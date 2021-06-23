package uz.pdp.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDto {

    @NotNull(message = "name bush bulmasin")
    private String name;

    @NotNull(message = "Language bush bulmasin ")
    private Integer languageId;
}
