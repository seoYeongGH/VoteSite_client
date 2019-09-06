package com.example.votesite.Retro;

import com.example.votesite.VO.ResponseData;
import com.example.votesite.VO.UserVO;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @FormUrlEncoded
    @POST("/VoteServer/User.do")
    Call<Integer> doService(@FieldMap HashMap<String,Object> parameters);
}
