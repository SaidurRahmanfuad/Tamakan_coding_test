package com.saidur.own.tamakan.Network;


import com.saidur.own.tamakan.Network.Request.RQ_Login;
import com.saidur.own.tamakan.Network.Response.Rsp_Login;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface ApiSource {
    @Headers({"Accept: application/json"})
    @POST("/api/login")
    Call<Rsp_Login> callLogin(@Body RQ_Login login);

    /*@GET("/api/Reports/GetEmpTrackingReport")
    Call<List<ModelAdmn_Tracking>> GetAdmin_Tracking_Rpt(@Query("wings_id") int wings_id, @Query("date") String date);

    @GET("/api/Reports/GetEmpTrackingDetailsReport")
    Call<List<Model_tracking_dtls>> GetAdmin_Tracking_Dtls_Rpt(@Query("EmpMasterId") int EmpMasterId, @Query("date") String date);

    @GET("/api/SeedData/GetWingList")
    Call<List<ModelCompany>> GetAdmin_Company();

    @GET("/api/Reports/GetClientVisitReport")
    Call<Rsp_Admn_CusVisit> GetAdmin_VisitReport(@Query("wings_id") int wings_id, @Query("date") String date, @Query("role") String role);
*/



}


