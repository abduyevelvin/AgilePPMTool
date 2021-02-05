package io.agileintelligence.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProjectNotFoundExceptionResponse {
    private String projectIdentifier;
}
