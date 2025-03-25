package com.example.service;

import com.example.entity.response.ResponseData;
import com.example.entity.requset.UserDetails;
import com.example.repo.DatabaseSchema;

import java.util.List;

public class UserService {

    private DatabaseSchema repo;

    public UserService(DatabaseSchema repo) {
        this.repo = repo;
    }

    public String insertData(UserDetails user) {
        repo.post(user);
        return "inserted";
    }

    public List<UserDetails> getData() {
        return repo.get();
    }

    public String putData(int id, String name) {
        repo.put(id, name);
        return "updated";
    }

    public List<ResponseData> allResponse(){
        return repo.responseData();
    }


}
