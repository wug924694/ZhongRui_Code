package com.zhongrui.search.controller;

import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.search.service.SkuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SkuController {

    @Resource
    private SkuService skuService;

    /**
     * 导入数据
     * @return
     */
    @GetMapping("/import")
    public Result search(){
        skuService.importSku();
        return new Result(true, StatusCode.OK,"导入数据到索引库中成功！");
    }

    /**
     * 搜索
     * @param searchMap
     * @return
     */
    @GetMapping
    public Map search(@RequestParam(required = false) Map searchMap){
        if(searchMap == null){
            searchMap = new HashMap<String,String>();
        }
        Object pageNum = searchMap.get("pageNum");
        if(pageNum == null){
            searchMap.put("pageNum","1");
        }
        if(pageNum instanceof Integer){
            searchMap.put("pageNum",pageNum.toString());
        }
        return  skuService.search(searchMap);
    }
}