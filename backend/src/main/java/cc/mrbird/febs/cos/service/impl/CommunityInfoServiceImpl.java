package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CommunityInfo;
import cc.mrbird.febs.cos.dao.CommunityInfoMapper;
import cc.mrbird.febs.cos.entity.HouseInfo;
import cc.mrbird.febs.cos.service.ICommunityInfoService;
import cc.mrbird.febs.cos.service.IHouseInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommunityInfoServiceImpl extends ServiceImpl<CommunityInfoMapper, CommunityInfo> implements ICommunityInfoService {

    private final IHouseInfoService houseInfoService;

    /**
     * 分页获取小区信息
     *
     * @param page 分页对象
     * @param communityInfo 小区信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectCommunityPage(Page<CommunityInfo> page, CommunityInfo communityInfo) {
        return baseMapper.selectCommunityPage(page, communityInfo);
    }

    /**
     * 远程获取小区信息
     *
     * @param name 小区信息
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> remoteCommunity(String name) {
        return baseMapper.remoteCommunity(name);
    }

    /**
     * 根据小区编号获取房屋信息
     *
     * @param code 小区编号
     * @return 结果
     */
    @Override
    public List<HouseInfo> selectHouseByCode(String code) {
        return houseInfoService.list(Wrappers.<HouseInfo>lambdaQuery().eq(HouseInfo::getCommunityCode, code));
    }

    /**
     * 根据小区编号获取租房信息
     *
     * @param code 小区编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectHouseRentByCode(String code) {
        return baseMapper.selectHouseRentByCode(code);
    }
}
