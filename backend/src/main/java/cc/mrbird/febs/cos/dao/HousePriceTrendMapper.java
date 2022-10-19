package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.HousePriceTrend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface HousePriceTrendMapper extends BaseMapper<HousePriceTrend> {

    /**
     * 分页获取房价走势信息
     *
     * @param page 分页对象
     * @param housePriceTrend 房价走势信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectPriceTrendPage(Page<HousePriceTrend> page, @Param("housePriceTrend") HousePriceTrend housePriceTrend);
}
