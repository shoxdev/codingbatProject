package uz.pdp.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotNull(message = "name bush bulmasin")
    private String name;

    @NotNull(message = "sectionId bush bulmasin")
    private Integer sectionId;
}
