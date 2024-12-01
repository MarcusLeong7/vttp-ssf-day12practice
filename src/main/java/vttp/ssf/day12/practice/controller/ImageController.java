package vttp.ssf.day12.practice.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class ImageController {

    @GetMapping
    public String showForm() {
        return "Form"; // Thymeleaf form page
    }

    @GetMapping("/generate")
    public String getIndex(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false, name = "list") String list,
            Model model) {

        if (list != null && !list.isEmpty()) {
            
            List<String> numbers = new ArrayList<>();
            String[] parts = list.split(",");

            for(String part: parts) {
                part = part.trim();
                int tempNumbers = Integer.parseInt(part);
                numbers.add("/numbers/number%s".formatted(tempNumbers + ".jpg"));
            }
            
            model.addAttribute("message", "Here are your list of numbers");
            model.addAttribute("randomPicture", numbers);
        } else if (name != null && count != null) {
            model.addAttribute("message", "Hello, " + name + " This is your " + count + " lucky numbers");
            model.addAttribute("randomPicture", getRandomPictures(count));
        } else {
            model.addAttribute("message", "Invalid input.Please provide your name and count.");
            return "results";
        }

        return "results";
    }

    // Utility method 
    private List<String> getRandomPictures(int numberOfImages) {
        if (Constants.images.isEmpty()) {
            throw new IllegalStateException("No images available");
        }

        // Ensure requsted number does not exceed available images
        int maxImages = Math.min(numberOfImages, Constants.images.size());

        Random rand = new SecureRandom();
        Set<Integer> uniqueIndexes = new HashSet<>();

        while (uniqueIndexes.size() < maxImages) {
            int idx = rand.nextInt(Constants.images.size());
            uniqueIndexes.add(idx);
        }

        // Build List of image paths
        List<String> randomPictures = new ArrayList<>();
        for (int idx : uniqueIndexes) {
            randomPictures.add("/numbers/number%s".formatted(idx + ".jpg"));
        }
        return randomPictures;

    }

}
