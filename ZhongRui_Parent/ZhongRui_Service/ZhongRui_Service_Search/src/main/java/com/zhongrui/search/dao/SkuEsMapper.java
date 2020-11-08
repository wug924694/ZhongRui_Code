package com.zhongrui.search.dao;

import com.zhongrui.goods.pojo.Sku;
import com.zhongrui.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuEsMapper extends ElasticsearchRepository<SkuInfo,Long> {
}
