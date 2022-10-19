package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.HousePriceTrend;
import cc.mrbird.febs.cos.dao.HousePriceTrendMapper;
import cc.mrbird.febs.cos.service.IHousePriceTrendService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class HousePriceTrendServiceImpl extends ServiceImpl<HousePriceTrendMapper, HousePriceTrend> implements IHousePriceTrendService {

    /**
     * 分页获取房价走势信息
     *
     * @param page 分页对象
     * @param housePriceTrend 房价走势信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectPriceTrendPage(Page<HousePriceTrend> page, HousePriceTrend housePriceTrend) {
        return baseMapper.selectPriceTrendPage(page, housePriceTrend);
    }
}
