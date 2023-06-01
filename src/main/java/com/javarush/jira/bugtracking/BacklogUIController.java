package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.to.TaskTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Slf4j
@Controller
@RequestMapping
public class BacklogUIController {
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/ui/backlog", method = RequestMethod.GET)
    public String getBacklog(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<TaskTo> backlogPage = taskService.getBacklogPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("backlogPage", backlogPage);

        int totalPages = backlogPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "backlog.html";
    }
}
