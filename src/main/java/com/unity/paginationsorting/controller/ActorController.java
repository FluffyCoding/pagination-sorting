package com.unity.paginationsorting.controller;


import com.unity.paginationsorting.dao.Offset;
import com.unity.paginationsorting.model.Actor;
import com.unity.paginationsorting.service.ActorRepoService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/get")
public class ActorController {

    private final ActorRepoService repoService;
    private int pageSize = 5;

    public ActorController(ActorRepoService repoService) {
        this.repoService = repoService;
    }

    /*@PostMapping("/setOffset")
     public String setPageOffset(@ModelAttribute Offset setPageSize){
        pageSize = setPageSize.getSize();
        System.out.println(pageSize);
        return "redirect:/get/all/0/id";
     }*/

     @GetMapping("/setOffset/{size}")
     public String setPageSize(@PathVariable("size") int size){
        pageSize = size;
        System.out.println(pageSize);
        return "redirect:/get/pg";
     }

    @GetMapping(path = "/all/{pageNo}/{field}")
    public String findAllActorsWithSortAndPage(
                                @PathVariable("pageNo")   Optional<Integer> pageNo,
                                @PathVariable("field") Optional<String>  field,
                                                          Model model){
        Page<Actor> actorsByPageSizeAndField = repoService.findActorsByPageSizeAndField(
                                                                        pageNo.orElse(0),
                                                                        pageSize,
                                                                        field.orElse("id"));

        List<Actor> actorList = actorsByPageSizeAndField.getContent();
        Offset selectRecordsSize = new Offset();
        selectRecordsSize.setSize(pageSize);
        model.addAttribute("data",actorList);
        model.addAttribute("pageNo",actorsByPageSizeAndField.getNumber());
        model.addAttribute("TotalPages",actorsByPageSizeAndField.getTotalPages()-1);
        model.addAttribute("pageSize",actorsByPageSizeAndField.getNumberOfElements());
        model.addAttribute("offset",actorsByPageSizeAndField.getPageable().getOffset());
        model.addAttribute("setPageSize",selectRecordsSize);


        return "actors";
    }

    @GetMapping(path = "/pg")
    public String posts(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                        @RequestParam(value = "size", required = false, defaultValue = "5") int size, Model model) {

         List<Actor> actors = repoService.findAllActors();

         model.addAttribute("posts", repoService.getActorPage(pageNumber, pageSize,"id"));
        model.addAttribute("pageSize",pageSize);

        return "paged-result";
    }

}
