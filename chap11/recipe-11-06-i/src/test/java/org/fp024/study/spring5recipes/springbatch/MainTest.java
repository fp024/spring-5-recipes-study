package org.fp024.study.spring5recipes.springbatch;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainTest {
  @DisplayName("테스트 목적 CSV를 만듦")
  @Order(1)
  @Test
  void makeCSV() throws Exception {
    Path csvPath = Path.of("csv/registrations.csv");

    if (csvPath.toFile().exists()) {
      csvPath.toFile().delete();
    }

    Path csvFile = Files.createFile(csvPath.toAbsolutePath());

    try (FileWriter fw = new FileWriter(csvFile.toFile(), StandardCharsets.UTF_8)) {
      for (int i = 1; i <= 100; i++) {
        String str =
            "firstName_"
                + i
                + ",lastName_"
                + i
                + ",company_"
                + i
                + ",address_"
                + i
                + ",city_"
                + i
                + ",state_"
                + i
                + ",zip_"
                + i
                + ",country_"
                + i
                + ",url_"
                + i
                + ",phoneNumber_"
                + i
                + ",fax_"
                + i
                + System.lineSeparator();
        fw.write(str);
        fw.flush();
      }
    }
  }

  @Order(2)
  @Test
  void testMain() throws Exception {
    Main.main(null);
  }
}
