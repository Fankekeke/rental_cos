package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.RentInfo;
import cc.mrbird.febs.cos.dao.RentInfoMapper;
import cc.mrbird.febs.cos.service.IRentInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class RentInfoServiceImpl extends ServiceImpl<RentInfoMapper, RentInfo> implements IRentInfoService {

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
}
