package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.CommunityInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface ICommunityInfoService extends IService<CommunityInfo> {

    /**
     * 分页获取小区信息
     *
     * @param page 分页对象
     * @param communityInfo 小区信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectCommunityPage(Page<CommunityInfo> page, CommunityInfo communityInfo);

    /**
     * 远程获取小区信息
     *
     * @param name 业主编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> remoteCommunity(String name);
}
