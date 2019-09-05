package com.example.votesite.Retro;

import com.example.votesite.VO.ResponseData;
import com.example.votesite.VO.UserVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("/userService/{doing}")
    Call<ResponseData> doService(@Path("doing")String doing, @Body UserVO userVO);
}
