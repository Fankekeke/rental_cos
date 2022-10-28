package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.HousePriceTrend;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

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

    /**
     * 获取房价走势信息
     *
     * @param communityCode 小区编号
     * @param year          年
     * @param month         月
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectHousePriceTrend(String communityCode, String year, String month);
}
