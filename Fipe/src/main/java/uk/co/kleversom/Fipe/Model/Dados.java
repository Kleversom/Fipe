package uk.co.kleversom.Fipe.Model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Dados (@JsonAlias ("codigo") String cod,
                    @JsonAlias("nome") String name) {

}
