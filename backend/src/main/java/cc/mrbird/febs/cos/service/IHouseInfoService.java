package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.HouseInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IHouseInfoService extends IService<HouseInfo> {

    /**
     * 分页获取房屋信息
     *
     * @param page 分页对象
     * @param houseInfo 房屋信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectHousePage(Page<HouseInfo> page, HouseInfo houseInfo);
}
