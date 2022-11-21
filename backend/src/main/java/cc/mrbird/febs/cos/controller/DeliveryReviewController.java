package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.DeliveryReview;
import cc.mrbird.febs.cos.service.IDeliveryReviewService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/delivery-review")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryReviewController {

    private final IDeliveryReviewService deliveryReviewService;

    /**
     * 分页获取交付审核信息
     *
     * @param page           分页对象
     * @param deliveryReview 参数
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<DeliveryReview> page, DeliveryReview deliveryReview) {
        return R.ok(deliveryReviewService.selectDeliveryPage(page, deliveryReview));
    }

    /**
     * 添加交付审核信息
     *
     * @param deliveryReview 交付审核信息
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody DeliveryReview deliveryReview) {
        return R.ok(deliveryReviewService.saveDeliveryReview(deliveryReview));
    }

    /**
     * 修改交付审核信息
     *
     * @param deliveryReview 交付审核信息
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody DeliveryReview deliveryReview) {
        return R.ok(deliveryReviewService.updateById(deliveryReview));
    }

    /**
     * 删除消息
     *
     * @param ids IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(deliveryReviewService.removeByIds(ids));
    }

}
