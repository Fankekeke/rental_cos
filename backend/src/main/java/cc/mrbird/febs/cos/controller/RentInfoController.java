package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.RentInfo;
import cc.mrbird.febs.cos.service.IRentInfoService;
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
@RequestMapping("/cos/rent-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentInfoController {

    private final IRentInfoService rentInfoService;

    /**
     * 分页获取出租信息信息
     *
     * @param page 分页对象
     * @param rentInfo 出租信息信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RentInfo> page, RentInfo rentInfo) {
        return R.ok(rentInfoService.selectRentPage(page, rentInfo));
    }

    /**
     * 新增出租信息信息
     *
     * @param rentInfo 出租信息信息
     * @return 结果
     */
    @PostMapping
    public R save(RentInfo rentInfo) {
        rentInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(rentInfoService.save(rentInfo));
    }

    /**
     * 修改出租信息信息
     *
     * @param rentInfo 出租信息信息
     * @return 结果
     */
    @PutMapping
    public R edit(RentInfo rentInfo) {
        return R.ok(rentInfoService.updateById(rentInfo));
    }

    /**
     * 删除出租信息信息
     *
     * @param ids ids
     * @return 出租信息信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(rentInfoService.removeByIds(ids));
    }

}
