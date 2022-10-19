package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.HousePriceTrend;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IHousePriceTrendService extends IService<HousePriceTrend> {

    /**
     * 分页获取房价走势信息
     *
     * @param page 分页对象
     * @param housePriceTrend 房价走势信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectPriceTrendPage(Page<HousePriceTrend> page, HousePriceTrend housePriceTrend);
}
