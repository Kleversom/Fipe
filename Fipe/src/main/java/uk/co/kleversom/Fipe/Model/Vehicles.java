package uk.co.kleversom.Fipe.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Vehicles(@JsonAlias ("Valor") String price,
                        @JsonAlias ("Marca") String brand,
                       @JsonAlias ("Modelo") String model,
                       @JsonAlias ("AnoModelo") Integer yearModel,
                       @JsonAlias ("Combustivel") String fueltype) {

}
