package io.agileintelligence.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class JWTLoginSucessReponse {
    private boolean success;
    private String token;
}
