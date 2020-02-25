package net.avalith.elections.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@ToString
@Table(name = "users")
public class User implements Serializable {

  @Id
  private String id;

  @NotNull
  @Min(18)
  private Integer age;

  @NotNull
  @Column(name = "day_of_birth")
  @JsonProperty(value = "day_of_birth")
  @DateTimeFormat(pattern = "yyyy-mm-dd")
  private LocalDate dayOfBirth;

  @Email
  @NotEmpty
  private String email;

  @Size(min = 2, max = 30)
  @JsonProperty(value = "first_name")
  @NotEmpty
  @Column(name = "first_name")
  private String firstName;

  @Size(min = 2, max = 30)
  @Column(name = "last_name")
  @JsonProperty(value = "last_name")
  @NotEmpty
  private String lastName;

  @Column(name = "is_fake")
  @JsonProperty(value = "is_fake")
  private Boolean isFake = false;

  @JsonIgnore
  @ToString.Exclude
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private List<Vote> votes;

}
