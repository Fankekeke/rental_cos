package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.RentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface RentInfoMapper extends BaseMapper<RentInfo> {

    /**
     * 分页获取出租信息信息
     *
     * @param page     分页对象
     * @param rentInfo 出租信息信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRentPage(Page<RentInfo> page, @Param("rentInfo") RentInfo rentInfo);

    /**
     * 本月新添房源数量
     *
     * @return 结果
     */
    Integer selectRentCountByMonth();
}
