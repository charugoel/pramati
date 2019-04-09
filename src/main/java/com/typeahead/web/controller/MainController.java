package com.typeahead.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.typeahead.web.model.Tag;
import com.typeahead.web.service.Trie;

@Controller
public class MainController {

	List<Tag> data = new ArrayList<Tag>();
	@Autowired
    public Trie trie;
	
	MainController() {

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getPages() {
		ModelAndView model = new ModelAndView("form");
		return model;

	}

	@RequestMapping(value = "/suggest_cities_unlimited", method = RequestMethod.GET)
	public @ResponseBody
	List<Tag> getTags(@RequestParam String tagName ) {
		//get city name filter by tagName
		ArrayList<String >result=trie.getAutoSuggestions(tagName.toLowerCase());
		return simulateSearchResult(result);

	}
	
	@RequestMapping(value = "/suggest_cities", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getTags(@RequestParam String start,@RequestParam int atmost ) {
		//get city name filter by tagName
		ArrayList<String >result=trie.getAutoSuggestions(start.toLowerCase());
		return result.subList(0, atmost);

	}

	private List<Tag> simulateSearchResult(ArrayList<String> result) {
		List<Tag> response = new ArrayList<Tag>();
		int i=0;
		// iterate a list and filter by tagName
		for (String tag : result) {
			 i++;
			response.add(new Tag(i, tag));
		}
		return response;
	}
	

}
