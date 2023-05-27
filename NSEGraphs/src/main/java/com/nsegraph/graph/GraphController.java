package com.nsegraph.graph;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graphs")
public class GraphController {
	
    @Autowired
    NSEGraphsSchedular nseGraph;
	
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        String message = "Hello, World!";
        return ResponseEntity.ok(message);
    }
    
    @GetMapping("/generate")
    public ResponseEntity<Void> generateGraph() throws IOException, InterruptedException {
    	nseGraph.takeScreenshot();
        return ResponseEntity.ok().build();
    }
}
