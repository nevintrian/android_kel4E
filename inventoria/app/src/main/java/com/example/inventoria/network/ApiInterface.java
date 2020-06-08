package com.example.inventoria.network;


import com.example.inventoria.network.response.BarangResponse;
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



    @FormUrlEncoded
    @PUT("user/{id_user}")
    Completable updateProfil(
            @Path("id_user") String  id_user,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level,
            @Field("nama") String nama,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat);





    //Supplier CRUD
    @GET("user")
    Observable<UserResponse> getUser();
//    @GET("supplier/{page}")
//    Call<SupplierResponse> getSuppliers(@Header("Authorization") String token,
//                                        @Path("page") String page);



    @Multipart
    @POST("user")
    Observable<UserResponse> saveUser(
            @Part MultipartBody.Part foto,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level,
            @Part("nama") RequestBody nama,
            @Part("tgl_lahir") RequestBody tgl_lahir,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("alamat") RequestBody alamat,
            @Part("no_telp") RequestBody no_telp);

    @Multipart
    @POST("user/update/{id_user}")
    Completable updateUser(
                           @Path("id_user") String  id_user,
                           @Part MultipartBody.Part foto,
                           @Part("email") RequestBody email,
                           @Part("username") RequestBody username,
                           @Part("password") RequestBody password,
                           @Part("level") RequestBody level,
                           @Part("nama") RequestBody nama,
                           @Part("tgl_lahir") RequestBody tgl_lahir,
                           @Part("jenis_kelamin") RequestBody jenis_kelamin,
                           @Part("alamat") RequestBody alamat,
                           @Part("no_telp") RequestBody no_telp);



    @DELETE("user/{id_user}")
    Completable deleteUser(
                           @Path("id_user") String  id_user);




    //Supplier CRUD
    @GET("pelanggan")
    Observable<UserResponse> getPelanggan();
//    @GET("supplier/{page}")
//    Call<SupplierResponse> getSuppliers(@Header("Authorization") String token,
//                                        @Path("page") String page);

    @Multipart
    @POST("pelanggan")
    Observable<UserResponse> savePelanggan(
            @Part MultipartBody.Part foto,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level,
            @Part("nama") RequestBody nama,
            @Part("tgl_lahir") RequestBody tgl_lahir,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("alamat") RequestBody alamat,
            @Part("no_telp") RequestBody no_telp);

    @Multipart
    @POST("pelanggan/update/{id_user}")
    Completable updatePelanggan(
            @Path("id_user") String  id_user,
            @Part MultipartBody.Part foto,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level,
            @Part("nama") RequestBody nama,
            @Part("tgl_lahir") RequestBody tgl_lahir,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("alamat") RequestBody alamat,
            @Part("no_telp") RequestBody no_telp);

    @DELETE("pelanggan/{id_user}")
    Completable deletePelanggan(
            @Path("id_user") String id_user);







    //Supplier CRUD
    @GET("sales")
    Observable<UserResponse> getSales();
//    @GET("supplier/{page}")
//    Call<SupplierResponse> getSuppliers(@Header("Authorization") String token,
//                                        @Path("page") String page);

    @Multipart
    @POST("sales")
    Observable<UserResponse> saveSales(
            @Part MultipartBody.Part foto,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level,
            @Part("nama") RequestBody nama,
            @Part("tgl_lahir") RequestBody tgl_lahir,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("alamat") RequestBody alamat,
            @Part("no_telp") RequestBody no_telp);

    @Multipart
    @POST("sales/update/{id_user}")
    Completable updateSales(
            @Path("id_user") String  id_user,
            @Part MultipartBody.Part foto,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level,
            @Part("nama") RequestBody nama,
            @Part("tgl_lahir") RequestBody tgl_lahir,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("alamat") RequestBody alamat,
            @Part("no_telp") RequestBody no_telp);

    @DELETE("sales/{id_user}")
    Completable deleteSales(
            @Path("id_user") String id_user);




    //Supplier CRUD
    @GET("gudang")
    Observable<UserResponse> getGudang();
//    @GET("supplier/{page}")
//    Call<SupplierResponse> getSuppliers(@Header("Authorization") String token,
//                                        @Path("page") String page);

    @Multipart
    @POST("gudang")
    Observable<UserResponse> saveGudang(
            @Part MultipartBody.Part foto,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level,
            @Part("nama") RequestBody nama,
            @Part("tgl_lahir") RequestBody tgl_lahir,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("alamat") RequestBody alamat,
            @Part("no_telp") RequestBody no_telp);

    @Multipart
    @POST("gudang/update/{id_user}")
    Completable updateGudang(
            @Path("id_user") String  id_user,
            @Part MultipartBody.Part foto,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level,
            @Part("nama") RequestBody nama,
            @Part("tgl_lahir") RequestBody tgl_lahir,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("alamat") RequestBody alamat,
            @Part("no_telp") RequestBody no_telp);

    @DELETE("gudang/{id_user}")
    Completable deleteGudang(
            @Path("id_user") String id_user);




    //Barang CRUD
    @GET("barang")
    Observable<BarangResponse> getBarang();



    @Multipart
    @POST("barang")
    Observable<BarangResponse> saveBarang(
                                          @Part MultipartBody.Part foto_barang,
                                          @Part("id_supplier") RequestBody id_supplier,
                                          @Part("nama_barang") RequestBody nama_barang,
                                          @Part("kemasan") RequestBody kemasan,
                                          @Part("merk") RequestBody merk,
                                          @Part("jenis") RequestBody jenis,
                                          @Part("stok") RequestBody stok,
                                          @Part("harga") RequestBody harga,
                                          @Part("terjual") RequestBody terjual);

    @Multipart
    @POST("barang/update/{id_barang}")
    Completable updateBarang(
                             @Path("id_barang") String id_barang,
                             @Part MultipartBody.Part foto_barang,
                             @Part("id_supplier") RequestBody id_supplier,
                             @Part("nama_barang") RequestBody nama_barang,
                             @Part("kemasan") RequestBody kemasan,
                             @Part("merk") RequestBody merk,
                             @Part("jenis") RequestBody jenis,
                             @Part("stok") RequestBody stok,
                             @Part("harga") RequestBody harga,
                             @Part("terjual") RequestBody terjual);

    @DELETE("barang/{id_barang}")
    Completable deleteBarang(
                             @Path("id_barang") String id_barang);



}
