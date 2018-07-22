package com.example.infosys.seanproject.Listener;

import com.example.infosys.seanproject.Class.Profile;
import com.example.infosys.seanproject.Util.Constant;

import org.json.JSONObject;

public class GetProfileListener extends BaseListener {

    public String URL_GET_PROFILE = Constant.BASE_URL + "api?m=random";

    public Profile getDoProfile(JSONObject jsonObject) throws Exception{
        Profile profile = new Profile();
        profile.setId((String) jsonObject.get("id"));
        profile.setDate(String.valueOf((Number) jsonObject.get("date")));
        profile.setCaption((String) jsonObject.get("caption"));
        profile.setThumbnailSrc((String) jsonObject.get("thumbnailSrc"));
        profile.setAccount((String) jsonObject.get("account"));

        return profile;
    }

}
