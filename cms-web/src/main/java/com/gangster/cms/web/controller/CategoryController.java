package com.gangster.cms.web.controller;

import com.gangster.cms.common.constant.CmsConst;
import com.gangster.cms.common.pojo.Category;
import com.gangster.cms.web.annotation.AccessCount;
import com.gangster.cms.web.annotation.AccessLogger;
import com.gangster.cms.web.annotation.CountParam;
import com.gangster.cms.web.annotation.CountType;
import com.gangster.cms.web.dto.ModelResult;
import com.gangster.cms.web.service.CategoryWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/category/")
public class CategoryController {
    private final CategoryWebService webService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryController(CategoryWebService webService) {
        this.webService = webService;
    }

    @AccessCount(value = CountType.CATEGORY)
    @AccessLogger
    @RequestMapping("{id}")
    public String show(@CountParam @PathVariable("id") Integer id, Model model) {
        ModelResult result = webService.getCategoryModel(id);

        if (result == null) {
            return "404";
        }

        //Add result to module
        model.addAttribute("result", result);

        Category category = (Category) result.get("category");

        //If skin = null, put default skin
        if (category.getCategorySkin() == null) {
            logger.error("{} skin name is null",category.getCategoryTitle());
        }

        model.addAttribute("BaseSkinPath",category.getCategorySkin().split("/")[0]);

        //Return to the site's skin view, for example : default-category
        return category.getCategorySkin();
    }
}
