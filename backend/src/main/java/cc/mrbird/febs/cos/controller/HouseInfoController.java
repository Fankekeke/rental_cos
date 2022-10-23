package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.HouseInfo;
import cc.mrbird.febs.cos.service.IHouseInfoService;
import cn.hutool.core.date.DateUtil;
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
@RequestMapping("/cos/house-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HouseInfoController {

    private final IHouseInfoService houseInfoService;

    /**
     * 分页获取房屋信息
     *
     * @param page      分页对象
     * @param houseInfo 房屋信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<HouseInfo> page, HouseInfo houseInfo) {
        return R.ok(houseInfoService.selectHousePage(page, houseInfo));
    }

    /**
     * 远程调用房屋信息
     *
     * @param ownerCode 业主编号
     * @return 结果
     */
    public R remoteHouse(@RequestParam(value = "ownerCode", required = false) String ownerCode) {
        return R.ok(houseInfoService.remoteHouse(ownerCode));
    }

    /**
     * 新增房屋信息
     *
     * @param houseInfo 房屋信息
     * @return 结果
     */
    @PostMapping
    public R save(HouseInfo houseInfo) {
        houseInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(houseInfoService.save(houseInfo));
    }

    /**
     * 修改房屋信息
     *
     * @param houseInfo 房屋信息
     * @return 结果
     */
    @PutMapping
    public R edit(HouseInfo houseInfo) {
        return R.ok(houseInfoService.updateById(houseInfo));
    }

    /**
     * 删除房屋信息
     *
     * @param ids ids
     * @return 房屋信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(houseInfoService.removeByIds(ids));
    }
}
