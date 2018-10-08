package shubham.in.bustimings.POJO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("/BackEnd-0.0.1-SNAPSHOT/api/user/users/{user_id}")
    Call<User> getUser(@Path(value = "user_id", encoded = true)String user_id);

    @FormUrlEncoded
    @POST("/BackEnd-0.0.1-SNAPSHOT/api/user/users")
    Call<UserResponse> createUser(@Field("firstname") String firstname, @Field("middlename")String middlename,
                                  @Field("lastname")String lastname, @Field("mobilenumber") String mobilenumber,
                                  @Field("emailid") String emailid, @Field("password") String password );

    @GET("/BackEnd-0.0.1-SNAPSHOT/api/timings/getAllRoutes")
    Call<BusTimes>getBusTimes();

    @GET("/BackEnd-0.0.1-SNAPSHOT/api/timings/getSpecific/{bus_id}")
    Call<BusParticular>getParticular(@Path(value = "bus_id", encoded = true)String bus_id);
}