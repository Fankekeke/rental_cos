package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.HousePriceTrend;
import cc.mrbird.febs.cos.service.IHousePriceTrendService;
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
@RequestMapping("/cos/house-price-trend")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HousePriceTrendController {

    private final IHousePriceTrendService housePriceTrendService;

    /**
     * 分页获取房价走势信息
     *
     * @param page 分页对象
     * @param housePriceTrend 房价走势信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<HousePriceTrend> page, HousePriceTrend housePriceTrend) {
        return R.ok(housePriceTrendService.selectPriceTrendPage(page, housePriceTrend));
    }

    /**
     * 新增房价走势信息
     *
     * @param housePriceTrend 房价走势信息
     * @return 结果
     */
    @PostMapping
    public R save(HousePriceTrend housePriceTrend) {
        housePriceTrend.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(housePriceTrendService.save(housePriceTrend));
    }

    /**
     * 修改房价走势信息
     *
     * @param housePriceTrend 房价走势信息
     * @return 结果
     */
    @PutMapping
    public R edit(HousePriceTrend housePriceTrend) {
        return R.ok(housePriceTrendService.updateById(housePriceTrend));
    }

    /**
     * 删除房价走势信息
     *
     * @param ids ids
     * @return 房价走势信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(housePriceTrendService.removeByIds(ids));
    }

}
