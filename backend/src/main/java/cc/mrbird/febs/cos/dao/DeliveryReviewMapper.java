package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.DeliveryReview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface DeliveryReviewMapper extends BaseMapper<DeliveryReview> {

    /**
     * 分页获取交付审核信息
     *
     * @param page           分页对象
     * @param deliveryReview 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectDeliveryPage(Page<DeliveryReview> page, @Param("deliveryReview") DeliveryReview deliveryReview);
}
