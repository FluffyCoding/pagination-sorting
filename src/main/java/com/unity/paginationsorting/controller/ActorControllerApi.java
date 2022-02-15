package com.unity.paginationsorting.controller;

import com.unity.paginationsorting.api.APIResponseStatus;
import com.unity.paginationsorting.model.Actor;
import com.unity.paginationsorting.service.ActorRepoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ActorControllerApi {

    private final ActorRepoService repoService;

    public ActorControllerApi(ActorRepoService repoService) {
        this.repoService = repoService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Actor>>  findAllActors(){
        return ResponseEntity.status(HttpStatus.OK).body(repoService.findAllActors());
    }

    @GetMapping(path = "/all/{field}")
    public ResponseEntity<List<Actor>>  findAllActors(@PathVariable("field") String field){
        return ResponseEntity.status(HttpStatus.OK).body(repoService.findAllActorsByField(field));
    }

    @GetMapping(path = "/all/{offset}/{size}")
    public ResponseEntity<Page<Actor>>  findAllActors(@PathVariable("offset") int offset,
                                                      @PathVariable("size") int pageSize){
        return ResponseEntity.status(HttpStatus.OK).body(repoService.findActorsByPageSize(offset, pageSize));
    }

    @GetMapping(path = "/all/{offset}/{size}/{field}")
    public ResponseEntity<Page<Actor>>  findAllActorsWithSortAndPage(@PathVariable("offset") int offset,
                                                                     @PathVariable("size") int pageSize,
                                                                     @PathVariable("field") String field){
        return ResponseEntity.status(HttpStatus.OK).body(repoService.findActorsByPageSizeAndField(offset, pageSize, field));
    }

    @GetMapping(path = "/all/bn/{offset}/{size}/{name}")
    public ResponseEntity<Page<Actor>>  findAllActorsByFirstNameAndPage(@PathVariable("offset") int offset,
                                                                     @PathVariable("size") int pageSize,
                                                                     @PathVariable("name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(repoService.findActorsByFirstName(name, offset, pageSize));
    }

    @GetMapping(path = "/all/fn")
    public APIResponseStatus<Page<Actor>> findActorsByFirstName(@RequestParam int offset,
                                                                @RequestParam int pageSize,
                                                                @RequestParam String name){
        Page<Actor> actors = repoService.findActorsByFirstName(name, offset, pageSize);
        return new APIResponseStatus<>(actors.getNumberOfElements(),actors);
    }

}
