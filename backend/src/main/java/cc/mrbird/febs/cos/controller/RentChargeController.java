package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.controller.po.RentChargePo;
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
     * 获取房屋出租负责信息
     *
     * @param rentCharge 参数
     * @return 结果
     */
    @PostMapping("/list")
    public R selectRentChargeInfo(RentChargePo rentCharge) {
        return R.ok(rentChargeService.selectRentChargeList(rentCharge));
    }

    /**
     * 获取各小区租房情况
     *
     * @param communityCode 小区编号
     * @return 结果
     */
    @GetMapping("/rentCharge/community")
    public R selectRentChargeByCommunity(@RequestParam(value = "communityCode", required = false) String communityCode) {
        return R.ok(rentChargeService.selectRentChargeByCommunity(communityCode));
    }

    /**
     * 房屋出租信息
     *
     * @param houseCode 房屋编号
     * @return 结果
     */
    @GetMapping("/rentCharge/house")
    public R selectRentChargeByHouse(@RequestParam(value = "houseCode", required = false) String houseCode) {
        return R.ok(rentChargeService.selectRentChargeByHouse(houseCode));
    }

    /**
     * 获取员工负责情况
     *
     * @param staffCode 员工编号
     * @return 结果
     */
    @GetMapping("/rentCharge/staff")
    public R selectRentChargeByStaff(@RequestParam(value = "staffCode", required = false) String staffCode) {
        return R.ok(rentChargeService.selectRentChargeByStaff(staffCode));
    }

    /**
     * 根据时间获取去出租信息
     *
     * @param year  统计年度
     * @param month 统计月度
     * @return 结果
     */
    @GetMapping("/charge/date")
    public R selectRentChargeByDate(@RequestParam("year") String year, @RequestParam(value = "month", required = false) String month) {
        return R.ok(rentChargeService.selectRentChargeByDate(year, month));
    }

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
