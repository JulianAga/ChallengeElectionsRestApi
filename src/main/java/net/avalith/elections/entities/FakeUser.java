package net.avalith.elections.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FakeUser {

  @JsonProperty("dob")
  private FakeUserDob fakeUserDob;

  @JsonProperty("email")
  private String email;

  @JsonProperty("name")
  private FakeUserName fakeUserName;

  @JsonProperty("id")
  private FakeUserId fakeUserId;

  @JsonProperty("login")
  private FakeUserLogin fakeUserLogin;
}
