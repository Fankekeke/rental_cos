package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.dao.MessageInfoMapper;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements IMessageInfoService {

    /**
     * 分页查询消息信息
     *
     * @param page        分页对象
     * @param messageInfo 参数
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectMessagePage(Page<MessageInfo> page, MessageInfo messageInfo) {
        return baseMapper.selectMessagePage(page, messageInfo);
    }

    /**
     * 根据用户编号获取消息信息
     *
     * @param userCode 用户编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectMessageByUser(String userCode) {
        return baseMapper.selectMessageByUser(userCode);
    }
}
