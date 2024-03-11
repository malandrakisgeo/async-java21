package com.gmalandrakis.async.clients;

import com.gmalandrakis.async.config.FeignClientConfig;
import com.gmalandrakis.async.model.MyModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "feign-example", url = "${local-server-url}",
        configuration = FeignClientConfig.class)
public interface ExampleFeignClient {

    @GetMapping(path = "/model/{uuid}")
    ResponseEntity<MyModel> getModel(
            @PathVariable(value = "uuid") final String uuid);
}
