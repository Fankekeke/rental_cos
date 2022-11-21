package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.DeliveryReview;
import cc.mrbird.febs.cos.dao.DeliveryReviewMapper;
import cc.mrbird.febs.cos.entity.PaymentRecord;
import cc.mrbird.febs.cos.service.IDeliveryReviewService;
import cc.mrbird.febs.cos.service.IPaymentRecordService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryReviewServiceImpl extends ServiceImpl<DeliveryReviewMapper, DeliveryReview> implements IDeliveryReviewService {

    private final IPaymentRecordService paymentRecordService;

    /**
     * 分页获取交付审核信息
     *
     * @param page           分页对象
     * @param deliveryReview 参数
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectDeliveryPage(Page<DeliveryReview> page, DeliveryReview deliveryReview) {
        return baseMapper.selectDeliveryPage(page, deliveryReview);
    }

    /**
     * 添加交付审核信息
     *
     * @param deliveryReview 交付审核信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDeliveryReview(DeliveryReview deliveryReview) {
        // 默认审核状态
        deliveryReview.setStep("1");
        // 合同状态
        deliveryReview.setContractStatus("1");
        deliveryReview.setCreateDate(DateUtil.formatDate(new Date()));
        // 合同编号
        if (StrUtil.isEmpty(deliveryReview.getContractCode())) {
            deliveryReview.setContractCode("CON-" + System.currentTimeMillis());
        }
        // 计算居住结束时间
        deliveryReview.setEndLive(DateUtil.formatDate(DateUtil.offsetMonth(DateUtil.parse(deliveryReview.getStartLive(), "yyyy-MM-dd"), deliveryReview.getRentDay())));
        // 添加缴费记录
        PaymentRecord deposit = new PaymentRecord();
        deposit.setContractCode(deliveryReview.getContractCode());
        deposit.setCreateDate(deliveryReview.getCreateDate());
        deposit.setRentUserCode(deliveryReview.getRentUserCode());
        deposit.setStartDate(deliveryReview.getCreateDate());
        // 租金缴费记录
        deposit.setAmount(deliveryReview.getContractPrice());
        deposit.setPaymentType("2");
        List<PaymentRecord> paymentRecordList = new ArrayList<>();
        // 房租缴费记录
        PaymentRecord rent = new PaymentRecord();
        rent.setContractCode(deliveryReview.getContractCode());
        rent.setCreateDate(deliveryReview.getCreateDate());
        rent.setRentUserCode(deliveryReview.getRentUserCode());
        rent.setStartDate(deliveryReview.getCreateDate());
        // 租金缴费记录
        rent.setPaymentType("1");
        if ("1".equals(deliveryReview.getPayType())) {
            rent.setRentDay(1);
            rent.setAmount(deliveryReview.getContractPrice());
            rent.setEndDate(DateUtil.formatDate(DateUtil.offsetMonth(new Date(), 1)));
        } else {
            rent.setRentDay(3);
            rent.setAmount(deliveryReview.getContractPrice().multiply(BigDecimal.valueOf(3)));
            rent.setEndDate(DateUtil.formatDate(DateUtil.offsetMonth(new Date(), 3)));
        }
        paymentRecordList.add(deposit);
        paymentRecordList.add(rent);
        paymentRecordService.saveBatch(paymentRecordList);
        return this.save(deliveryReview);
    }
}
