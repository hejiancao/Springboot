package com.example.demo.controller;

import com.example.demo.entity.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * 用户创建某本图书	POST	/books/
 * 用户修改对某本图书	PUT	/books/:id/
 * 用户删除对某本图书	DELETE	/books/:id/
 * 用户获取所有的图书 GET /books
 * 用户获取某一图书  GET /Books/:id
 * 官方文档：http://swagger.io/docs/specification/api-host-and-base-path/
 */
@Api(tags = "图书管理")
@RestController
@RequestMapping(value = "/books")
public class BookContrller {

    private static final String TAGS = "图书管理";


    @ApiOperation(value = "获取图书列表", tags = TAGS, notes = "获取图书列表")
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public List<Book> getBook() {
        return null;
    }

    @ApiOperation(value = "创建图书", tags = TAGS, notes = "创建图书")
    @ApiImplicitParam(name = "book", value = "图书详细实体", required = true, dataType = "Book")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postBook(@RequestBody Book book) {
        return null;
    }

    // 需要说明的是，如果请求参数在url上，@ApiImplicitParam 上加paramType = “path”

    @ApiOperation(value = "获图书细信息", tags = TAGS, notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Book getBook(@PathVariable Long id) {
        return null;
    }

    @ApiOperation(value = "更新信息", tags = TAGS, notes = "根据url的id来指定更新图书信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图书ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "book", value = "图书实体book", required = true, dataType = "Book")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @RequestBody Book book) {
        return null;
    }

    @ApiOperation(value = "删除图书", tags = TAGS, notes = "根据url的id来指定删除图书")
    @ApiImplicitParam(name = "id", value = "图书ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        return null;
    }

    @ApiIgnore//使用该注解忽略这个API
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String jsonTest() {
        return " hi you!";
    }
}

