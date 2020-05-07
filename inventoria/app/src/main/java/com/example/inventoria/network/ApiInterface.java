package com.example.inventoria.network;


import com.example.inventoria.network.response.SupplierResponse;
import com.example.inventoria.network.response.UserResponse;


import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

//Auth


//Supplier CRUD
    @GET("supplier")
    Observable<SupplierResponse> getSuppliers();
//    @GET("supplier/{page}")
//    Call<SupplierResponse> getSuppliers(@Header("Authorization") String token,
//                                        @Path("page") String page);

    @FormUrlEncoded
    @POST("supplier")
    Observable<SupplierResponse> saveSupplier(
                                              @Field("nama_supplier") String nama_supplier,
                                              @Field("no_telp") String no_telp,
                                              @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("supplier/update/{id}")
    Completable updateSupplier(
                               @Path("id_supplier") String id_supplier,
                               @Field("nama_supplier") String nama_supplier,
                               @Field("no_telp") String no_telp,
                               @Field("alamat") String alamat);

    @POST("supplier/delete/{id}")
    Completable deleteSupplier(
                               @Path("id_supplier") String id_supplier);





    //Supplier CRUD
    @GET("user/{page}")
    Observable<UserResponse> getUsers(
                                      @Path("page") String page);
//    @GET("supplier/{page}")
//    Call<SupplierResponse> getSuppliers(@Header("Authorization") String token,
//                                        @Path("page") String page);

    @FormUrlEncoded
    @POST("user")
    Observable<UserResponse> saveUser(
                                      @Field("nama") String nama,
                                      @Field("no_telp") String no_telp,
                                      @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("user/update/{id}")
    Completable updateUser(
                           @Path("id_user") String id_user,
                           @Field("nama") String nama,
                           @Field("no_telp") String no_telp,
                           @Field("alamat") String alamat);

    @POST("user/delete/{id}")
    Completable deleteUser(
                           @Path("id_user") String id_user);





}
