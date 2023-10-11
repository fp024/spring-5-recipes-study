package org.fp024.study.spring5recipes.reactive.court.common;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webjars.WebJarAssetLocator;

@Controller
@Slf4j
public class WebjarsController {
  private static final WebJarAssetLocator LOCATOR = new WebJarAssetLocator();

  @ResponseBody
  @GetMapping({"/webjars_locator/{webjar}/**"})
  public ResponseEntity<Resource> locateWebjarAsset(
      @PathVariable String webjar, ServerHttpRequest request) {

    try {
      String mvcPrefix = String.format("/webjars_locator/%s/", webjar);
      String mvcPath = request.getPath().value();

      if (!mvcPath.endsWith(".css") && !mvcPath.endsWith(".js")) {
        LOGGER.warn("허용되지 않는 확장자. mvcPath: {}", mvcPath);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      String fullPath = LOCATOR.getFullPath(webjar, mvcPath.substring(mvcPrefix.length()));
      ClassPathResource classPathResource = new ClassPathResource(fullPath);

      return ResponseEntity.status(HttpStatus.OK) //
          .header(
              HttpHeaders.CONTENT_TYPE,
              Files.probeContentType(Path.of(classPathResource.getPath())))
          .cacheControl(CacheControl.maxAge(Duration.ofDays(7)))
          .body(classPathResource);

    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
