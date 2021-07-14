package br.com.baseapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserInput {

    @NotBlank
    @ApiModelProperty(example = "Alice")
    private String name;

    @NotBlank
    @ApiModelProperty(example = "example@email.com")
    private String email;

    @NotBlank
    @ApiModelProperty(example = "123456")
    private String password;
}
