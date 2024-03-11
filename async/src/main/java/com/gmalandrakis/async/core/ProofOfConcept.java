package com.gmalandrakis.async.core;


import com.gmalandrakis.async.clients.ExampleFeignClient;
import com.gmalandrakis.async.model.ModelList;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;

import static com.gmalandrakis.async.core.AsyncDispatcher.waitForAll;


/*
    Sends requests via the clients and receives the results asynchronously
 */
@Service
public class ProofOfConcept {

    ExampleFeignClient feignClient;

    //WebClient springClient; //TODO: Enable

    @Autowired
    ProofOfConcept(ExampleFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    public ModelList getByParallelStream(final List<String> uuids) {
        ModelList fe = new ModelList();
        List<String> successList = new ArrayList<>();
        List<String> failedList = new ArrayList<>();

        try {
            final var completableFutures = uuids.parallelStream()
                    .map(uuid -> {
                        var ret = feignClient.getModel(String.valueOf(uuid));
                        if (ret != null && ret.getBody() != null && ret.getStatusCode().is2xxSuccessful()) {
                            successList.add(uuid);
                        } else {
                            failedList.add(uuid);
                        }
                        return ret;
                    })
                    .filter(r -> r != null && r.getStatusCode().is2xxSuccessful())
                    .map(ResponseEntity::getBody)
                    .filter(Objects::nonNull)
                    .map(r -> fe.getModelList().add(r)).toList();

        } finally { //TODO
        }

        return fe;
    }


    public ModelList getByDispatcherFeign(
            final List<String> uuids) {
        /*
            As of 3/2024, Java has a tough time using parallel streams when ThreadLocal variables are into play,
            so the asyncDispacher should be used instead of parallelStream() even though it is way slower.
         */
        ModelList val = new ModelList();
        List<String> successList = new ArrayList<>();
        List<String> failedList = new ArrayList<>();
        try {
            var completableFutures = uuids.stream()
                    .map(uuid -> AsyncDispatcher.run(() -> {
                                var ret = feignClient.getModel(String.valueOf(uuid));
                                if (ret != null && ret.getBody() != null && ret.getStatusCode().is2xxSuccessful()) {
                                    successList.add(uuid);
                                } else {
                                    failedList.add(uuid);
                                }
                                return ret;
                            }
                    )).toList();

            waitForAll(completableFutures)
                    .filter(Objects::nonNull)
                    .filter(r-> r.getStatusCode().is2xxSuccessful())
                    .map(ResponseEntity::getBody)
                    .filter(Objects::nonNull)
                    .map(r -> val.getModelList().add(r)).toList();

        } catch (Exception e ){
            e.printStackTrace();
            System.out.println("Make sure the restful-service-example is up and running.");
        } finally{
            System.out.println("Successful uuids in getByDispatcherFeign(): " + successList.size());
            System.out.println("Failed uuids in getByDispatcherFeign(): " + failedList.size());
        }

        return val;
    }

    private void timedRun(Method method, Object ... args) throws Exception {
        long start = System.currentTimeMillis();
        var ret = method.invoke(this, args);
        long end = System.currentTimeMillis() - start;
        System.out.println("Function: " + method.getName() + "() needed " + end + " ms. ");

    }

    @PostConstruct
    private void runAfterCreating() throws Exception {
        final var idLst = List.of("03472000-1f2d-436d-ad90-143a602b975a", "03472000-1f2d-436d-ad90-143a602b975b", "03472000-1f2d-436d-ad90-143a602b975c");
        this.timedRun(ProofOfConcept.class.getMethod("getByDispatcherFeign", List.class), idLst);
        this.timedRun(ProofOfConcept.class.getMethod("getByParallelStream", List.class), idLst);
    }




}
