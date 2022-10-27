package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.RentCharge;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author FanK
 */
public interface IRentChargeService extends IService<RentCharge> {

    /**
     * 查询租房信息包括房屋编号与小区编号
     *
     * @return 结果
     */
    List<RentCharge> selectRentChargeWithHouse();
}
