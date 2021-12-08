package application.ui.controller;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.User;
import application.ui.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class TaskController {

    @GetMapping(value = "/projects/{project_id}/tasks/create")
    public ModelAndView create_get(@PathVariable("project_id") Project project, @ModelAttribute Task task, User user) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("project", project);
        params.put("user", user);
        return new ModelAndView("tasks/create", params);
    }

    @PostMapping(value = "/projects/{project_id}/tasks/create")
    public ModelAndView create_post(@PathVariable("project_id") Project project,
                                    @Valid Task task,
                                    User user,
                                    BindingResult result,
                                    RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("tasks/create", "formErrors", result.getAllErrors());
        }
        task = TaskService.create(project, task);
        HashMap<String, Object> params = new HashMap<>();
        params.put("project_id", project.getId());
        params.put("task_id", task.getId());
        params.put("user", user);
        redirect.addFlashAttribute("globalTask", "Successfully created a new task");
        return new ModelAndView("redirect:/projects/{project_id}/tasks/{task_id}", params);
    }

    @GetMapping(value = "/projects/{project_id}/tasks/{task_id}")
    public ModelAndView view_get(@PathVariable("project_id") Project project,
                                 @PathVariable("task_id") Task task,
                                 User user) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("project", project);
        params.put("task", task);
        params.put("user", user);
        return new ModelAndView("tasks/view", params);
    }
}
