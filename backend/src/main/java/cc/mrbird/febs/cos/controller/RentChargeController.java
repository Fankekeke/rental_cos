package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.RentCharge;
import cc.mrbird.febs.cos.service.IRentChargeService;
import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/rent-charge")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentChargeController {

    private final IRentChargeService rentChargeService;

    /**
     * 添加租房负责人
     *
     * @param rentCharge 负责人
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody RentCharge rentCharge) {
        rentCharge.setPlanStatus(1);
        rentCharge.setCreateDate(DateUtil.formatDate(new Date()));
        return R.ok(rentChargeService.save(rentCharge));
    }

    /**
     * 修改租房负责人
     *
     * @param rentCharge 负责人
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody RentCharge rentCharge) {
        return R.ok(rentChargeService.updateById(rentCharge));
    }

}
