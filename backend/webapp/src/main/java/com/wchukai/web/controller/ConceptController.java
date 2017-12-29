package com.wchukai.web.controller;

import com.wchukai.web.dto.Result;
import com.wchukai.web.dto.in.ConceptIn;
import com.wchukai.web.service.ConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chukai
 */
@RestController
@RequestMapping("/api")
public class ConceptController extends BaseController {

    @Autowired
    private ConceptService conceptService;

    @RequestMapping(value = "/concept", method = RequestMethod.GET)
    public
    @ResponseBody
    Result selectConcept(@ModelAttribute ConceptIn conceptIn) {
        return conceptService.selectConcept(conceptIn);
    }

    @RequestMapping("/concept/search")
    public
    @ResponseBody
    Result liveSearch(@RequestParam String key) {
        return conceptService.liveSearch(key);
    }

    @RequestMapping("/fields")
    public
    @ResponseBody
    Result fields() {
        return conceptService.selectFields();
    }

    @RequestMapping("concepts")
    public
    @ResponseBody
    Result concepts(@ModelAttribute ConceptIn conceptIn) {
        return conceptService.selectConcepts(conceptIn);
    }
}
