package br.com.baseapi.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendContactUsEmailDTO {

    @NotBlank
    @Length(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @ApiModelProperty(example = "Alice")
    private String name;

    @NotBlank
    @ApiModelProperty(example = "example@email.com")
    private String email;

    @NotBlank
    @ApiModelProperty(example = "Hello World")
    private String message;
}
