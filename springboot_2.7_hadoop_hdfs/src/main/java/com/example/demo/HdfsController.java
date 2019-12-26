package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hadoop/hdfs")
public class HdfsController {

    private static Logger LOGGER = LoggerFactory.getLogger(HdfsController.class);

}