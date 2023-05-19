package com.hello_world_sprinboot.scholify.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ScholifyInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String,String> mp = new HashMap<>();
        mp.put("App name","Scholify");
        mp.put("App Desc","Scholify web app for school management");
        mp.put("App Version","1.0.0");
        mp.put("Contact Email", "maheshphutane1810@gmail.com");
        mp.put("Contact Mobile", "8550950183");
        builder.withDetail("Scholify-info", mp);
    }
}
