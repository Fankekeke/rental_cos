package cc.mrbird.febs.job.task;

import cc.mrbird.febs.cos.dao.RentChargeMapper;
import cc.mrbird.febs.cos.entity.RentEvaluation;
import cc.mrbird.febs.cos.entity.StaffEvaluation;
import cc.mrbird.febs.cos.entity.vo.RentChargeEvaluationVo;
import cc.mrbird.febs.cos.entity.vo.RentChargeVo;
import cc.mrbird.febs.cos.service.IRentChargeService;
import cc.mrbird.febs.cos.service.IRentEvaluationService;
import cc.mrbird.febs.cos.service.IRentInfoService;
import cc.mrbird.febs.cos.service.IStaffEvaluationService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestTask {

    private final IRentInfoService rentInfoService;

    private final IRentChargeService rentChargeService;

    private final RentChargeMapper rentChargeMapper;

    private final IRentEvaluationService rentEvaluationService;

    private final IStaffEvaluationService staffEvaluationService;

    public void test(String params) {
        log.info("我是带参数的test方法，正在被执行，参数为：{}" , params);
    }
    public void test1() {
        log.info("我是不带参数的test1方法，正在被执行");
    }

    /**
     * 生成租房评价数据-定时任务
     */
    public void generateEvaluation() {
        // 查询三天前租房信息
        List<RentChargeEvaluationVo> rentChargeVoList = rentChargeMapper.selectLastRentEvaluation();
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            System.out.println("==================================未找到需要生成的数据=================================");
            return;
        }
        List<Integer> rentIds = rentChargeVoList.stream().map(RentChargeEvaluationVo::getRentId).collect(Collectors.toList());
        List<RentEvaluation> rentEvaluationList = rentEvaluationService.list(Wrappers.<RentEvaluation>lambdaQuery().in(RentEvaluation::getRentId, rentIds));
        Map<Integer, RentEvaluation> rentEvaluationMap = rentEvaluationList.stream().collect(Collectors.toMap(RentEvaluation::getRentId, e -> e));
        // 需要添加的数据
        List<RentEvaluation> needEvaluationList = new ArrayList<>();
        List<StaffEvaluation> needStaffEvaluationList = new ArrayList<>();
        for (RentChargeEvaluationVo rentCharge : rentChargeVoList) {
            if (rentEvaluationMap.get(rentCharge.getRentId()) == null) {
                continue;
            }
            RentEvaluation rentEvaluation = new RentEvaluation(rentCharge.getRentId(), rentCharge.getHouseCode(), BigDecimal.valueOf(95), BigDecimal.valueOf(95), BigDecimal.valueOf(95), BigDecimal.valueOf(95), BigDecimal.valueOf(95), BigDecimal.valueOf(95), BigDecimal.valueOf(95), rentCharge.getUserCode(), DateUtil.formatDateTime(new Date()));
            needEvaluationList.add(rentEvaluation);
            StaffEvaluation staffEvaluation = new StaffEvaluation(rentCharge.getStaffCode(), rentCharge.getRentId(), BigDecimal.valueOf(95), rentCharge.getUserCode(), DateUtil.formatDateTime(new Date()));
            needStaffEvaluationList.add(staffEvaluation);
        }
        rentEvaluationService.saveBatch(needEvaluationList);
        staffEvaluationService.saveBatch(needStaffEvaluationList);
        System.out.println("==================================生成评价数据-定时任务完成=================================");
    }


}
