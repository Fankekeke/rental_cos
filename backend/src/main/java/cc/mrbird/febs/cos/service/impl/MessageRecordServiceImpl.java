package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.MessageRecord;
import cc.mrbird.febs.cos.dao.MessageRecordMapper;
import cc.mrbird.febs.cos.service.IMessageRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class MessageRecordServiceImpl extends ServiceImpl<MessageRecordMapper, MessageRecord> implements IMessageRecordService {

    /**
     * 分页查询消息发送记录
     *
     * @param page        分页对象
     * @param messageRecord 参数
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectMessageRecordPage(Page<MessageRecord> page, MessageRecord messageRecord) {
        return baseMapper.selectMessageRecordPage(page, messageRecord);
    }
}
