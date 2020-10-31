package com.zhongrui.controller;

import com.zhongrui.entity.Page;
import com.zhongrui.search.feign.SkuFeign;
import com.zhongrui.search.pojo.SkuInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class SkuController {
    @Resource
    private SkuFeign skuFeign;

    @GetMapping("/list")
    public String search(@RequestParam(required = false) Map searchMap, Model model){
        Map search = skuFeign.search(searchMap);
        model.addAttribute("result",search);
        model.addAttribute("searchMap",searchMap);
        String[] urls = url(searchMap);
        model.addAttribute("url",urls[0]);
        model.addAttribute("sorturl",urls[1]);
        //分页计算
        Page<SkuInfo> page = new Page<SkuInfo>(
          Long.parseLong(search.get("totalPages").toString()),
          Integer.parseInt(search.get("pageNum").toString()),
          Integer.parseInt(search.get("pageSize").toString()));
        model.addAttribute("page",page);
        return "search";
    }

    /**
     * 拼接组装用户请求的url地址
     * 1.获取用户每次请求地址
     * 2.页面在url地址上拼接条件
     * @return
     */
    public String[] url(Map<String,String> searchMap){
        String url = "/search/list";
        String sorturl = "/search/list";
        if(searchMap != null && searchMap.size() > 0){
            url += "?";
            sorturl += "?";
            for (Map.Entry<String, String> stringStringEntry : searchMap.entrySet()) {
                //如果是排序 则 跳过 拼接排序的地址 因为有数据
                if(stringStringEntry.getKey().equals("sortField") || stringStringEntry.getKey().equals("sortRule")){
                    continue;
                }
                //分页参数不拼接
                if(stringStringEntry.getKey().equalsIgnoreCase("pageNum")){
                    continue;
                }
                url += stringStringEntry.getKey() + "=" + stringStringEntry.getValue() + "&";

            }
            url = url.substring(0,url.length()-1);
            sorturl = sorturl.substring(0,sorturl.length()-1);
        }
        return new String[]{url,sorturl};
    }
}
