package life.antonio12.community12.community12.Provider;

import com.alibaba.fastjson.JSON;
import life.antonio12.community12.community12.dto.AccessTokenDTO;
import life.antonio12.community12.community12.dto.GithubUser;
import okhttp3.*;

import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Antonio on 2019/06/12
 *
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
           /* String[] split = string.split(regex:"&");
            String tokenstr = split[0];
            String token = tokenstr.split(regex:"=")[1];*/
            String token = string.split("&")[0]. split("=")[1];
            return token;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);  // 把string的json的对象自动转化解析位class对象
            return githubUser;
        } catch (IOException e) {
            return null;
        }
    }
}
