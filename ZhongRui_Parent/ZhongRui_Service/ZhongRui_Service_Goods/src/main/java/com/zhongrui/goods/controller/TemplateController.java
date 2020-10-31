package com.zhongrui.goods.controller;

import com.github.pagehelper.PageInfo;
import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.goods.pojo.Template;
import com.zhongrui.goods.service.TemplateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/template")
public class TemplateController {

    //注入service
    @Resource
    private TemplateService templateService;

    @GetMapping
    public Result<List<Template>> findAll() {
        List<Template> templates = templateService.findAll();
        return new Result<>(true, StatusCode.OK, "查询所有模板成功", templates);
    }

    @GetMapping("/{id}")
    public Result<Template> findById(@PathVariable("id") Integer id) {
        Template template = templateService.findById(id);
        return new Result<>(true, StatusCode.OK, "根据id查询模板成功", template);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Template template) {
        templateService.add(template);
        return new Result(true, StatusCode.OK, "新增模板成功");
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Integer id, @RequestBody Template template) {
        template.setId(id);
        templateService.update(template);
        return new Result(true, StatusCode.OK, "修改模板数据成功");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        templateService.delete(id);
        return new Result(true, StatusCode.OK, "删除模板数据成功");
    }

    @PostMapping("/search")
    public Result<List<Template>> findList(@RequestBody Template template) {
        List<Template> templates = templateService.findList(template);
        if (template == null) {
            return new Result<>(false, StatusCode.ERROR, "查询数据不存在");
        }
        return new Result<>(true, StatusCode.OK, "查询模板数据成功", templates);
    }

    @GetMapping("/{page}/{size}")
    public Result<List<Template>> findPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Template> pageInfo = templateService.findPage(page, size);
        if (pageInfo == null) {
            return new Result<>(false, StatusCode.ERROR, "查询分页数据不存在，失败");
        }
        return new Result<List<Template>>(true, StatusCode.OK, "查询模板分页数据成功", pageInfo);
    }

    @PostMapping("/{page}/{size}")
    public Result<List<Template>> findPage(@RequestBody Template template, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Template> pageInfo = templateService.findPage(template,page, size);
        if (pageInfo == null) {
            return new Result<>(false, StatusCode.ERROR, "多条件查询分页数据不存在，失败");
        }
        return new Result<>(true, StatusCode.OK, "多条件查询模板分页数据成功", pageInfo);
    }
}
