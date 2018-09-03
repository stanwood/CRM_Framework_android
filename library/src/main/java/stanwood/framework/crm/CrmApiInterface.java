package stanwood.framework.crm;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CrmApiInterface {


    @POST("users/firebase/{project_id}")
    Call<Void> sendTokens(@Path("project_id") String jiraProjectId, @Body Map<String, Object> params);


}
