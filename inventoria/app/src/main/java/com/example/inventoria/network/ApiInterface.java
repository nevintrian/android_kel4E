package com.example.inventoria.network;


import com.example.inventoria.network.response.SupplierResponse;
import com.example.inventoria.network.response.UserResponse;


import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    @PUT("supplier/{id_supplier}")
    Completable updateSupplier(
                               @Path("id_supplier") String id_supplier,
                               @Field("nama_supplier") String nama_supplier,
                               @Field("no_telp") String no_telp,
                               @Field("alamat") String alamat);

    @DELETE("supplier/{id_supplier}")
    Completable deleteSupplier(@Path("id_supplier") String id_supplier);





    //Supplier CRUD
    @GET("user")
    Observable<UserResponse> getUsers();
//    @GET("supplier/{page}")
//    Call<SupplierResponse> getSuppliers(@Header("Authorization") String token,
//                                        @Path("page") String page);

    @FormUrlEncoded
    @POST("user")
    Observable<UserResponse> saveUser(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level,
            @Field("nama") String nama,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat);

    @FormUrlEncoded
    @PUT("user/{id_user}")
    Completable updateUser(
                           @Path("id_user") String id_user,
                           @Field("email") String email,
                           @Field("username") String username,
                           @Field("password") String password,
                           @Field("level") String level,
                           @Field("nama") String nama,
                           @Field("tgl_lahir") String tgl_lahir,
                           @Field("jenis_kelamin") String jenis_kelamin,
                           @Field("no_telp") String no_telp,
                           @Field("alamat") String alamat);

    @DELETE("user/{id_user}")
    Completable deleteUser(
                           @Path("id_user") String id_user);




    //Supplier CRUD
    @GET("pelanggan")
    Observable<UserResponse> getPelanggans();
//    @GET("supplier/{page}")
//    Call<SupplierResponse> getSuppliers(@Header("Authorization") String token,
//                                        @Path("page") String page);

    @FormUrlEncoded
    @POST("pelanggan")
    Observable<UserResponse> savePelanggan(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level,
            @Field("nama") String nama,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat);

    @FormUrlEncoded
    @PUT("pelanggan/{id_user}")
    Completable updatePelanggan(
            @Path("id_user") String id_user,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level,
            @Field("nama") String nama,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat);

    @DELETE("pelanggan/{id_user}")
    Completable deletePelanggan(
            @Path("id_user") String id_user);







    //Supplier CRUD
    @GET("sales")
    Observable<UserResponse> getSaless();
//    @GET("supplier/{page}")
//    Call<SupplierResponse> getSuppliers(@Header("Authorization") String token,
//                                        @Path("page") String page);

    @FormUrlEncoded
    @POST("sales")
    Observable<UserResponse> saveSales(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level,
            @Field("nama") String nama,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat);

    @FormUrlEncoded
    @PUT("sales/{id_user}")
    Completable updateSales(
            @Path("id_user") String id_user,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level,
            @Field("nama") String nama,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat);

    @DELETE("sales/{id_user}")
    Completable deleteSales(
            @Path("id_user") String id_user);




    //Supplier CRUD
    @GET("gudang")
    Observable<UserResponse> getGudangs();
//    @GET("supplier/{page}")
//    Call<SupplierResponse> getSuppliers(@Header("Authorization") String token,
//                                        @Path("page") String page);

    @FormUrlEncoded
    @POST("gudang")
    Observable<UserResponse> saveGudang(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level,
            @Field("nama") String nama,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat);

    @FormUrlEncoded
    @PUT("gudang/{id_user}")
    Completable updateGudang(
            @Path("id_user") String id_user,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level,
            @Field("nama") String nama,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat);

    @DELETE("gudang/{id_user}")
    Completable deleteGudang(
            @Path("id_user") String id_user);


}
