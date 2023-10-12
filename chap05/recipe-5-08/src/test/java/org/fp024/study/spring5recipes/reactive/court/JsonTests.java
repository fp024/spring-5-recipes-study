package org.fp024.study.spring5recipes.reactive.court;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import org.fp024.study.spring5recipes.reactive.court.domain.Player;
import org.fp024.study.spring5recipes.reactive.court.domain.Reservation;
import org.fp024.study.spring5recipes.reactive.court.domain.SportType;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class JsonTests {

  @Test
  void jsonTest() throws JsonProcessingException {
    final String json =
        "{\"courtName\":\"Tennis #2\",\"date\":[2008,1,14],\"hour\":20,\"player\":{\"name\":\"James\",\"phone\":\"N/A\"},\"sportType\":{\"id\":1,\"name\":\"Tennis\"},\"dateAsUtilDate\":1200265200000}\n";
    ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().modules(new JavaTimeModule()).build();

    Reservation reservation = mapper.readValue(json, Reservation.class);
    System.out.println(reservation);

    assertThat(reservation) //
        .hasFieldOrPropertyWithValue("courtName", "Tennis #2")
        .hasFieldOrPropertyWithValue("date", LocalDate.of(2008, 1, 14))
        .hasFieldOrPropertyWithValue("hour", 20);

    assertThat(reservation.getPlayer())
        .usingRecursiveComparison() //
        .isEqualTo(new Player("James", "N/A"));

    assertThat(reservation.getSportType())
        .usingRecursiveComparison() //
        .isEqualTo(new SportType(1, "Tennis"));
  }
}
