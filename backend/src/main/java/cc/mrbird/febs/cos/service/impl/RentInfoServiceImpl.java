package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.PaymentRecordMapper;
import cc.mrbird.febs.cos.entity.PaymentRecord;
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

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentInfoServiceImpl extends ServiceImpl<RentInfoMapper, RentInfo> implements IRentInfoService {

    private final IRentChargeService rentChargeService;

    private final PaymentRecordMapper paymentRecordMapper;

    private final RentInfoMapper rentInfoMapper;

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

    /**
     * 获取首页数据
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectHomeData() {
        // 本月收入
        paymentRecordMapper.selectAmount();
        // 新添房源
        rentInfoMapper.selectRentCountByMonth();
        // 租出房源
        paymentRecordMapper.selectRentStartByMonth();
        // 总收益
        BigDecimal all = paymentRecordMapper.selectList(Wrappers.<PaymentRecord>lambdaQuery()).stream().map(PaymentRecord::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 近十天收入统计

        // 近十天房源省份统计

        // 近十天房源省份统计

        // 近十天房屋合租类型统计
        return null;
    }
}
