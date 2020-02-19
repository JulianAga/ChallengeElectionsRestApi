package net.avalith.elections.services;

import net.avalith.elections.entities.FakeUsers;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserService {

  @GET("api/")
  Call<FakeUsers> getFakeUsers(@Query("results") Long quantity);
}
