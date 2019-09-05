package com.example.votesite.Retro;

import com.example.votesite.VO.ResponseData;
import com.example.votesite.VO.UserVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @FormUrlEncoded
    @POST("/userService")
    Call<ResponseData> doService(@Field("doing")String doing, @Body UserVO userVO);
}
