package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.RentCharge;
import cc.mrbird.febs.cos.dao.RentChargeMapper;
import cc.mrbird.febs.cos.service.IRentChargeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author FanK
 */
@Service
public class RentChargeServiceImpl extends ServiceImpl<RentChargeMapper, RentCharge> implements IRentChargeService {

    /**
     * 查询租房信息包括房屋编号与小区编号
     *
     * @return 结果
     */
    @Override
    public List<RentCharge> selectRentChargeWithHouse() {
        return null;
    }
}
