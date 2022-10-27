package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 房屋出租负责人
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RentCharge implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出租信息ID
     */
    private Integer rentId;

    /**
     * 负责人
     */
    private String staffCode;

    /**
     * 状态（1.在售 2.已售 3.下架）
     */
    private Integer planStatus;

    /**
     * 创建时间
     */
    private String createDate;


}
