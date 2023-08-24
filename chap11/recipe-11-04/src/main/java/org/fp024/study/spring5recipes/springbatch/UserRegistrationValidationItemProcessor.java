package org.fp024.study.spring5recipes.springbatch;

import java.util.Arrays;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;

@Slf4j
public class UserRegistrationValidationItemProcessor
    implements ItemProcessor<UserRegistration, UserRegistration> {

  private Collection<String> states;

  public UserRegistrationValidationItemProcessor() {
    this.states =
        Arrays.asList(
            ("AL AK AS AZ AR CA CO CT DE DC FM "
                    + "FL GA GU HI ID IL IN IA KS KY LA ME MH MD "
                    + "MA MI MN MS MO MT NE NV NH NJ NM NY NC ND "
                    + "MP OH OK OR PW PA PR RI SC SD TN TX UT "
                    + "VT VI VA WA WV WI WY")
                .split(" "));
  }

  private String stripNonNumbers(String input) {
    String output = input == null ? "" : input;
    StringBuilder numbersOnly = new StringBuilder();
    for (char potentialDigit : output.toCharArray()) {
      if (Character.isDigit(potentialDigit)) {
        numbersOnly.append(potentialDigit);
      }
    }
    return numbersOnly.toString();
  }

  private boolean isTelephoneValid(String telephone) {
    return StringUtils.hasText(telephone) && telephone.length() == 10;
  }

  private boolean isZipCodeValid(String zip) {
    return StringUtils.hasText(zip) && ((zip.length() == 5) || (zip.length() == 9));
  }

  private boolean isValidState(String state) {
    return StringUtils.hasText(state) && states.contains(state.trim());
  }

  @Override
  public UserRegistration process(UserRegistration input) throws Exception {

    String zipCode = stripNonNumbers(input.getZip());
    String telephone = stripNonNumbers(input.getPhoneNumber());
    String state = input.getState();

    if (isTelephoneValid(telephone) && isZipCodeValid(zipCode) && isValidState(state)) {
      input.setZip(zipCode);
      input.setPhoneNumber(telephone);
      LOGGER.debug("input is valid, returning");
      return input;
    }

    LOGGER.debug(
        "Returning null, telephone: {} / zipCode: {} / state: {}", telephone, zipCode, state);
    return null;
  }
}
