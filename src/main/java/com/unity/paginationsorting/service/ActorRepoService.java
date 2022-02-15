package com.unity.paginationsorting.service;

import com.unity.paginationsorting.dao.ActorRepository;
import com.unity.paginationsorting.model.Actor;
import com.unity.paginationsorting.paging.Paged;
import com.unity.paginationsorting.paging.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorRepoService {

    private final ActorRepository actorRepository;

    public ActorRepoService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> findAllActors(){
    return actorRepository.findAll();
}

    public List<Actor> findAllActorsByField(String field){
        return actorRepository.findAll(Sort.by(Sort.Direction.ASC,field));
    }

    public Page<Actor> findActorsByPageSize(int offset, int pageSize){
        return actorRepository.findAll(PageRequest.of(offset, pageSize));
    }

    public Page<Actor> findActorsByPageSizeAndField(int offset, int pageSize, String field){
        return actorRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.Direction.ASC,field));
    }

    public Page<Actor> findActorsByFirstName(String name, int offset , int pageSize){
       return actorRepository.findActorsByFirstName(name,PageRequest.of(offset,pageSize));


    }

    public Paged<Actor> getActorPage(int pageNo , int pageSize, String field){
        PageRequest request = PageRequest.of(pageNo - 1, pageSize).withSort(Sort.Direction.ASC,field);
        Page<Actor> actorPage = actorRepository.findAll(request);
        return new Paged<>(actorPage, Paging.of(actorPage.getTotalPages(),pageNo,pageSize));
    }

}
