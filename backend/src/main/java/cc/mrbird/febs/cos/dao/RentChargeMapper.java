package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.RentCharge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author FanK
 */
public interface RentChargeMapper extends BaseMapper<RentCharge> {

    /**
     * 查询租房信息包括房屋编号与小区编号
     *
     * @return 结果
     */
    List<RentCharge> selectRentChargeWithHouse();
}
