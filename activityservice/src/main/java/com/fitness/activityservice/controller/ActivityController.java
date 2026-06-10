package com.fitness.activityservice.controller;

import com.fitness.activityservice.DTO.ActivityDTO;
import com.fitness.activityservice.DTO.ActivityRequest;
import com.fitness.activityservice.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping("/track")
    public ResponseEntity<ActivityDTO> trackActivity(@RequestBody ActivityRequest request){

       return  ResponseEntity.ok(activityService.trackActivity(request));
    }


    @GetMapping
    public ResponseEntity<ActivityDTO> getActivity(@PathVariable String id){

        return new ResponseEntity<ActivityDTO>(activityService.getActivity(id), HttpStatus.FOUND);

    }
}
