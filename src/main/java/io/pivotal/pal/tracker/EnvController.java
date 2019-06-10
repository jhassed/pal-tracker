package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private String port;
    private String memoryLimit;
    private String index;
    private String address;

    public EnvController(@Value("${PORT:NOT SET}") String cfPort,
                         @Value("${MEMORY_LIMIT:NOT SET}") String cfMemoryLimit,
                         @Value("${CF_INSTANCE_INDEX:NOT SET}") String cfIndex,
                         @Value("${CF_INSTANCE_ADDR:NOT SET}") String cfAddress){

        port = cfPort;
        memoryLimit = cfMemoryLimit;
        index = cfIndex;
        address = cfAddress;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv(){
        Map<String, String> map = new HashMap<>();
        map.put("PORT", port);
        map.put("MEMORY_LIMIT", memoryLimit);
        map.put("CF_INSTANCE_INDEX", index);
        map.put("CF_INSTANCE_ADDR", address);
        return map;
    }
}
