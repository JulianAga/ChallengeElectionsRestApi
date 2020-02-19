package net.avalith.elections.config;

import net.avalith.elections.services.RandomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RandomUserRetrofitConfiguration {

  /***retrofitConfig.
   * Construct the retrofit
   * @return return interface to create users
   */
  @Bean("fakeUsers")
  public RandomUserService randomUserRetrofitConfiguration() {
    Retrofit retrofit = new Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build();
    return retrofit.create(RandomUserService.class);
  }


}
