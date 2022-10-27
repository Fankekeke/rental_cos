package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.RentCharge;
import cc.mrbird.febs.cos.entity.RentInfo;
import cc.mrbird.febs.cos.dao.RentInfoMapper;
import cc.mrbird.febs.cos.service.IRentChargeService;
import cc.mrbird.febs.cos.service.IRentInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentInfoServiceImpl extends ServiceImpl<RentInfoMapper, RentInfo> implements IRentInfoService {

    private final IRentChargeService rentChargeService;

    /**
     * 分页获取出租信息信息
     *
     * @param page 分页对象
     * @param rentInfo 出租信息信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRentPage(Page<RentInfo> page, RentInfo rentInfo) {
        return baseMapper.selectRentPage(page, rentInfo);
    }

    /**
     * 获取租房状态
     *
     * @param rentId 租房ID
     * @return 结果
     */
    @Override
    public RentCharge selectRentStatus(String rentId) {
        return rentChargeService.getOne(Wrappers.<RentCharge>lambdaQuery().eq(RentCharge::getRentId, rentId));
    }
}
