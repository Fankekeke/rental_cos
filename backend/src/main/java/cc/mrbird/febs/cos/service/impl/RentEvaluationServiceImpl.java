package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.RentEvaluation;
import cc.mrbird.febs.cos.dao.RentEvaluationMapper;
import cc.mrbird.febs.cos.service.IRentEvaluationService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class RentEvaluationServiceImpl extends ServiceImpl<RentEvaluationMapper, RentEvaluation> implements IRentEvaluationService {

    /**
     * 分页获取租房评价信息
     *
     * @param page 分页对象
     * @param rentEvaluation 租房评价信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRentEvaluationPage(Page<RentEvaluation> page, RentEvaluation rentEvaluation) {
        return baseMapper.selectRentEvaluationPage(page, rentEvaluation);
    }
}
