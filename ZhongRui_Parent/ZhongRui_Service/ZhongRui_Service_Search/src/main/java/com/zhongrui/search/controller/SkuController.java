package com.zhongrui.search.controller;

import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/search")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @RequestMapping("/import")
    public Result importEs() {

        skuService.importSku();
        return new Result(true, StatusCode.OK, "导入成功");
    }

    /**
     * 搜索
     * @param searchMap
     * @return
     */
    @GetMapping
    public Map search(@RequestParam(required = false) Map searchMap){
        if(searchMap == null && searchMap.size() == 0){
            searchMap = new HashMap<String,String>();
        }
        Object pageNum = searchMap.get("pageNum");
        if(pageNum==null){
            searchMap.put("pageNum","1");
        }
        if(pageNum instanceof Integer){
            searchMap.put("pageNum",pageNum.toString());
        }
        return  skuService.search(searchMap);
    }
}
